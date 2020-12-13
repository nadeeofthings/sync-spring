 package com.stardust.sync.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stardust.sync.core.ModbusSingleton;
import com.stardust.sync.model.Alert;
import com.stardust.sync.model.Meter;
import com.stardust.sync.model.MeterConfiguration;
import com.stardust.sync.model.MeterExtended;
import com.stardust.sync.service.AlertService;
import com.stardust.sync.service.MeterConfigurationService;
import com.stardust.sync.service.MeterService;
import com.stardust.sync.service.UserService;

import de.re.easymodbus.modbusclient.*;

import java.io.IOException;
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
	MeterConfigurationService meterConfigurationService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AlertService alertService;
	
	@GetMapping(value = "rest/reading")
    public List<Meter> getReadings(String id, String unit) {
		
		
       List<Meter> meters = new ArrayList<Meter>();
       
       List<MeterConfiguration> configs= meterConfigurationService.getMeterConfigurationByIdAndUnit(id, unit);
       
       for(MeterConfiguration config : configs) {
    	   System.out.println(config.getAddress());
    	   meters.add(new Meter(id,unit,1,meterService.getValue(config.getAddress()),"kWh", new Date(),false));
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
    public void resetAlertCount(Authentication authentication) {
		userService.resetAlertCountByUsername(authentication.getName());
	}
	
	@GetMapping(value = "rest/getTop3Alerts")
    public List<Alert> getTop3Alerts() {
		return alertService.findTop3OrderByTimeStampDesc();
		
	}
	
	@GetMapping(value = "rest/getAllAlerts")
    public List<Alert> getAllAlerts() {
		return alertService.findAll();
		
	}
	 
}
