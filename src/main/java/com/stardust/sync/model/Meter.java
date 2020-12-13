package com.stardust.sync.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@IdClass(UnitId.class)
@Table(name = "meter")
public class Meter implements Comparable {

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
    
    private boolean peak;
    
     
    public Meter() {}
    
	public Meter(String id, String unit, int meter, double value, String ext, Date timeStamp, boolean peak) {
		super();
		this.id = id;
		this.unit = unit;
		this.meter = meter;
		this.value = value;
		this.ext = ext;
		this.timeStamp = timeStamp;
		this.peak = peak;
	}
	
	

	public boolean isPeak() {
		return peak;
	}

	public void setPeak(boolean peak) {
		this.peak = peak;
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

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		 int compareage=(int) ((Meter)o).getValue();
	        /* For Ascending order*/
	     return (int) (compareage-this.value);
	} 


}