package com.starduct.sync.service;

import com.starduct.sync.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
