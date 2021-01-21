package com.stardust.sync.service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.stardust.sync.core.Constants;
import com.stardust.sync.model.Billing;
import com.stardust.sync.model.Configuration;
import com.stardust.sync.model.MeterConfiguration;
import com.stardust.sync.repository.ConfigRepository;
import com.stardust.sync.repository.MeterConfigRepository;

/**
 * ConfigurationService is responsible for loading and checking configuration parameters.
 *
 * @author mbcoder
 *
 */
@Service
public class MeterConfigurationService {

    private static final Logger         LOGGER  = LoggerFactory.getLogger(MeterConfigurationService.class);

    @Autowired
    private MeterConfigRepository meterConfigRepository;
    
    public MeterConfiguration getMeterConfigurationByIdAndUnitAndMeterAndExt(String id, String unit, int meter, String ext) {
		return meterConfigRepository.findTopByIdAndUnitAndMeterAndExt(id, unit, meter, ext);
    }
    
    public List<MeterConfiguration> getAllMeterConfigurations (){
    	return meterConfigRepository.findAll();
    }

	public List<MeterConfiguration> getMeterConfigurationsByIdAndUnit(String id, String unit) {
		return meterConfigRepository.findAllByIdAndUnit(id, unit);
	}
	
	public void saveConfiguration(MeterConfiguration config) {
		meterConfigRepository.save(config);
	}
	
    public Map<String, String> getCustomerInformation(Authentication authentication ,String id, String unit, String ext){
    	MeterConfiguration config;
    	Map<String, String> list= new HashMap<String, String>();
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	if(id.equalsIgnoreCase("Ground")) {
    		config = getMeterConfigurationByIdAndUnitAndMeterAndExt(id,  "1", Integer.parseInt(unit), ext);
    	}else {
    		config = getMeterConfigurationByIdAndUnitAndMeterAndExt(id, unit, ((ext.equalsIgnoreCase("kWh")) ? 1 : 2), ext);
    	}
    	Iterator iterator = authentication.getAuthorities().iterator();
    	boolean isAdmin = false;
    	while(iterator.hasNext()){
    	      Object object = iterator.next();
    	      if(object.toString().equalsIgnoreCase("ROLE_ADMIN")) {
    	    	  isAdmin = true;
    	    	  break;
    	      }
    	    }
    	if(isAdmin) {
    		list.put("name", config.getCustomer().getName());
    		list.put("address", config.getCustomer().getAddress());
    		list.put("nic", config.getCustomer().getNic());
    		list.put("brc", config.getCustomer().getBrc());
    		list.put("phone", config.getCustomer().getPhone());
    		list.put("email", config.getCustomer().getEmail());
    		list.put("business_phone", config.getCustomer().getBusiness_phone());
    		list.put("business_email", config.getCustomer().getBusiness_email());
    	}else {
    		list.put("name", config.getCustomer().getName());
    		list.put("business_phone", config.getCustomer().getBusiness_phone());
    		list.put("business_email", config.getCustomer().getBusiness_email());

    	}
    	
    	return list;
    }
}
