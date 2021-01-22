package com.stardust.sync.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstname;
    
    private String lastname;
    
    private String department;
    
    private String phone;
    
    private long alertCount;
    
	private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    @ManyToOne
    private Role role;
    
    private boolean enabled;
    
    public User() {};

  

	public User(String firstname, String lastname, String department, String phone, long alertCount, String username,
			String password, String passwordConfirm, Role role, boolean enabled) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.department = department;
		this.phone = phone;
		this.alertCount = alertCount;
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.role = role;
		this.enabled = enabled;
	}



	public long getAlertCount() {
		return alertCount;
	}

	public void setAlertCount(long alertCount) {
		this.alertCount = alertCount;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

    public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
