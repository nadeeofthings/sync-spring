package com.stardust.sync.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
public class BillingProperties {
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	private String propertyKey;
	private String ext;
	@Lob
	private String propertyValue;
	private int enabled;
	private Date timestamp;
	
	private Boolean defaultVal;
	
	public BillingProperties() {}

	

	public BillingProperties(String propertyKey, String ext, String propertyValue, int enabled, Date timestamp,
			Boolean defaultVal) {
		super();
		this.propertyKey = propertyKey;
		this.ext = ext;
		this.propertyValue = propertyValue;
		this.enabled = enabled;
		this.timestamp = timestamp;
		this.defaultVal = defaultVal;
	}

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public String getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Boolean getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(Boolean defaultVal) {
		this.defaultVal = defaultVal;
	}
	
	
	

}
