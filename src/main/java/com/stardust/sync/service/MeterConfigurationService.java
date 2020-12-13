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
    
    public List<MeterConfiguration> getMeterConfigurationByIdAndUnit(String id, String unit) {
		return meterConfigRepository.findAllByIdAndUnit(id, unit);
    }
    
    public List<MeterConfiguration> getAllMeterConfigurations (){
    	return meterConfigRepository.findAll();
    }
}
