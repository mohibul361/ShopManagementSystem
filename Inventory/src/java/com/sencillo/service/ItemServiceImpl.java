package com.sencillo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.classes.Attribute;
import com.sencillo.classes.DashboardInfo;
import com.sencillo.classes.DeliveryItem;
import com.sencillo.classes.ItemAvailableStatus;
import com.sencillo.classes.ItemStatusCount;
import com.sencillo.classes.ItemStatusInfo;
import com.sencillo.classes.VendorItemCount;
import com.sencillo.dao.ItemDao;
import com.sencillo.enums.ItemStatus;
import com.sencillo.model.Item;

@Transactional
@Service
public class ItemServiceImpl implements ItemService
{
	@Autowired
	private ItemDao itemDao;

	@Override
	public void save(Item item)
	{
		this.itemDao.save(item);
	}

	@Override
	public void edit(Item item)
	{
		this.itemDao.edit(item);
	}

	@Override
	public List<Item> getItemList()
	{
		return this.itemDao.getItemList();
	}

	@Override
	public Item getItem(Integer id)
	{
		return this.itemDao.getItem(id);
	}

	@Override
	public Item getItemByStatus(String serial, ItemStatus status)
	{
		return this.itemDao.getItemByStatus(serial, status);
	}

	@Override
	public void updateQcSatus(List<Item> itemList, Integer userId)
	{
		this.itemDao.updateQcSatus(itemList, userId);
	}

	@Override
	public List<Object> getItemList(Map<String, String> paramMap, int beginIndex, int pageSize)
	{
		return this.itemDao.getItemList(paramMap, beginIndex, pageSize);
	}
	
	@Override
	public List<Object> getItemDetails(String serial)
	{
		return this.itemDao.getItemDetails(serial);
	}

	@Override
	public Map<String, List<ItemStatusCount>> getDashBoardData()
	{
		return this.itemDao.getDashBoardData();
	}


	@Override
	public List<ItemStatusInfo> getDashBoardDataNew()
	{
		return this.itemDao.getDashBoardDataNew();
	}

	@Override
	public DashboardInfo getDashBoardDataNew1()
	{
		return this.itemDao.getDashBoardDataNew1();
	}
	
	@Override
	public Map<ItemStatus, Integer> getItemStatusCountByAttribute(List<Attribute> attributeList, Integer itemTypeId)
	{
		return this.itemDao.getItemStatusCountByAttribute(attributeList, itemTypeId);
		
	}

	@Override
	public List<DeliveryItem> getDeliveryReport(Integer recipientId, Integer itemTypeId)
	{
		return this.itemDao.getDeliveryReport(recipientId, itemTypeId);
	}

	public boolean updateIMEI(String serial, String imei1, String imei2)
	{
		return this.itemDao.updateIMEI(serial, imei1, imei2);
	}

	@Override
	public List<ItemAvailableStatus> getItemAvailableStatus()
	{
		return this.itemDao.getItemAvailableStatus();
	}

	@Override
	public List<VendorItemCount> getVendorItemCount(Integer itemType)
	{
		return this.itemDao.getVendorItemCount(itemType);
	}
	
	@Override
	public List<Object> getDailyTransactionReport(Date date)
	{
		return this.itemDao.getDailyTransactionReport(date);
	}
}
