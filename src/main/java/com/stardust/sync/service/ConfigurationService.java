package com.stardust.sync.service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
