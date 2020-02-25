package com.stardust.sync.service;

import com.stardust.sync.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
