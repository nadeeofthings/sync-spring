package com.stardust.sync.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stardust.sync.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

	List<User> findAllByEnabled(boolean enabled);
	User findByIdAndEnabled(long id, boolean enabled);
}
