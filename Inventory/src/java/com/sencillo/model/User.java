package com.sencillo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	
	@NotBlank
	@Column(name="user_name", nullable = false)
	private String userName;
	
	@NotBlank
	@Column(name="full_name", nullable = false)
	private String fullName;
	
	@NotBlank
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="salt", nullable = false)
	private String salt;
	
	@Column(name="active", nullable = false)
	private boolean active; 

	@ManyToOne
	@JoinColumn(name="role_id", nullable=false)
	private Role role;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt()
	{
		return salt;
	}

	public void setSalt(String salt)
	{
		this.salt = salt;
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	@Override
	public String toString()
	{
		return this.getFullName();
	}


	
}