package com.stardust.sync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stardust.sync.model.MeterConfiguration;

public interface MeterConfigRepository extends JpaRepository<MeterConfiguration, String> {
	
	List<MeterConfiguration> findAllByIdAndUnit(String id, String unit);
}
