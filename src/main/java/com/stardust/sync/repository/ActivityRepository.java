package com.stardust.sync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stardust.sync.model.Activity;


public interface ActivityRepository extends JpaRepository<Activity, String> {

	List<Activity> findByUserId(Long id);
}