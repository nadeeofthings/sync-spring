package com.stardust.sync.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "meter_configuration")
public class MeterConfiguration {
    
	@Id
	private Integer no;
	
	private String id;
    private String unit;
    private int meter;
    private String ext;
    private int address;
    
    @ManyToOne
    private Customer customer;

    public MeterConfiguration() {
    }

	public MeterConfiguration(String id, String unit, int meter, int address, Customer customer) {
		super();
		this.id = id;
		this.unit = unit;
		this.meter = meter;
		this.address = address;
		this.customer = customer;
	}
	
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnit() {
		return unit;
	}
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getMeter() {
		return meter;
	}
	public void setMeter(int meter) {
		this.meter = meter;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	
	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
    

}