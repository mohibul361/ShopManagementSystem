package com.sencillo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="return_item_info")
public class ReturnItemInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "problem_type_id", nullable = true)
	private ProblemType problemType;

	@Column(name = "retailer_name", nullable = true)
	private String retailerName;

	@Column(name = "retailer_code", nullable = true)
	private String retailerCode;

	@Column(name = "retailer_mobile_no", nullable = true)
	private String retailerMobileNo;

	
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public ProblemType getProblemType()
	{
		return problemType;
	}

	public void setProblemType(ProblemType problemType)
	{
		this.problemType = problemType;
	}

	public String getRetailerName()
	{
		return retailerName;
	}

	public void setRetailerName(String retailerName)
	{
		this.retailerName = retailerName;
	}

	public String getRetailerCode()
	{
		return retailerCode;
	}

	public void setRetailerCode(String retailerCode)
	{
		this.retailerCode = retailerCode;
	}

	public String getRetailerMobileNo()
	{
		return retailerMobileNo;
	}

	public void setRetailerMobileNo(String retailerMobileNo)
	{
		this.retailerMobileNo = retailerMobileNo;
	}
	
	
}
