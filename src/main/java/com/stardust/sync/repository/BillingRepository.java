package com.stardust.sync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stardust.sync.model.Billing;

public interface BillingRepository extends JpaRepository<Billing, String> {

	Billing findTopByEnableOrderByBillIdDesc(int enable);

	Billing findTopByIdAndUnitAndMeterAndEnableOrderByTimestampDesc(String id, String unit, int meter, int enable);

	List<Billing> findAllByExtAndEnableOrderByTimestampDesc(String ext, int enable);

	List<Billing> findTop2ByIdAndUnitAndMeterAndEnableOrderByTimestampDesc(String id, String unit, int meter, int enable);

	Billing findTopByBillIdAndEnable(long billId, int enable);

	List<Billing> findAllByIdAndUnitAndMeterAndExtAndEnableOrderByTimestampDesc(String id, String unit, int meter, String ext, int flagEnabled);

}
