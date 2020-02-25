package com.stardust.sync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stardust.sync.model.Meter;

public interface MeterRepository extends JpaRepository<Meter, String> {
  
	List<Meter> findAllByIdAndUnit(String id, String unit);

	List<Meter> findAllByIdAndUnitAndExt(String id, String unit, String ext);
}
