package com.stardust.sync.service;

import java.util.List;

import com.stardust.sync.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

	List<User> findAll();

	void updateAlertCount();

	void saveAll(List<User> allUsers);

	long getAlertCountByUsername(String username);

	void resetAlertCountByUsername(String name);
}
