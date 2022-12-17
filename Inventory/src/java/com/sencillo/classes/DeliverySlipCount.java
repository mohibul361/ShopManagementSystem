package com.sencillo.classes;

public class DeliverySlipCount
{
	private String slipNumber;
	private long slipCount;
	private Integer slipId;

	public DeliverySlipCount(String slipNumber, long slipCount, Integer slipId)
	{
		super();
		this.slipNumber = slipNumber;
		this.slipCount = slipCount;
		this.slipId = slipId;
	}

	public String getSlipNumber()
	{
		return slipNumber;
	}

	public void setSlipNumber(String slipNumber)
	{
		this.slipNumber = slipNumber;
	}

	public long getSlipCount()
	{
		return slipCount;
	}

	public void setSlipCount(long slipCount)
	{
		this.slipCount = slipCount;
	}

	public Integer getSlipId()
	{
		return slipId;
	}

	public void setSlipId(Integer slipId)
	{
		this.slipId = slipId;
	}
	

}
