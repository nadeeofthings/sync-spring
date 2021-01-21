package com.stardust.sync.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stardust.sync.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByName(String name);

	List<Customer> findAllByOrderByNameAsc();

	Customer findAllById(long id);

}