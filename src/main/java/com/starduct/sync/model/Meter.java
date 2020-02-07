package com.starduct.sync.model;

import javax.persistence.*;

@Entity
@Table(name = "meter")
public class Meter {

    @Id
    private String id;

    private String unit;

    private double value;
    
    private String ext;

	public Meter(String id, String unit, double value, String ext) {
		super();
		this.id = id;
		this.unit = unit;
		this.value = value;
		this.ext = ext;
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