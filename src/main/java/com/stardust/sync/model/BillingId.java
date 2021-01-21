package com.stardust.sync.model;

import java.io.Serializable;
import java.util.Date;


public class BillingId implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7377553144357047025L;
	/**
	 * 
	 */
	private long billId;
    private String id;
    private String unit;
    private int meter;
    private String ext;
    private Date fromDate;
    private Date toDate;
    private Date timestamp;
    
    // default constructor
    public BillingId() {}

	public BillingId(long billId, String id, String unit, int meter, String ext, Date fromDate, Date toDate, Date timestamp) {
		this.billId = billId;
		this.id = id;
		this.unit = unit;
		this.meter = meter;
		this.ext = ext;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.timestamp = timestamp;
	}
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result +  billId);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result +  meter;
		result = prime * result + ((ext == null) ? 0 : ext.hashCode());
		result = prime * result + ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result + ((toDate == null) ? 0 : toDate.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillingId other = (BillingId) obj;
		if (billId != other.billId)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (meter != other.meter)
			return false;
		if (ext == null) {
			if (other.ext != null)
				return false;
		} else if (!ext.equals(other.ext))
			return false;
		if (fromDate == null) {
			if (other.timestamp != null)
				return false;
		} else if (!fromDate.equals(other.fromDate))
			return false;
		if (toDate == null) {
			if (other.toDate != null)
				return false;
		} else if (!toDate.equals(other.toDate))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

    
}
