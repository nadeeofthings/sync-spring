 package com.stardust.sync.web;

import org.apache.tomcat.util.json.JSONParser;
import org.jfree.data.json.impl.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stardust.sync.core.ModbusSingleton;
import com.stardust.sync.model.Activity;
import com.stardust.sync.model.Alert;
import com.stardust.sync.model.Billing;
import com.stardust.sync.model.BillingProperties;
import com.stardust.sync.model.Configuration;
import com.stardust.sync.model.Customer;
import com.stardust.sync.model.Meter;
import com.stardust.sync.model.MeterConfiguration;
import com.stardust.sync.model.MeterExtended;
import com.stardust.sync.model.User;
import com.stardust.sync.service.ActivityService;
import com.stardust.sync.service.AlertService;
import com.stardust.sync.service.BillingPropertiesService;
import com.stardust.sync.service.BillingService;
import com.stardust.sync.service.ConfigurationService;
import com.stardust.sync.service.CustomerService;
import com.stardust.sync.service.MeterConfigurationService;
import com.stardust.sync.service.MeterService;
import com.stardust.sync.service.UserService;

import de.re.easymodbus.modbusclient.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
public class UserRestController {
	
	@Autowired
	MeterService meterService;
	
	@Autowired
	ConfigurationService configurationService;
	
	@Autowired
	MeterConfigurationService meterConfigurationService;
	
	@Autowired
	BillingPropertiesService billingPropertiesService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AlertService alertService;
	
	@Autowired
	private BillingService billingService;
	
	@Autowired
	ActivityService activityService;
	
	@GetMapping(value = "rest/reading")
    public List<Meter> getReadings(String id, String unit) {
		
		
       List<Meter> meters = new ArrayList<Meter>();
       
       List<MeterConfiguration> configs= meterConfigurationService.getMeterConfigurationsByIdAndUnit(id, unit);
       
       for(MeterConfiguration config : configs) {
    	  // System.out.println(config.getAddress());
    	   meters.add(new Meter(id,unit,1,meterService.getValue(config.getAddress()),config.getExt(), new Date(),false));
       }
       
       return meters;
    }
	
	@GetMapping(value = "rest/byId")
    public List<Meter> getReadingsById(String ext,String id, String unit) {
		return meterService.byIdAndUnitAndExt(id, unit, ext);
		
	}
	
	@GetMapping(value = "rest/byMeterId")
    public List<Meter> getReadingsByMeterId(String ext,String id, String unit, int meter) {
		return meterService.byIdAndUnitAndExtAndMeter(id, unit, ext, meter);
		
	}
	
	@GetMapping(value = "rest/lastSevenbyMeterId")
    public List<Meter> getLastSevenReadingsByMeterId(String ext,String id, String unit, int meter) {
		return meterService.lastSevenbyIdAndUnitAndExtAndMeter(id, unit, ext, meter);
		
	}
	
	@GetMapping(value = "rest/totalbyExt")
    public Map<String, Double> getTotalByExt(String ext,String start, String end) {
    	Date startDate = new java.util.Date(Long.parseLong(start)*1000L); 
		Date endDate = new java.util.Date(Long.parseLong(end)*1000L); 
		return meterService.byExtBetweenStartAndEnd(ext, startDate, endDate);
		
		
	}
	
	@GetMapping(value = "rest/unitTotalbyExt")
    public List<Meter> getUnitTotalByExt(String id, String unit, String ext, int meter, String start) {
    	Date startDate = new java.util.Date(Long.parseLong(start)*1000L); 
		List<Meter> meters = new ArrayList<Meter>();
		meters.add(meterService.unitDailyUsage(id, unit, ext, meter, startDate));
		return meters;
	}
	
	@GetMapping(value = "rest/extendedUnitTotalbyExt")
    public List<MeterExtended> getExtendedUnitTotalByExt(String id, String unit, String ext, int meter, String start) {
    	Date startDate = new java.util.Date(Long.parseLong(start)*1000L); 
		List<MeterExtended> meters = new ArrayList<MeterExtended>();
		meters.add(meterService.unitDailyUsageExtended(id, unit, ext, meter, startDate));
		return meters;
	}
	
	@GetMapping(value = "rest/buildingTotalbyExt")
    public List<Meter> getBuildingTotalByExt(String ext, String start) {
    	Date startDate = new java.util.Date(Long.parseLong(start)*1000L); 
		List<Meter> meters = new ArrayList<Meter>();
		meters.add(meterService.buildingDailyUsage(ext, startDate));
		return meters;
	}
	
	@GetMapping(value = "rest/unitDailyUsage")
    public List<Meter> getUnitDailyUsage(String id, String unit, String ext, int meter, String start, int limit) {
    	Date startDate = new java.util.Date(Long.parseLong(start)*1000L); 
    	return meterService.listDailyUsage(id, unit, ext, meter, startDate,limit);
	}
	
	@GetMapping(value = "rest/extendedUnitDailyUsage")
    public List<MeterExtended> getExtendedUnitDailyUsage(String id, String unit, String ext, int meter, String start, int limit) {
    	Date startDate = new java.util.Date(Long.parseLong(start)*1000L); 
    	return meterService.listDailyUsageExtended(id, unit, ext, meter, startDate,limit);
	}
	
	@GetMapping(value = "rest/buildingDailyUsage")
    public List<Meter> getBuildingDailyUsage(String ext, String start, int limit) {
    	Date startDate = new java.util.Date(Long.parseLong(start)*1000L); 
    	return meterService.listBuildingDailyUsage(ext, startDate,limit);
	}
	
	@GetMapping(value = "rest/unitUsage")
    public List<Meter> getUsage(String id, String unit, String ext, int meter, String start, String end) {
    	Date startDate = new java.util.Date(Long.parseLong(start)*1000L);
    	Date endDate = new java.util.Date(Long.parseLong(end)*1000L); 
    	List<Meter> meters = new ArrayList<Meter>();
		meters.add(meterService.totalUseageOfAUnit(id, unit, ext, meter, startDate, endDate));
    	return meters;
	}
	
	@GetMapping(value = "rest/topUnitsThisWeek")
    public List<Meter> getTopUnits(String ext) {
    		return meterService.topThreeThisWeek(ext);
	}
	
	
	@GetMapping(value = "rest/prevMonthTotal")
    public List<Meter> getPrevMonthTotal() {
		return meterService.totalPrevMonth();
		
	}
	
	@GetMapping(value = "rest/currMonthTotal")
    public List<Meter> getCurrMonthTotal() {
		return meterService.totalCurrMonth();
		
	}
	
	@GetMapping(value = "rest/alertCount")
    public long getAlertCount(Authentication authentication) {
		return userService.getAlertCountByUsername(authentication.getName());
		
	}
	
	@GetMapping(value = "rest/resetAlertCount")
    public int resetAlertCount(Authentication authentication) {
		userService.resetAlertCountByUsername(authentication.getName());
		return 1;
	}
	
	@GetMapping(value = "rest/getTop3Alerts")
    public List<Alert> getTop3Alerts() {
		return alertService.findTop3OrderByTimeStampDesc();
		
	}
	
	@GetMapping(value = "rest/getAllAlerts")
    public List<Alert> getAllAlerts() {
		return alertService.findAll();
		
	}
	
	@GetMapping(value = "rest/getAllConfigs")
    public List<MeterConfiguration> getAllConfigs() {
		return meterConfigurationService.getAllMeterConfigurations();
		
	}
	
	@GetMapping(value = "rest/getSystemConfigs")
    public List<Configuration> getSystemConfigs() {
		return configurationService.getAllConfigurations();
		
	}
	
	@GetMapping(value = "rest/getAllBillingProperties")
    public List<List<BillingProperties>> getAllBillingConfigs(String ext) {
		if (ext != null) return billingPropertiesService.getBillingProperties(ext);
		else return null;
	}
	
	@GetMapping(value = "rest/getAllBillingLogs")
    public List<Billing> getAllBillingLogs(String ext) {
		if (ext != null) return billingService.getAllbillingLogs(ext);
		else return null;
	}
	
	@GetMapping(value = "rest/generateElec")
    public int generateElecBill(String efl, String efr, String ero, String epp, String edi,
			String eof, String eto, String erp, String epe, String eadj, Authentication authentication) {
		if (efl == null || efr == null || ero == null || epp == null || edi == null || eof == null || eto == null
				|| erp == null || epe == null || eadj == null )
			return 0;
		
		activityService.log("Electricity bill generated for Office "+eof+" at "+efl+" Floor. (From:"+efr+" To:"+eto+")", authentication.getName());
		billingService.generateBill(efl, efr, ero, epp, edi, eof, eto, erp, epe, eadj, "kWh");
		return 1;
	}
	
	@GetMapping(value = "rest/generateAC")
    public int generateACBill(String afl, String afr, String aro, String app, String adi,
			String aof, String ato, String arp, String ape, String aadj, Authentication authentication) {
		if (afl == null || afr == null || aro == null || app == null || adi == null || aof == null || ato == null
				|| arp == null || ape == null || aadj == null)
			return 0;
		
		activityService.log("Air Conditioning bill generated for Office "+aof+" at "+afl+" Floor. (From:"+afr+" To:"+ato+")", authentication.getName());
		billingService.generateBill(afl, afr, aro, app, adi, aof, ato, arp, ape, aadj, "BTU");
		return 1;
	}
	
	@GetMapping(value = "rest/getDisabledDates")
    public List<Map<String, String>> getDisabledDates(String id, String unit, String ext) {
		return billingService.getDisabledDates(id, unit, ext);
	}
	
	@GetMapping(value = "rest/getCustomerInformation")
    public Map<String, String> getCustomerInformation(Authentication authentication, String id, String unit, String ext) {
		return meterConfigurationService.getCustomerInformation(authentication, id, unit, ext);
	}
	
	@GetMapping(value = "rest/getUsers")
    public List<User> getUsers(Authentication authentication) {
		return userService.getUsers(authentication);
	}
	
	@GetMapping(value = "rest/getUser")
    public User getUser(Authentication authentication, long id) {
		return userService.getUser(authentication, id);
	}
	@GetMapping(value = "rest/getProfile")
    public User getProfile(Authentication authentication) {
		return userService.getProfile(authentication);
	}
	@GetMapping(value = "rest/getInitials")
    public Map<String, String> getInitials(Authentication authentication) {
		return userService.getInitials(authentication);
	}
	@GetMapping(value = "rest/rank")
    public User rank(Authentication authentication, String uname, String role) {
		return userService.rank(authentication, uname, role);
	}
	@GetMapping(value = "rest/enable")
    public User enable(Authentication authentication, String uname, String flag) {
		return userService.enable(authentication, uname, flag);
	}
	@GetMapping(value = "rest/getAllCustomers")
    public List<Customer> getCustomers(Authentication authentication) {
		return customerService.getCustomers(authentication);
	}
	@GetMapping(value = "rest/assignCustomer")
    public MeterConfiguration assignCustomer(Authentication authentication, String id, String unit, int meter, String ext, long customerId) {
		return customerService.assignCustomer(authentication, id, unit, meter, ext, customerId);
	}
	
	@PostMapping(value = "rest/setSystemConfig")
    public List<Configuration> setSystemConfig(Authentication authentication,@RequestBody  String data) {
		configurationService.setConfigurations(authentication, data);
		
		activityService.log("System configuration updated", authentication.getName());
		return configurationService.getAllConfigurations();
	}
	@PostMapping(value = "rest/setBillPropsMK1")
    public List<List<BillingProperties>> setBillPropsMK1(Authentication authentication,@RequestBody  String data) {
		billingPropertiesService.setConfigurations(authentication, data);
		
		activityService.log("System common billing properties updated", authentication.getName());
		return billingPropertiesService.getBillingProperties("kWh");
	}
	@PostMapping(value = "rest/setElecRatesPeak")
    public List<List<BillingProperties>> setElecRatesPeak(Authentication authentication,@RequestBody  String data) {
		billingPropertiesService.setRatesPeak(authentication, data, "kWh");
		
		activityService.log("Electricity bill peak rates updated", authentication.getName());
		return billingPropertiesService.getBillingProperties("kWh");
	}
	@PostMapping(value = "rest/setElecRatesOffPeak")
    public List<List<BillingProperties>> setElecRatesOffPeak(Authentication authentication,@RequestBody  String data) {
		billingPropertiesService.setRatesOffPeak(authentication, data, "kWh");
		
		activityService.log("Electricity bill off peak rates updated", authentication.getName());
		return billingPropertiesService.getBillingProperties("kWh");
	}
    @PostMapping(value = "rest/setAirconRatesPeak")
    public List<List<BillingProperties>> setAirconRatesPeak(Authentication authentication,@RequestBody  String data) {
		billingPropertiesService.setRatesPeak(authentication, data, "BTU");
		
		activityService.log("Air conditioning bill peak rates updated", authentication.getName());
		return billingPropertiesService.getBillingProperties("BTU");
	}
    @PostMapping(value = "rest/setAirconRatesOffPeak")
    public List<List<BillingProperties>> setAirconRatesOffPeak(Authentication authentication,@RequestBody  String data) {
		billingPropertiesService.setRatesOffPeak(authentication, data, "BTU");
		
		activityService.log("Air conditioning bill off peak rates updated", authentication.getName());
		return billingPropertiesService.getBillingProperties("BTU");
	}
    @PostMapping(value = "rest/setElecDiscounts")
    public List<List<BillingProperties>> setElecDiscounts(Authentication authentication,@RequestBody  String data) {
		billingPropertiesService.setDiscounts(authentication, data, "kWh");
		
		activityService.log("Electricity bill discounts updated", authentication.getName());
		return billingPropertiesService.getBillingProperties("kWh");
	}
    @PostMapping(value = "rest/setAirconDiscounts")
    public List<List<BillingProperties>> setAirconDiscounts(Authentication authentication,@RequestBody  String data) {
		billingPropertiesService.setDiscounts(authentication, data, "BTU");
		
		activityService.log("Air conditioning bill discounts updated", authentication.getName());
		return billingPropertiesService.getBillingProperties("BTU");
	}
    @PostMapping(value = "rest/setElecPenalties")
    public List<List<BillingProperties>> setElecPenalties(Authentication authentication,@RequestBody  String data) {
		billingPropertiesService.setPenalties(authentication, data, "kWh");
		
		activityService.log("Electricity bill penalties updated", authentication.getName());
		return billingPropertiesService.getBillingProperties("kWh");
	}
    @PostMapping(value = "rest/setAirconPenalties")
    public List<List<BillingProperties>> setAirconPenalties(Authentication authentication,@RequestBody  String data) {
		billingPropertiesService.setPenalties(authentication, data, "BTU");
		
		activityService.log("Air conditioning bill penalties updated", authentication.getName());
		return billingPropertiesService.getBillingProperties("BTU");
	}
    @GetMapping(value = "rest/getActivities")
    public List<Activity> getActivities(Authentication authentication) {
    	return activityService.getActivities(authentication);
	}
    
}
