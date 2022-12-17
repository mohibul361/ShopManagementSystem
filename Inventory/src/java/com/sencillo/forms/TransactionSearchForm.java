package com.sencillo.forms;

import java.util.Date;

public class TransactionSearchForm
{
	private Integer customerId;
	private Integer vendorId;
	private Date fromDate;
	private Date toDate;
	public Integer getCustomerId()
	{
		return customerId;
	}
	public void setCustomerId(Integer customerId)
	{
		this.customerId = customerId;
	}
	public Integer getVendorId()
	{
		return vendorId;
	}
	public void setVendorId(Integer vendorId)
	{
		this.vendorId = vendorId;
	}
	public Date getFromDate()
	{
		return fromDate;
	}
	public void setFromDate(Date fromDate)
	{
		this.fromDate = fromDate;
	}
	public Date getToDate()
	{
		return toDate;
	}
	public void setToDate(Date toDate)
	{
		this.toDate = toDate;
	}
	
	
}
