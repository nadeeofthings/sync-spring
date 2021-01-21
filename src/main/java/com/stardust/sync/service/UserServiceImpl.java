package com.stardust.sync.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.stardust.sync.model.MeterExtended;
import com.stardust.sync.model.Role;
import com.stardust.sync.model.User;
import com.stardust.sync.repository.RoleRepository;
import com.stardust.sync.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;

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
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRole(userRole);
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

    public List<User> findAllEnabled(boolean enabled) {
        return userRepository.findAllByEnabled(enabled);
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

	@Override
	public List<User> getUsers(Authentication authentication) {
		List<User> usrOut = new ArrayList<User>();
		List<User> usr = findAllEnabled(true);
		for (User user_ : usr) {
			user_.setPassword(null);
			if(!user_.getUsername().contains("superadmin")) {
				usrOut.add(user_);
			}
		}
		if(authentication.getAuthorities().toString().contains("ADMIN")) {
			usr = findAllEnabled(false);
			for (User user_ : usr) {
				user_.setPassword(null);
				usrOut.add(user_);
			}
		}
		return usrOut;
	}

	public User findByIdAndEnabled(Long id, boolean enabled) {
		return userRepository.findByIdAndEnabled(id, enabled);
	}
	@Override
	public User getUser(Authentication authentication, long id){
		User usrOut = findByIdAndEnabled(id, true);
		if(usrOut!=null && !usrOut.getUsername().contains("superadmin")) {
			//Active user
			usrOut.setPassword(null);
			return usrOut;
		}else {
			//disabled user
			User usr = findByIdAndEnabled(id, false);
			if(authentication.getAuthorities().toString().contains("ADMIN") && usr!=null) {
				usr.setPassword(null);
				return usr;
			}else {
				return new User();
			}
		}
	}

	@Override
	public Map<String, String> getInitials(Authentication authentication) {
		User usr = findByUsername(authentication.getName());
		Map<String, String> list= new HashMap<String, String>();
		list.put("initials",""+((usr.getFirstname()!=null) ? usr.getFirstname():"#").charAt(0)+((usr.getFirstname()!=null) ? usr.getLastname():"#").charAt(0));
		return list;
	}

	@Override
	public User rank(Authentication authentication, String uname, String role) {
		User usr = findByUsername(uname);
		if(authentication.getAuthorities().toString().contains("ROLE_SUPERADMIN") && usr!=null) {
			Role roler =(role.contains("ADMIN")) ? roleRepository.findByName("ROLE_ADMIN") : roleRepository.findByName("ROLE_USER");
			usr.setRole(roler);
			userRepository.save(usr);
			return usr;
		}else {
			return new User();
		}
	}

	@Override
	public User enable(Authentication authentication, String uname, String flag) {
		User usr = findByUsername(uname);
		if((authentication.getAuthorities().toString().contains("ROLE_SUPERADMIN")||authentication.getAuthorities().toString().contains("ROLE_ADMIN")) && usr!=null) {
			usr.setEnabled((flag.contains("ENABLE")?true:false));
			userRepository.save(usr);
			return usr;
		}else {
			return new User();
		}
	}
	
}
