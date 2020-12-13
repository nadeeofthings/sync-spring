package com.stardust.sync.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.stardust.sync.model.Role;
import com.stardust.sync.model.User;
import com.stardust.sync.repository.RoleRepository;
import com.stardust.sync.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);
        user.setRoles(roles);
        user.setEnabled(false);
        user.setAlertCount(0);
        userRepository.save(user);
    }
    
	@Override
    public void saveAll(List<User> allUsers) {
    	userRepository.saveAll(allUsers);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
	@Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
	
	@Override
    public void updateAlertCount() {
		List<User> allUsers = findAll();
		for(User user : allUsers) {
			user.setAlertCount(user.getAlertCount()+1);
		}
		saveAll(allUsers);
    }
	
	@Override
	public long getAlertCountByUsername(String username) {
		return userRepository.findByUsername(username).getAlertCount();
	}
	

	@Override
	public void resetAlertCountByUsername(String username) {
		User user = findByUsername(username);
		user.setAlertCount(0);
		userRepository.save(user);
		
	}
	
}
