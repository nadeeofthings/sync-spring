package com.stardust.sync.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.stardust.sync.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
