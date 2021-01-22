package com.stardust.sync.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;

import com.stardust.sync.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

	List<User> findAll();

	void updateAlertCount();

	void saveAll(List<User> allUsers);

	long getAlertCountByUsername(String username);

	void resetAlertCountByUsername(String name);

	List<User> getUsers(Authentication authentication);

	User getUser(Authentication authentication, long id);

	Map<String, String> getInitials(Authentication authentication);

	User rank(Authentication authentication, String uname, String role);

	User enable(Authentication authentication, String uname, String flag);

	User getProfile(Authentication authentication);

	User findById(Long id);
	
}
