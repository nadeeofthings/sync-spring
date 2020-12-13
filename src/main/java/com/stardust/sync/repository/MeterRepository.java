package com.stardust.sync.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stardust.sync.model.Meter;

public interface MeterRepository extends JpaRepository<Meter, String> {
  
	List<Meter> findAllByIdAndUnitOrderByTimeStampDesc(String id, String unit);

	List<Meter> findAllByIdAndUnitAndExtOrderByTimeStampDesc(String id, String unit, String ext);
	
	List<Meter> findAllByIdAndUnitAndExtAndMeterOrderByTimeStampDesc(String id, String unit, String ext, int meter);
	
	List<Meter> findAllByIdAndUnitAndExtAndMeterOrderByTimeStampAsc(String id, String unit, String ext, int meter);
	
	List<Meter> findTop28ByIdAndUnitAndExtAndMeterOrderByTimeStampDesc(String id, String unit, String ext, int meter);
	
	List<Meter> findAllByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(String id, String unit, String ext, int meter, Date start, Date end);
	
	List<Meter> findAllByExtAndTimeStampBetweenOrderByTimeStampDesc(String ext, Date start, Date end);
	
	List<Meter> findAllByExtAndTimeStampBetweenOrderByTimeStampAsc(String ext, Date start, Date end);
	
	Meter findTopByIdAndUnitAndExtAndMeterOrderByTimeStampDesc(String id, String unit, String ext, int meter);
	
	Meter findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(String id, String unit, String ext, int meter, Date start, Date end);
	
	Meter findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampDesc(String id, String unit, String ext, int meter, Date start, Date end);

	
}
