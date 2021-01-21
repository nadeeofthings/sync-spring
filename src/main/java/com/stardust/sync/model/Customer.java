package com.stardust.sync.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String address;
	private String nic;
	private String brc;//Bs reg
	private String phone;
	private String email;
	private String business_phone;
	private String business_email;
	
	@OneToMany(mappedBy = "customer")
	private List<MeterConfiguration> meterConfigurations;
	
	public Customer() {}

	public Customer(String name, String address, String nic, String brc, String phone, String email,
			String business_phone, String business_email) {
		super();
		this.name = name;
		this.address = address;
		this.nic = nic;
		this.brc = brc;
		this.phone = phone;
		this.email = email;
		this.business_phone = business_phone;
		this.business_email = business_email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public String getBrc() {
		return brc;
	}

	public void setBrc(String brc) {
		this.brc = brc;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBusiness_phone() {
		return business_phone;
	}

	public void setBusiness_phone(String business_phone) {
		this.business_phone = business_phone;
	}

	public String getBusiness_email() {
		return business_email;
	}

	public void setBusiness_email(String business_email) {
		this.business_email = business_email;
	}
	
	
	
}
