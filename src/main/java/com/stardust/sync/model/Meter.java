package com.stardust.sync.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@IdClass(UnitId.class)
@Table(name = "meter")
public class Meter {

    @Id
    private String id;
    @Id
    private String unit;
    @Id
    private int meter;
    @Id
    private Date timeStamp;

	private double value;
    
    private String ext;
    
     
    public Meter() {}
    
	public Meter(String id, String unit, int meter, double value, String ext, Date timeStamp) {
		super();
		this.id = id;
		this.unit = unit;
		this.meter = meter;
		this.value = value;
		this.ext = ext;
		this.timeStamp = timeStamp;
	}

	public int getMeter() {
		return meter;
	}

	public void setMeter(int meter) {
		this.meter = meter;
	}
	
	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	} 


}