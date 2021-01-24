package com.stardust.sync.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stardust.sync.core.Constants;
import com.stardust.sync.model.BillingProperties;
import com.stardust.sync.model.Configuration;
import com.stardust.sync.repository.BillingPropertiesRepository;

@Service
public class BillingPropertiesService {
	private static final Logger         LOGGER  = LoggerFactory.getLogger(ConfigurationService.class);
    
    @Autowired
    private BillingPropertiesRepository            billingPropertiesRepository;
    
    
	public List<List<BillingProperties>> getBillingProperties(String ext) {
		List<List<BillingProperties>> propertyList = new ArrayList<List<BillingProperties>>();;
		//IMPORTANT DONT CHANGE ORDER
		propertyList.add(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_T_AND_C, ext, Constants.FLAG_ENABLED));
		propertyList.add(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_EMERGENCY_CONTACT, ext, Constants.FLAG_ENABLED));
		propertyList.add(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_BILLING_INQUIRIES, ext, Constants.FLAG_ENABLED));
		propertyList.add(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_NBTAX, ext, Constants.FLAG_ENABLED));
		propertyList.add(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_VATAX, ext, Constants.FLAG_ENABLED));
		propertyList.add(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByPropertyValueAsc(Constants.CONFIG_KEY_PEAK_RATE, ext, Constants.FLAG_ENABLED));
		propertyList.add(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByPropertyValueAsc(Constants.CONFIG_KEY_OFF_PEAK_RATE, ext, Constants.FLAG_ENABLED));
		propertyList.add(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByPropertyValueAsc(Constants.CONFIG_KEY_PENALTY, ext, Constants.FLAG_ENABLED));
		propertyList.add(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByPropertyValueAsc(Constants.CONFIG_KEY_DISCOUNT, ext, Constants.FLAG_ENABLED));
		propertyList.add(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_DUE_DAYS_PERIOD, ext, Constants.FLAG_ENABLED));
		propertyList.add(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_SERVICE_CHARGE, ext, Constants.FLAG_ENABLED));
		return propertyList;
	}
	
	public List<BillingProperties> getBillingProperty (String key, String ext) {
		return billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc( key, ext, Constants.FLAG_ENABLED);
	}
	
	public HashMap<String, String> getMandatoryBillingProperties (String ext) {
		HashMap<String, String> mandatoryBillingProperties = new HashMap<String, String>();
		//IMPORTANT DONT CHANGE ORDER
		mandatoryBillingProperties.put(Constants.CONFIG_KEY_T_AND_C, billingPropertiesRepository.findTopByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_T_AND_C, ext, Constants.FLAG_ENABLED).getPropertyValue());
		mandatoryBillingProperties.put(Constants.CONFIG_KEY_EMERGENCY_CONTACT, billingPropertiesRepository.findTopByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_EMERGENCY_CONTACT, ext, Constants.FLAG_ENABLED).getPropertyValue());
		mandatoryBillingProperties.put(Constants.CONFIG_KEY_BILLING_INQUIRIES, billingPropertiesRepository.findTopByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_BILLING_INQUIRIES, ext, Constants.FLAG_ENABLED).getPropertyValue());
		mandatoryBillingProperties.put(Constants.CONFIG_KEY_NBTAX, billingPropertiesRepository.findTopByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_NBTAX, ext, Constants.FLAG_ENABLED).getPropertyValue());
		mandatoryBillingProperties.put(Constants.CONFIG_KEY_VATAX, billingPropertiesRepository.findTopByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_VATAX, ext, Constants.FLAG_ENABLED).getPropertyValue());
		mandatoryBillingProperties.put(Constants.CONFIG_KEY_DUE_DAYS_PERIOD, billingPropertiesRepository.findTopByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_DUE_DAYS_PERIOD, ext, Constants.FLAG_ENABLED).getPropertyValue());
		mandatoryBillingProperties.put(Constants.CONFIG_KEY_SERVICE_CHARGE, billingPropertiesRepository.findTopByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_SERVICE_CHARGE, ext, Constants.FLAG_ENABLED).getPropertyValue());
		return mandatoryBillingProperties;
	}
	
	public void disablePreviousMandatoryBillingProperties (String ext) {
		List<BillingProperties> conf = new ArrayList<BillingProperties>();
		
		//IMPORTANT DONT CHANGE ORDER
		conf.addAll(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_T_AND_C, ext, Constants.FLAG_ENABLED));
		conf.addAll(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_EMERGENCY_CONTACT, ext, Constants.FLAG_ENABLED));
		conf.addAll(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_BILLING_INQUIRIES, ext, Constants.FLAG_ENABLED));
		conf.addAll(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_NBTAX, ext, Constants.FLAG_ENABLED));
		conf.addAll(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_VATAX, ext, Constants.FLAG_ENABLED));
		conf.addAll(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_DUE_DAYS_PERIOD, ext, Constants.FLAG_ENABLED));
		conf.addAll(billingPropertiesRepository.findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(Constants.CONFIG_KEY_SERVICE_CHARGE, ext, Constants.FLAG_ENABLED));
		for(BillingProperties prop : conf) {
			prop.setEnabled(0);
		}
		billingPropertiesRepository.saveAll(conf);
			
		//return mandatoryBillingProperties;
	}
	
	public void disablePreviousMandatoryBillingProperties (String key, String ext) {
		List<BillingProperties> conf = new ArrayList<BillingProperties>();
		
		//IMPORTANT DONT CHANGE ORDER
		conf.addAll(billingPropertiesRepository.findAllByPropertyKeyAndDefaultValAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(key, false, ext, Constants.FLAG_ENABLED));
		for(BillingProperties prop : conf) {
			prop.setEnabled(0);
		}
		billingPropertiesRepository.saveAll(conf);
			
		//return mandatoryBillingProperties;
	}

	public void setConfigurations(Authentication authentication, String data) {
			if((authentication.getAuthorities().toString().contains("ROLE_SUPERADMIN")||authentication.getAuthorities().toString().contains("ROLE_ADMIN"))) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					String decoded = URLDecoder.decode(data, "UTF-8");
					@SuppressWarnings("unchecked")
					HashMap<String,String> result = mapper.readValue(decoded, HashMap.class);

					String serviceCharge = result.get(Constants.CONFIG_KEY_SERVICE_CHARGE);
			   		String dueDaysPeriod = result.get(Constants.CONFIG_KEY_DUE_DAYS_PERIOD);
			   		String nbtax = result.get(Constants.CONFIG_KEY_NBTAX);
			   		String vatax = result.get(Constants.CONFIG_KEY_VATAX);
			   		String emergencyContact = result.get(Constants.CONFIG_KEY_EMERGENCY_CONTACT);
			   		String billingInquiries = result.get(Constants.CONFIG_KEY_BILLING_INQUIRIES);
			   		String tandc = result.get(Constants.CONFIG_KEY_T_AND_C);
					
					
					
					
 				  if(serviceCharge != null && dueDaysPeriod != null && nbtax != null && vatax != null && emergencyContact != null &&
 						 billingInquiries != null && tandc != null) {
 					 List<BillingProperties> conf = new ArrayList<BillingProperties>();
 					 long timestamp = new Date().getTime();
 					conf.add(new BillingProperties(Constants.CONFIG_KEY_SERVICE_CHARGE, "kWhBTU", serviceCharge, Constants.FLAG_ENABLED, new Date(timestamp), false));
 					conf.add(new BillingProperties(Constants.CONFIG_KEY_DUE_DAYS_PERIOD, "kWhBTU", dueDaysPeriod, Constants.FLAG_ENABLED, new Date(timestamp), false));
 					conf.add(new BillingProperties(Constants.CONFIG_KEY_NBTAX, "kWhBTU", nbtax, Constants.FLAG_ENABLED, new Date(timestamp), false));
 					conf.add(new BillingProperties(Constants.CONFIG_KEY_VATAX, "kWhBTU", vatax, Constants.FLAG_ENABLED, new Date(timestamp), false));
 					conf.add(new BillingProperties(Constants.CONFIG_KEY_EMERGENCY_CONTACT, "kWhBTU", emergencyContact, Constants.FLAG_ENABLED, new Date(timestamp), false));
 					conf.add(new BillingProperties(Constants.CONFIG_KEY_BILLING_INQUIRIES, "kWhBTU", billingInquiries, Constants.FLAG_ENABLED, new Date(timestamp), false));
 					conf.add(new BillingProperties(Constants.CONFIG_KEY_T_AND_C, "kWhBTU", tandc, Constants.FLAG_ENABLED, new Date(timestamp), false));
 					    disablePreviousMandatoryBillingProperties("kWhBTU");
						billingPropertiesRepository.saveAll(conf);
					}
					
				} catch (JsonProcessingException | UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					LOGGER.error("Error parsing json string "+e);
				}
			}
			
		}

	public void setRatesPeak(Authentication authentication, String data, String ext) {
		if((authentication.getAuthorities().toString().contains("ROLE_SUPERADMIN")||authentication.getAuthorities().toString().contains("ROLE_ADMIN"))) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String decoded = URLDecoder.decode(data, "UTF-8");
				String[] result = mapper.readValue(decoded, String[].class);
				List<BillingProperties> conf = new ArrayList<BillingProperties>();
				long timestamp = new Date().getTime();
				for(String value : result) {
					if(value != null)
					conf.add(new BillingProperties(Constants.CONFIG_KEY_PEAK_RATE, ext, value, Constants.FLAG_ENABLED, new Date(timestamp), false));
				}
				if(!conf.isEmpty()) {
					disablePreviousMandatoryBillingProperties(Constants.CONFIG_KEY_PEAK_RATE, ext);
					billingPropertiesRepository.saveAll(conf);
				}
				
				
			} catch (JsonProcessingException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				LOGGER.error("Error parsing json string "+e);
			}
		}
		
		
	}

	public void setRatesOffPeak(Authentication authentication, String data, String ext) {
		if((authentication.getAuthorities().toString().contains("ROLE_SUPERADMIN")||authentication.getAuthorities().toString().contains("ROLE_ADMIN"))) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String decoded = URLDecoder.decode(data, "UTF-8");
				String[] result = mapper.readValue(decoded, String[].class);
				List<BillingProperties> conf = new ArrayList<BillingProperties>();
				long timestamp = new Date().getTime();
				for(String value : result) {
					if(value != null)
					conf.add(new BillingProperties(Constants.CONFIG_KEY_OFF_PEAK_RATE, ext, value, Constants.FLAG_ENABLED, new Date(timestamp), false));
				}
				if(!conf.isEmpty()) {
					disablePreviousMandatoryBillingProperties(Constants.CONFIG_KEY_OFF_PEAK_RATE, ext);
					billingPropertiesRepository.saveAll(conf);
				}
				
				
			} catch (JsonProcessingException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				LOGGER.error("Error parsing json string "+ext+" "+e);
			}
		}
		
	}

	public void setDiscounts(Authentication authentication, String data, String ext) {
		if((authentication.getAuthorities().toString().contains("ROLE_SUPERADMIN")||authentication.getAuthorities().toString().contains("ROLE_ADMIN"))) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String decoded = URLDecoder.decode(data, "UTF-8");
				String[] result = mapper.readValue(decoded, String[].class);
				List<BillingProperties> conf = new ArrayList<BillingProperties>();
				long timestamp = new Date().getTime();
				for(String value : result) {
					if(value != null)
					conf.add(new BillingProperties(Constants.CONFIG_KEY_DISCOUNT, ext, value, Constants.FLAG_ENABLED, new Date(timestamp), false));
				}
				if(!conf.isEmpty()) {
					disablePreviousMandatoryBillingProperties(Constants.CONFIG_KEY_DISCOUNT, ext);
					billingPropertiesRepository.saveAll(conf);
				}
				
				
			} catch (JsonProcessingException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				LOGGER.error("Error parsing json string "+ext+" "+e);
			}
		}
		
	}

	public void setPenalties(Authentication authentication, String data, String ext) {
		if((authentication.getAuthorities().toString().contains("ROLE_SUPERADMIN")||authentication.getAuthorities().toString().contains("ROLE_ADMIN"))) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String decoded = URLDecoder.decode(data, "UTF-8");
				String[] result = mapper.readValue(decoded, String[].class);
				List<BillingProperties> conf = new ArrayList<BillingProperties>();
				long timestamp = new Date().getTime();
				for(String value : result) {
					if(value != null)
					conf.add(new BillingProperties(Constants.CONFIG_KEY_PENALTY, ext, value, Constants.FLAG_ENABLED, new Date(timestamp), false));
				}
				if(!conf.isEmpty()) {
					disablePreviousMandatoryBillingProperties(Constants.CONFIG_KEY_PENALTY, ext);
					billingPropertiesRepository.saveAll(conf);
				}
				
				
			} catch (JsonProcessingException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				LOGGER.error("Error parsing json string "+ext+" "+e);
			}
		}
		
	}
}
