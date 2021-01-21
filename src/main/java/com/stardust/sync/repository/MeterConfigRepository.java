package com.stardust.sync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stardust.sync.model.MeterConfiguration;

public interface MeterConfigRepository extends JpaRepository<MeterConfiguration, String> {
	
	MeterConfiguration findTopByIdAndUnitAndMeterAndExt(String id, String unit, int meter, String ext);

	List<MeterConfiguration> findAllByIdAndUnit(String id, String unit);
}
