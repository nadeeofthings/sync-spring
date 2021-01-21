package com.stardust.sync.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stardust.sync.core.Constants;
import com.stardust.sync.model.Configuration;
import com.stardust.sync.repository.ConfigRepository;

/**
 * ConfigurationService is responsible for loading and checking configuration parameters.
 *
 * @author mbcoder
 *
 */
@Service
public class ConfigurationService {

    private static final Logger         LOGGER  = LoggerFactory.getLogger(ConfigurationService.class);
    
    @Autowired
    private ConfigRepository            configRepository;

    private Map<String, Configuration>  configurationList;

    private List<String>                mandatoryConfigs;

    @Autowired
    public ConfigurationService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
        this.configurationList = new ConcurrentHashMap<>();
        this.mandatoryConfigs = new ArrayList<>();
        this.mandatoryConfigs.add(Constants.CONFIG_KEY_REFRESH_RATE_CONFIG);
        this.mandatoryConfigs.add(Constants.CONFIG_KEY_PEAK_START_CONFIG);
        this.mandatoryConfigs.add(Constants.CONFIG_KEY_PEAK_END_CONFIG);
    }

    /**
     * Loads configuration parameters from Database
     */
    @PostConstruct
    public void loadConfigurations() {
        LOGGER.debug("Scheduled Event: Configuration table loaded/updated from database");
        StringBuilder sb = new StringBuilder();
        sb.append("Configuration Parameters:");
        List<Configuration> configs = configRepository.findAll();
        for (Configuration configuration : configs) {
            sb.append("\n" + configuration.getConfigKey() + ":" + configuration.getConfigValue());
            this.configurationList.put(configuration.getConfigKey(), configuration);
        }
        LOGGER.debug(sb.toString());

        checkMandatoryConfigurations();
    }

    public Configuration getConfiguration(String key) {
       System.out.println(configurationList.get(key));
    	
    	return configurationList.get(key);
    }
    
    public Configuration findConfiguration(String key) {
    	return configRepository.findById(key)
        .orElseThrow(() -> new EntityNotFoundException());

     }
    
    
    public List<Configuration> getAllConfigurations() {
     	return configRepository.findAll();
     }
    /**
     * Checks if the mandatory parameters are exists in Database
     */
    public void checkMandatoryConfigurations() {
        for (String mandatoryConfig : mandatoryConfigs) {
            boolean exists = false;
            for (Map.Entry<String, Configuration> pair : configurationList.entrySet()) {
                if (pair.getKey().equalsIgnoreCase(mandatoryConfig) && !pair.getValue().getConfigValue().isEmpty()) {
                    exists = true;
                }
            }
            if (!exists) {
            		switch (mandatoryConfig){
            				case Constants.CONFIG_KEY_REFRESH_RATE_CONFIG:
            					configurationList.put(Constants.CONFIG_KEY_REFRESH_RATE_CONFIG, new Configuration(Constants.CONFIG_KEY_REFRESH_RATE_CONFIG,Constants.CONFIG_KEY_REFRESH_RATE_CONFIG_DEFAULT));
            				case Constants.CONFIG_KEY_PEAK_START_CONFIG:
            					configurationList.put(Constants.CONFIG_KEY_PEAK_START_CONFIG, new Configuration(Constants.CONFIG_KEY_PEAK_START_CONFIG,Constants.CONFIG_KEY_PEAK_START_CONFIG_DEFAULT));
            				case Constants.CONFIG_KEY_PEAK_END_CONFIG:
            					configurationList.put(Constants.CONFIG_KEY_PEAK_END_CONFIG, new Configuration(Constants.CONFIG_KEY_PEAK_END_CONFIG,Constants.CONFIG_KEY_PEAK_END_CONFIG_DEFAULT));
            		}
                String errorLog = String.format("A mandatory Configuration parameter is not found in DB: %s", mandatoryConfig);
                LOGGER.warn(errorLog);
            }
        }

    }

	public int setConfigurations(Authentication authentication, String data) {
		if((authentication.getAuthorities().toString().contains("ROLE_SUPERADMIN")||authentication.getAuthorities().toString().contains("ROLE_ADMIN"))) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String decoded = URLDecoder.decode(data, "UTF-8");
				@SuppressWarnings("unchecked")
				HashMap<String,Object> result = mapper.readValue(decoded, HashMap.class);
				String peakStart = (String)result.get(Constants.CONFIG_KEY_PEAK_START_CONFIG);
				String peakEnd = (String)result.get(Constants.CONFIG_KEY_PEAK_END_CONFIG);
				Pattern pattern = Pattern.compile("([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]");
				if(pattern.matcher(peakStart).matches() && pattern.matcher(peakEnd).matches()) {
					Configuration configPeakStart = findConfiguration(Constants.CONFIG_KEY_PEAK_START_CONFIG);
					configPeakStart.setConfigValue(peakStart);
					Configuration configPeakEnd = findConfiguration(Constants.CONFIG_KEY_PEAK_END_CONFIG);
					configPeakEnd.setConfigValue(peakEnd);
					configRepository.save(configPeakStart);
					configRepository.save(configPeakEnd);
				}
				
			} catch (JsonProcessingException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				LOGGER.error("Error parsing json string "+e);
			}
			return 1;
		}
		
		return 0;
	}

}
