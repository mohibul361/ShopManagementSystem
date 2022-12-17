/*
 * Item.java
 * 
 * Created on Apr 28, 2008, 12:45:59 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sencillo.utility;

import java.io.Serializable;


public class ItemObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer otherId;
	private String firstSt = "";
	private String middleSt = "";
	private String lastSt = "";
	private String additionalSt = "";
	private Double doubleValue;
	private Double otherDoubleValue;

	public ItemObject(Integer id, String nameSt)
	{
		this.firstSt = nameSt;
		this.id = id;
	}

	public ItemObject(Integer id, Integer numberNr)
	{
		this.firstSt = numberNr.toString();
		this.id = id;
	}

	public ItemObject(Integer id, Double numberNr, Double double1Nr, String additionalSt)
	{
		this.id = id;
		this.otherDoubleValue = numberNr;
		this.doubleValue = double1Nr;
		this.additionalSt = additionalSt;
	}

	public ItemObject(Integer id, String nameSt, Integer otherId)
	{
		this.firstSt = nameSt;
		this.id = id;
		this.otherId = otherId;
	}

	public ItemObject(Integer id, String nameSt, Integer otherId, String otherSt)
	{
		this.firstSt = nameSt;
		this.id = id;
		this.otherId = otherId;
		this.lastSt = otherSt;
	}

	public ItemObject(String a, String b, String c)
	{
		this(null, a, b, c);
	}

	public String getAdditionalSt()
	{
		return additionalSt;
	}

	public void setAdditionalSt(String additionalSt)
	{
		this.additionalSt = additionalSt;
	}

	public String getName()
	{
		String name = "";
		if (firstSt != null)
		{
			name += this.firstSt;
		}
		if (middleSt != null && !this.middleSt.equals(""))
		{
			name += " ";
			name += this.middleSt;
		}
		if (lastSt != null && !this.lastSt.equals(""))
		{
			name += " ";
			name += this.lastSt;
		}
		return name;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.firstSt = name;
		this.middleSt = "";
		this.lastSt = "";
	}

	public Integer getOtherId()
	{
		return otherId;
	}

	public void setOtherId(Integer otherId)
	{
		this.otherId = otherId;
	}

	public String getType()
	{
		return lastSt;
	}

	public void setType(String type)
	{
		this.lastSt = type;
	}

	public String getFirstSt()
	{
		return firstSt;
	}

	public void setFirstSt(String firstSt)
	{
		this.firstSt = firstSt;
	}

	public String getLastSt()
	{
		return lastSt;
	}

	public void setLastSt(String lastSt)
	{
		this.lastSt = lastSt;
	}

	public String getMiddleSt()
	{
		return middleSt;
	}

	public void setMiddleSt(String middleSt)
	{
		this.middleSt = middleSt;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
	}

	public int compareTo(Integer anotherString)
	{
		return id.compareTo(anotherString);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final ItemObject other = (ItemObject) obj;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id)))
		{
			return false;
		}
		if ((this.getName() == null) ? (other.getName() != null) : !this.getName().equals(other.getName()))
		{
			return false;
		}
		return true;
	}

	public ItemObject(Integer id, String a, String b, String c)
	{
		this.firstSt = a;
		this.middleSt = b;
		this.lastSt = c;
		this.id = id;
	}

	public Double getDoubleValue()
	{
		return doubleValue;
	}

	public void setDoubleValue(Double doubleValue)
	{
		this.doubleValue = doubleValue;
	}

	public Double getOtherDoubleValue()
	{
		return otherDoubleValue;
	}

	public void setOtherDoubleValue(Double otherDoubleValue)
	{
		this.otherDoubleValue = otherDoubleValue;
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
		hash = 59 * hash + (this.getName() != null ? this.getName().hashCode() : 0);
		return hash;
	}

	@Override
	public String toString()
	{
		return this.getName();
	}

}
