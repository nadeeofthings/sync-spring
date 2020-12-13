package com.stardust.sync.model;

import javax.persistence.*;

@Entity
@Table(name = "meter_configuration")
public class MeterConfiguration {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int no;
	
	private String id;
    private String unit;
    private int meter;
    private String ext;
    private int address;

    public MeterConfiguration() {
    }

	public MeterConfiguration(String id, String unit, int meter, int address) {
		super();
		this.id = id;
		this.unit = unit;
		this.meter = meter;
		this.address = address;
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