package com.stardust.sync.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@IdClass(BillingId.class)
@Table(name = "billing")
public class Billing {
    @Id
    @Column(length = 20)
    @GeneratedValue
    private long billId;
    @Id
    @Column(length = 20)
    private String id;
    @Id
    @Column(length = 10)
    private String unit;
    @Id
    private int meter;
    @Id
    @Column(length = 10)
    private String ext;
    @Id
    private Date fromDate;
    @Id
    private Date toDate;
    @Id
    private Date timestamp;
    
    @Column(precision = 20, scale = 2)
    private BigDecimal peakUseage;
    @Column(precision = 20, scale = 2)
    private BigDecimal offPeakUseage;
    @Column(precision = 20, scale = 2)
    private BigDecimal peakrate;
    @Column(precision = 20, scale = 2)
    private BigDecimal offPeakRate;
    @Column(precision = 20, scale = 2)
    private BigDecimal penalty;
    @Column(precision = 20, scale = 2)
    private BigDecimal discount;
    @Column(precision = 20, scale = 2)
    private BigDecimal adjustments;
    @Column(precision = 20, scale = 2)
    private BigDecimal balance;
    @Column(precision = 20, scale = 2)
    private BigDecimal payment;
    private int enable;
    
    public Billing() {}

	public Billing(String id, String unit, int meter, String ext, Date fromDate, Date toDate, Date timestamp,
			BigDecimal peakUseage, BigDecimal offPeakUseage, BigDecimal peakrate, BigDecimal offPeakRate,
			BigDecimal penalty, BigDecimal discount, BigDecimal adjustments, BigDecimal balance, BigDecimal payment,
			int enable) {
		super();
		this.id = id;
		this.unit = unit;
		this.meter = meter;
		this.ext = ext;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.timestamp = timestamp;
		this.peakUseage = peakUseage;
		this.offPeakUseage = offPeakUseage;
		this.peakrate = peakrate;
		this.offPeakRate = offPeakRate;
		this.penalty = penalty;
		this.discount = discount;
		this.adjustments = adjustments;
		this.balance = balance;
		this.payment = payment;
		this.enable = enable;
	}

	public long getBillId() {
		return billId;
	}

	public void setBillId(long billId) {
		this.billId = billId;
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

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getPeakUseage() {
		return peakUseage;
	}

	public void setPeakUseage(BigDecimal peakUseage) {
		this.peakUseage = peakUseage;
	}

	public BigDecimal getOffPeakUseage() {
		return offPeakUseage;
	}

	public void setOffPeakUseage(BigDecimal offPeakUseage) {
		this.offPeakUseage = offPeakUseage;
	}

	public BigDecimal getPeakrate() {
		return peakrate;
	}

	public void setPeakrate(BigDecimal peakrate) {
		this.peakrate = peakrate;
	}

	public BigDecimal getOffPeakRate() {
		return offPeakRate;
	}

	public void setOffPeakRate(BigDecimal offPeakRate) {
		this.offPeakRate = offPeakRate;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getAdjustments() {
		return adjustments;
	}

	public void setAdjustments(BigDecimal adjustments) {
		this.adjustments = adjustments;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}



}