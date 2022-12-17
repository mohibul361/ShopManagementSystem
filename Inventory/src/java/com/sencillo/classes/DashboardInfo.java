package com.sencillo.classes;

import java.util.List;
import java.util.Map;

public class DashboardInfo
{
	private List<ItemStatusInfo> itemStatusInfoList;
	private Map<String, List<RecipientCount>> recipientCountMap;
		
	public DashboardInfo(List<ItemStatusInfo> itemStatusInfo, Map<String, List<RecipientCount>> recipientCountMap)
	{
		super();
		this.itemStatusInfoList = itemStatusInfo;
		this.recipientCountMap = recipientCountMap;
	}
	public List<ItemStatusInfo> getItemStatusInfoList()
	{
		return itemStatusInfoList;
	}
	public void setItemStatusInfoList(List<ItemStatusInfo> itemStatusInfoList)
	{
		this.itemStatusInfoList = itemStatusInfoList;
	}
	public Map<String, List<RecipientCount>> getRecipientCountMap()
	{
		return recipientCountMap;
	}
	public void setRecipientCountMap(Map<String, List<RecipientCount>> recipientCountMap)
	{
		this.recipientCountMap = recipientCountMap;
	}
	
	
}
