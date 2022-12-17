package com.sencillo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company
{

	@Id
	private Integer id;
	
	@Column(name="cname", nullable=false)
	private String name;
	
	@Column(name="proprietor", nullable=false)
	private String proprietor;
	
	@Column(name="business_info", nullable=false)
	private String businessInfo;
	
	@Column(name="address", nullable=false)
	private String address;
	
	@Column(name="contact_info", nullable=false)
	private String contactInfo;
	
	private byte[] banner; 
	
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getProprietor()
	{
		return proprietor;
	}
	public void setProprietor(String proprietor)
	{
		this.proprietor = proprietor;
	}
	public String getBusinessInfo()
	{
		return businessInfo;
	}
	public void setBusinessInfo(String businessInfo)
	{
		this.businessInfo = businessInfo;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getContactInfo()
	{
		return contactInfo;
	}
	public void setContactInfo(String contactInfo)
	{
		this.contactInfo = contactInfo;
	}
	public byte[] getBanner()
	{
		return banner;
	}
	public void setBanner(byte[] banner)
	{
		this.banner = banner;
	}
	
	
	
}
