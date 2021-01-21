package com.stardust.sync.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

public class MeterId implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1821770720804318391L;
	private String id;
    private String unit;
    private int meter;
    private Date timeStamp;
    // default constructor
    public MeterId(){}
 
    public MeterId(String id, String unit, int meter, Date timeStamp) {
        this.id = id;
        this.unit = unit;
        this.meter = meter;
        this.timeStamp = timeStamp;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + meter;
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
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
		MeterId other = (MeterId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (meter != other.meter)
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}

    
    
}