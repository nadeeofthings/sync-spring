package com.starduct.sync.init;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.starduct.sync.model.Role;
import com.starduct.sync.model.User;
import com.starduct.sync.repository.RoleRepository;
import com.starduct.sync.repository.UserRepository;
import com.starduct.sync.service.UserService;

@Component
public class InitialdataLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	boolean alreadySetup = false;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
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
