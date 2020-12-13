package com.stardust.sync.web;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stardust.sync.model.User;
import com.stardust.sync.service.SecurityService;
import com.stardust.sync.service.UserService;
import com.stardust.sync.util.GenerateDailyReadingPdfReport;
import com.stardust.sync.util.GenerateFacilitySummaryPdfReport;
import com.stardust.sync.util.GeneratePdfBill;
import com.stardust.sync.util.GenerateTenantSummaryPdfReport;
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
	private GenerateDailyReadingPdfReport generateDailyReadingPdfReport;

	@Autowired
	private GenerateTenantSummaryPdfReport generateTenantSummaryPdfReport;
	
	@Autowired
	private GenerateFacilitySummaryPdfReport generateFacilitySummaryPdfReport;

	@Autowired
	private GeneratePdfBill generatePdfBill;

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

	@RequestMapping(value = "/billing/generateElec", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> electricalR(String efl, String efr, String ero, String epp, String edi,
			String eof, String eto, String erp, String epe) {
		if (efl == null || efr == null || ero == null || epp == null || edi == null || eof == null || eto == null
				|| erp == null || epe == null)
			return null;

		ByteArrayInputStream bis = generatePdfBill.electricalR(efl, efr, ero, epp, edi, eof, eto, erp, epe);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Electricity_Bill_" + efr + "-" + eto + ".pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@RequestMapping(value = "/billing/generateAC", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> airconR(String afl, String afr, String aro, String app, String adi,
			String aof, String ato, String arp, String ape) {

		if (afl == null || afr == null || aro == null || app == null || adi == null || aof == null || ato == null
				|| arp == null || ape == null)
			return null;

		ByteArrayInputStream bis = generatePdfBill.airconR(afl, afr, aro, app, adi, aof, ato, arp, ape);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Aircon_Bill_" + afr + "-" + ato + ".pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@RequestMapping(value = "/report/electricalDailyReport", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> electricalDailyReport(String efr, String eto) {

		if (efr == null || eto == null)
			return null;

		ByteArrayInputStream bis = generateDailyReadingPdfReport.electricalDR(efr, eto);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Electrical_DailyReport_" + efr + "-" + eto + ".pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@RequestMapping(value = "/report/airconDailyReport", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> airconDailyReport(String efr, String eto) {

		if (efr == null || eto == null)
			return null;

		ByteArrayInputStream bis = generateDailyReadingPdfReport.airconDR(efr, eto);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Aircon_DailyReport_" + efr + "-" + eto + ".pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@RequestMapping(value = "/report/electricalTenantSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> electricalTenantSummary(String efl, String eof, String efr, String eto) {

		if (efr == null || eto == null)
			return null;

		ByteArrayInputStream bis = generateTenantSummaryPdfReport.electricalTS(efl, eof, efr, eto);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Electrical_TenantSummary_" + efr + "-" + eto + ".pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@RequestMapping(value = "/report/airconTenantSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> airconTenantSummary(String afl, String aof, String afr, String ato) {

		if (afr == null || ato == null)
			return null;

		ByteArrayInputStream bis = generateTenantSummaryPdfReport.airconTS(afl, aof, afr, ato);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Aircon_TenantSummary_" + afr + "-" + ato + ".pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@RequestMapping(value = "/report/facilitySummaryReport", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> facilitySummaryReport(String efr, String eto) {

		if (efr == null || eto == null)
			return null;

		ByteArrayInputStream bis = generateFacilitySummaryPdfReport.summaryReport(efr, eto);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Facility_Summary_Report_" + efr + "-" + eto + ".pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

}
