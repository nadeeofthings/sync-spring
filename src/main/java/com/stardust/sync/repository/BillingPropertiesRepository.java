package com.stardust.sync.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stardust.sync.model.BillingProperties;

public interface BillingPropertiesRepository extends JpaRepository<BillingProperties, String> {
	
	List<BillingProperties> findTop5ByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(String propertyKey, String ext, int enabled);
	BillingProperties findTopByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(String propertyKey, String ext, int enabled);
	List<BillingProperties> findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(String propertyKey, String ext, int enabled);
	List<BillingProperties> findAllByPropertyKeyAndDefaultValAndExtIgnoreCaseContainingAndEnabledOrderByTimestampDesc(String configKeyTAndC, boolean i, String ext, int flagEnabled);
	List<BillingProperties> findAllByPropertyKeyAndExtIgnoreCaseContainingAndEnabledOrderByPropertyValueAsc(String configKeyPeakRate, String ext, int flagEnabled);
}
