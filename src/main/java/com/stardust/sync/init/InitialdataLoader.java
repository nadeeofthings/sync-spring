package com.stardust.sync.init;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import com.stardust.sync.model.Role;
import com.stardust.sync.model.User;
import com.stardust.sync.repository.ConfigRepository;
import com.stardust.sync.repository.MeterRepository;
import com.stardust.sync.repository.RoleRepository;
import com.stardust.sync.repository.UserRepository;
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
    private ConfigRepository configRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private MeterService meterService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(alreadySetup)
			return;
		
		/*
		 * List<Configuration> configs = new ArrayList<>(); configs.add(new
		 * Configuration(Constants.CONFIG_KEY_REFRESH_RATE_CONFIG,"300"));
		 * configs.add(new
		 * Configuration(Constants.CONFIG_KEY_PEAK_START_CONFIG,"8:00"));
		 * configs.add(new Configuration(Constants.CONFIG_KEY_PEAK_END_CONFIG,"17:00"));
		 * 
		 * configRepository.saveAll(configs);
		 */
		//meterService.capturePeakReadings();
		
		Constants.meters.add(new Meter("Ground", "1", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("Ground", "1", 2, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("Ground", "1", 3, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("Ground", "1", 4, 0, "BTU", new Date(), false));
		
		Constants.meters.add(new Meter("3rd", "1", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("3rd", "1", 2, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("3rd", "2", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("3rd", "2", 2, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("3rd", "3", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("3rd", "3", 2, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("3rd", "4", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("3rd", "4", 2, 0, "BTU", new Date(), false));

		Constants.meters.add(new Meter("4th", "1", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("4th", "1", 2, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("4th", "2", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("4th", "2", 2, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("4th", "3", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("4th", "3", 2, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("4th", "4", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("4th", "4", 2, 0, "BTU", new Date(), false));
		
		Constants.meters.add(new Meter("5th", "1", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("5th", "1", 2, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("5th", "2", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("5th", "2", 2, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("5th", "3", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("5th", "3", 2, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("5th", "4", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("5th", "4", 2, 0, "BTU", new Date(), false));
		
		Constants.meters.add(new Meter("6th", "1", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("6th", "1", 2, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("6th", "2", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("6th", "2", 2, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("6th", "3", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("6th", "3", 2, 0, "BTU", new Date(), false));
		Constants.meters.add(new Meter("6th", "4", 1, 0, "kWh", new Date(), false));
		Constants.meters.add(new Meter("6th", "4", 2, 0, "BTU", new Date(), false));
		
		createRoleIfNotFound("ROLE_SUPERADMIN");
		createRoleIfNotFound("ROLE_ADMIN");
		createRoleIfNotFound("ROLE_USER");
		
		createSuAdminIfNotFound();
		
		alreadySetup = true;
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
		Set<Role> roles = new HashSet<>();
		roles.add(suAdminRole);
		user.setRoles(roles);
		user.setEnabled(true);
		userRepository.save(user);
        return;
    }
}
