package com.starduct.sync.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.starduct.sync.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
