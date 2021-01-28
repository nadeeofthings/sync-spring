package com.stardust.sync.init;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.stardust.sync.core.Constants;
import com.stardust.sync.core.ModbusSingleton;
import com.stardust.sync.model.Configuration;
import com.stardust.sync.model.Meter;
import com.stardust.sync.model.MeterConfiguration;
import com.stardust.sync.model.Role;
import com.stardust.sync.model.User;
import com.stardust.sync.repository.ConfigRepository;
import com.stardust.sync.repository.MeterConfigRepository;
import com.stardust.sync.repository.MeterRepository;
import com.stardust.sync.repository.RoleRepository;
import com.stardust.sync.repository.UserRepository;
import com.stardust.sync.service.MeterConfigurationService;
import com.stardust.sync.service.MeterService;
import com.stardust.sync.service.UserService;


@Component
public class InitialdataLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	boolean alreadySetup = false;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private MeterRepository meterRepository;
	
	@Autowired
    private MeterConfigurationService meterConfigurationService;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    private UserService userService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(alreadySetup)
			return;
		
		
		createRoleIfNotFound("ROLE_SUPERADMIN");
		createRoleIfNotFound("ROLE_ADMIN");
		createRoleIfNotFound("ROLE_USER");
		
		
		
		//configRepository.save(entity)
		
		
		//createSuAdminIfNotFound();
		//repairDB();
		
		alreadySetup = true;
	}
	
	private void repairDB() {
		System.out.println("sdfsefffffffffffffffffffsefs");
		double maximum = 0.0;
		List<MeterConfiguration> configs = meterConfigurationService.getAllMeterConfigurations();
		for(MeterConfiguration mConfiguration : configs) {
		maximum=0.0;
		System.out.println("Current: "+mConfiguration.getId()+" "+mConfiguration.getUnit());	
		List<Meter> meterList = meterRepository.findAllByIdAndUnitAndExtAndMeterOrderByTimeStampAsc(mConfiguration.getId(),mConfiguration.getUnit(),mConfiguration.getExt(),mConfiguration.getMeter());
		//List<Meter> meterList = meterRepository.findAllByIdAndUnitAndExtAndMeterOrderByTimeStampAsc("5th","2","kWh",1);
			for (Meter meter: meterList) {
				 //System.out.println(meter.getId()+" "+meter.getUnit()+"  "+meter.getValue()+meter.getExt());
				System.out.println(meter.getValue()+" >="+ maximum);
				 if(meter.getValue()>=maximum) {
					 maximum=meter.getValue();
				 }else {
					 meter.setValue(maximum);
					 meterRepository.save(meter);
				 }
				 
				 
			 }
			}
		System.out.println("\n\n\n");
	}
	
	
    @Transactional
    private Role createRoleIfNotFound(
      String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
        return role;
    }
    
    @Transactional
    private void createSuAdminIfNotFound() {
    	if (userService.findByUsername("superadmin") != null) 
    		return;
    	
    	Role suAdminRole = roleRepository.findByName("ROLE_SUPERADMIN");
		User user = new User();
		user.setUsername("superadmin");
		user.setPassword(bCryptPasswordEncoder.encode("testing12345"));
		user.setRole(suAdminRole);
		user.setEnabled(true);
		userRepository.save(user);
        return;
    }
}
