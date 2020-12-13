package com.stardust.sync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stardust.sync.model.Alert;
import com.stardust.sync.model.Meter;

public interface AlertRepository extends JpaRepository<Alert, String> {
	List<Alert> findTop3ByOrderByTimeStampDesc();
}