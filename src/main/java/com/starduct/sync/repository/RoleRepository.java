package com.starduct.sync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starduct.sync.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(String name);
}
