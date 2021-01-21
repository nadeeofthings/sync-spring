package com.stardust.sync.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.stardust.sync.model.Customer;
import com.stardust.sync.model.MeterConfiguration;
import com.stardust.sync.model.User;
import com.stardust.sync.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MeterConfigurationService meterConfigurationService;
	
	public String save(Customer customer) {
		customerRepository.save(customer);
		return "success";
	}

	public Customer findByName(String name) {
		return customerRepository.findByName(name);
	}
	
	public Customer findById(long id) {
		return customerRepository.findById(id)
		        .orElseThrow(() -> new EntityNotFoundException());
	}

	public List<Customer> getCustomers(Authentication authentication) {
		if((authentication.getAuthorities().toString().contains("ROLE_SUPERADMIN")||authentication.getAuthorities().toString().contains("ROLE_ADMIN"))) {
			return customerRepository.findAllByOrderByNameAsc();
		}else {
			return new ArrayList<Customer>();
		}
		
	}

	public MeterConfiguration assignCustomer(Authentication authentication, String id, String unit, int meter, String ext, long customerId) {
		if((authentication.getAuthorities().toString().contains("ROLE_SUPERADMIN")||authentication.getAuthorities().toString().contains("ROLE_ADMIN"))) {
			MeterConfiguration config = meterConfigurationService.getMeterConfigurationByIdAndUnitAndMeterAndExt(id, unit, meter, ext);
			Customer customer = findById(customerId);
			config.setCustomer(customer);
			meterConfigurationService.saveConfiguration(config);
			return config;
		}else {
			return new MeterConfiguration();
		}
	}
}
