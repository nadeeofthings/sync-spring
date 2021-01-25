package com.stardust.sync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stardust.sync.model.Alert;
import com.stardust.sync.model.Meter;

public interface AlertRepository extends JpaRepository<Alert, Long> {
	List<Alert> findTop3ByOrderByTimeStampDesc();

	long countByStatus(int alertStatusPending);

	List<Alert> findAllByOrderByTimeStampDesc();
}