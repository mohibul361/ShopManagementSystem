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
@Table(name="return_from")
public class ReturnFrom implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "distributor_name", nullable = false)
	private String distributorName;
		
	@Column(name = "distributor_code", nullable = false)
	private String distributorCode;
	
	@ManyToOne
	@JoinColumn(name="region_id", nullable = false)
	private Region region;
	
	@Column(name = "area", nullable = false)
	private String area;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getDistributorName()
	{
		return distributorName;
	}

	public void setDistributorName(String distributorName)
	{
		this.distributorName = distributorName;
	}

	public String getDistributorCode()
	{
		return distributorCode;
	}

	public void setDistributorCode(String distributorCode)
	{
		this.distributorCode = distributorCode;
	}

	public Region getRegion()
	{
		return region;
	}

	public void setRegion(Region region)
	{
		this.region = region;
	}

	public String getArea()
	{
		return area;
	}

	public void setArea(String area)
	{
		this.area = area;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReturnFrom other = (ReturnFrom) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
