package com.sencillo.classes;

import java.util.Date;
import java.util.List;

public class DeliverySlipInfo
{
	private Date deliveryDate;
	private List<DeliverySlipCount> deliverySlipCount;
	private int dateWiseCount;
		
	public DeliverySlipInfo(Date deliveryDate, List<DeliverySlipCount> deliverySlipCount, int dateWiseCount)
	{
		super();
		this.deliveryDate = deliveryDate;
		this.deliverySlipCount = deliverySlipCount;
		this.dateWiseCount = dateWiseCount;
	}

	public Date getDeliveryDate()
	{
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}

	public List<DeliverySlipCount> getDeliverySlipCount()
	{
		return deliverySlipCount;
	}

	public void setDeliverySlipCount(List<DeliverySlipCount> deliverySlipCount)
	{
		this.deliverySlipCount = deliverySlipCount;
	}

	public int getDateWiseCount()
	{
		return dateWiseCount;
	}

	public void setDateWiseCount(int dateWiseCount)
	{
		this.dateWiseCount = dateWiseCount;
	}
	
}
