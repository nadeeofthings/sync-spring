package com.stardust.sync.web;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stardust.sync.core.Constants;
import com.stardust.sync.model.Alert;
import com.stardust.sync.model.Billing;
import com.stardust.sync.model.Customer;
import com.stardust.sync.model.User;
import com.stardust.sync.service.ActivityService;
import com.stardust.sync.service.BillingService;
import com.stardust.sync.service.CustomerService;
import com.stardust.sync.service.NotificationDispatcherService;
import com.stardust.sync.service.SecurityService;
import com.stardust.sync.service.UserService;
import com.stardust.sync.util.GenerateDailyReadingPdfReport;
import com.stardust.sync.util.GenerateFacilitySummaryPdfReport;
import com.stardust.sync.util.GeneratePdfBill;
import com.stardust.sync.util.GenerateTenantSummaryPdfReport;
import com.stardust.sync.validator.CustomerValidator;
import com.stardust.sync.validator.UserValidator;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private CustomerValidator customerValidator;

	@Autowired
	private GenerateDailyReadingPdfReport generateDailyReadingPdfReport;

	@Autowired
	private GenerateTenantSummaryPdfReport generateTenantSummaryPdfReport;
	
	@Autowired
	private GenerateFacilitySummaryPdfReport generateFacilitySummaryPdfReport;

	@Autowired
	private BillingService billingService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	ActivityService activityService;
	
	@Autowired
    private NotificationDispatcherService notificationDispatcherService;

	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@PostMapping("/registration")
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(userForm);
		// securityService.autoLogin(userForm.getUsername(),
		// userForm.getPasswordConfirm());

		return "pending";
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@GetMapping("/property")
	public String property(Model model, String id) {
		if (id != null) {
			model.addAttribute("id", id);

			return "levels";
		} else {
			return "index";
		}

	}

	@GetMapping("/unit")
	public String unit(Model model, String id, String unit) {
		if (id != null && unit != null) {
			model.addAttribute("id", id);
			model.addAttribute("unit", unit);
			return "apartment";
		} else {
			return "index";
		}
	}
	
	@GetMapping("/alerts")
	public String alerts(Model model) {
		return "alert";
	}

	@GetMapping({ "/", "/dashboard" })
	public String welcome(Model model) {
		return "index";
	}

	@GetMapping({ "/billing" })
	public String billing(Model model) {
		return "billing";
	}

	@GetMapping({ "/reports" })
	public String reports(Model model) {
		return "reports";
	}
	
	@GetMapping({ "/activity" })
	public String activity(Model model) {
		return "activity";
	}
	
	@GetMapping({ "/settings" })
	public String settings(Model model, Authentication authentication) {
		String auth = authentication.getAuthorities().toString();
		if(auth.contains("ROLE_ADMIN") || auth.contains("ROLE_SUPERADMIN")) {
			model.addAttribute("customerForm", new Customer());
			return "settings";
		}else
		return "index";
	}

	@PostMapping("/settings")
	public String settings( Authentication authentication, Model model, @ModelAttribute("customerForm") Customer customerForm, BindingResult bindingResult) {
		customerValidator.validate(customerForm, bindingResult);
		if (bindingResult.hasErrors()) {
			//model.addAttribute("tab", "customer");
			return "settings";
		}
		customerService.save(customerForm);
		
		activityService.log("New customer: "+customerForm.getName()+" created", authentication.getName());
		model.addAttribute("customerForm", new Customer());
		notificationDispatcherService.dispatch(new Alert("New customer added to the system", Constants.ALERT_SUCCESS, new Date(), Constants.ALERT_STATUS_ACK));
		return "redirect:/settings?tag=customer";
	}
	
	@RequestMapping(value = "/billing/downloadBill", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> electricalR(long billId, Authentication authentication) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Billing bill = billingService.getBillByBillId(billId);
		if(bill != null) {
		ByteArrayInputStream bis = billingService.generatePDFBill(billId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename="+bill.getBillId()+"_"+(bill.getExt().equalsIgnoreCase("kWh")?"Electricity":"HVAC")+"_Bill_" 
												+ bill.getId()+"_" +bill.getUnit()+"_"+bill.getMeter()+"_"+format.format(bill.getFromDate())+"-" + format.format(bill.getToDate()) + ".pdf");
		
		activityService.log(bill.getBillId()+"_"+(bill.getExt().equalsIgnoreCase("kWh")?"Electricity":"HVAC")+"_Bill_" 
				+ bill.getId()+"_" +bill.getUnit()+"_"+bill.getMeter()+"_"+format.format(bill.getFromDate())+"-" + format.format(bill.getToDate()) + ".pdf generated", authentication.getName());
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
		}
		return null;
	}

	@RequestMapping(value = "/report/electricalDailyReport", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> electricalDailyReport(String efr, String eto, Authentication authentication) {

		if (efr == null || eto == null)
			return null;

		ByteArrayInputStream bis = generateDailyReadingPdfReport.electricalDR(efr, eto);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Electrical_DailyReport_" + efr + "-" + eto + ".pdf");
		
		activityService.log("Electrical_DailyReport_" + efr + "-" + eto + ".pdf created", authentication.getName());
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@RequestMapping(value = "/report/airconDailyReport", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> airconDailyReport(String efr, String eto, Authentication authentication) {

		if (efr == null || eto == null)
			return null;

		ByteArrayInputStream bis = generateDailyReadingPdfReport.airconDR(efr, eto);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Aircon_DailyReport_" + efr + "-" + eto + ".pdf");
		
		activityService.log("Aircon_DailyReport_" + efr + "-" + eto + ".pdf generated", authentication.getName());
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@RequestMapping(value = "/report/electricalTenantSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> electricalTenantSummary(String efl, String eof, String efr, String eto, Authentication authentication) {

		if (efr == null || eto == null)
			return null;

		ByteArrayInputStream bis = generateTenantSummaryPdfReport.electricalTS(efl, eof, efr, eto);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Electrical_TenantSummary_" + efr + "-" + eto + ".pdf");
		
		
		activityService.log("Electrical_TenantSummary_" + efr + "-" + eto + ".pdf generated", authentication.getName());
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@RequestMapping(value = "/report/airconTenantSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> airconTenantSummary(String afl, String aof, String afr, String ato, Authentication authentication) {

		if (afr == null || ato == null)
			return null;

		ByteArrayInputStream bis = generateTenantSummaryPdfReport.airconTS(afl, aof, afr, ato);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Aircon_TenantSummary_" + afr + "-" + ato + ".pdf");

		activityService.log("Aircon_TenantSummary_" + afr + "-" + ato + ".pdf generated", authentication.getName());
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@RequestMapping(value = "/report/facilitySummaryReport", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> facilitySummaryReport(String efr, String eto, Authentication authentication) {

		if (efr == null || eto == null)
			return null;

		ByteArrayInputStream bis = generateFacilitySummaryPdfReport.summaryReport(efr, eto);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Facility_Summary_Report_" + efr + "-" + eto + ".pdf");

		activityService.log("Facility_Summary_Report_" + efr + "-" + eto + ".pdf generated", authentication.getName());
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}
	


}
