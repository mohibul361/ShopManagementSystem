package com.sencillo.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sencillo.classes.Attribute;
import com.sencillo.classes.DashboardInfo;
import com.sencillo.classes.DeliveryItem;
import com.sencillo.classes.ItemAvailableStatus;
import com.sencillo.classes.ItemStatusCount;
import com.sencillo.classes.ItemStatusInfo;
import com.sencillo.classes.VendorItemCount;
import com.sencillo.enums.ItemStatus;
import com.sencillo.model.Item;

public interface ItemDao
{
	public void save(Item item);
	
	public void edit(Item item);

	public List<Item> getItemList();

	public Item getItem(Integer id);
	
	public Item getItemByStatus(String serial, ItemStatus status);
	
	public void updateQcSatus(List<Item> itemList, Integer userId);
	
	public List<Object> getItemList(Map<String, String> paramMap, int beginIndex, int pageSize);
	
	public List<Object> getItemDetails(String serial);
	
	public Map<String, List<ItemStatusCount>> getDashBoardData();
	
	public List<ItemStatusInfo> getDashBoardDataNew();
	
	public DashboardInfo getDashBoardDataNew1();
	
	public Map<ItemStatus, Integer> getItemStatusCountByAttribute(List<Attribute> attributeList, Integer itemTypeId);
	
	public List<DeliveryItem> getDeliveryReport(Integer recipientId, Integer itemTypeId);
	
	public boolean updateIMEI(String serial, String imei1, String imei2);
	
	public List<ItemAvailableStatus> getItemAvailableStatus();
	
	public List<VendorItemCount> getVendorItemCount(Integer itemType);
	
	public List<Object> getDailyTransactionReport(Date date);
}
