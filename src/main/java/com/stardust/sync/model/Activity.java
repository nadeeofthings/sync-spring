package com.stardust.sync.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "activity")
public class Activity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Lob
	private String message;
	private Date timeStamp;
	
	private long userId;
	
	@Transient
	private User user;
	
	public Activity() {};

	public Activity(String message, Date timeStamp, long userId) {
		super();
		this.message = message;
		this.timeStamp = timeStamp;
		this.userId = userId;
	}
	
	public Activity(String message, Date timeStamp, User user) {
		super();
		this.message = message;
		this.timeStamp = timeStamp;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
