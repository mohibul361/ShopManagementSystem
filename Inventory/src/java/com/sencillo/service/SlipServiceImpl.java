package com.sencillo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.classes.CustomerBalance;
import com.sencillo.classes.DeliverySlipInfo;
import com.sencillo.dao.ItemDao;
import com.sencillo.dao.SlipDao;
import com.sencillo.dao.UserDao;
import com.sencillo.enums.SlipStatus;
import com.sencillo.enums.SlipType;
import com.sencillo.forms.SlipForm;
import com.sencillo.model.Item;
import com.sencillo.model.ItemAttributeValue;
import com.sencillo.model.Slip;
import com.sencillo.model.SlipItem;
import com.sencillo.model.User;

@Transactional
@Service
public class SlipServiceImpl implements SlipService
{
	@Autowired
	private SlipDao slipDao;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private ItemDao itemDao;
	
	@Override
	public Slip save(Slip slip)
	{
		return this.slipDao.save(slip);
	}

	@Override
	public void edit(Slip slip, List<SlipItem> slipItems2remove)
	{
		this.slipDao.edit(slip, slipItems2remove);
		
	}

	@Override
	public List<Object> getSlipList(Map paramMap, int beginIndex, int pageSize)
	{ 
		return this.slipDao.getSlipList(paramMap, beginIndex, pageSize);
	}

	@Override
	public Slip getSlip(Integer id)
	{
		return this.slipDao.getSlip(id);
	}

//	@Override
//	public Slip saveSlip(SlipForm slipForm, String userName)
//	{
//		Slip slip = slipForm.getSlip();
//		Date d = slip.getSlipDate();
//		slip.setCreationDate(d);
//		User user = userDao.getUser(userName);
//		slip.setCreatedBy(user);
//		slip.setSlipType(SlipType.INCOMING);
//		slip.setSlipStatus(SlipStatus.SAVED);
//		slip.setSlipItemTotal(slipForm.getItems().size());
//		
//		for (Item item : slipForm.getItems())
//		{
//			item.setItemType(item.getItemType());
//			item.setName(item.getName());
//			item.setItemStatus(ItemStatus.ON_HAND);
//			
//			item.setPurchaseDate(item.getPurchaseDate());
//			
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(item.getPurchaseDate());
//			cal.add(Calendar.YEAR, item.getWarranty());
//			item.setWarrantyDate(cal.getTime());
//			
//			System.out.println("item >>> " + item.getSerial());
//			List<ItemAttributeValue> itemAttributeValues = new ArrayList<>();
//			for (ItemAttributeValue itemAttributeValue : item.getItemAttributeValues())
//			{
//				System.out.println("item Attribute value---" + itemAttributeValue);
//				itemAttributeValue.setItem(item);
//				itemAttributeValue.setId(null);
//				itemAttributeValues.add(itemAttributeValue);
//			}
//			item.setItemAttributeValues(itemAttributeValues);
//
//			this.itemDao.save(item);
//
//			SlipItem slipItem = new SlipItem();
//			slipItem.setId(null);
//			slipItem.setItem(item);
//			slipItem.setItemType(item.getItemType());
//			slipItem.setQuantity(item.getQuantity());
//			slipItem.setSlip(slip);
//			slip.getSlipItems().add(slipItem);
//		}
//
//		slip = this.slipDao.save(slip);
//
//		return slip;
//	}

	@Override
	public Slip saveSlip(SlipForm slipForm, String userName)
	{
		Slip slip = slipForm.getSlip();
		Date d = slip.getSlipDate();
		slip.setCreationDate(d);
		User user = userDao.getUser(userName);
		slip.setCreatedBy(user);
		slip.setSlipType(SlipType.INCOMING);
		slip.setSlipStatus(SlipStatus.SAVED);
//		slip.setSlipItemTotal(slipForm.getItems().size());
		
		for (Item item : slipForm.getItems())
		{
			System.out.println("item >>> " + item.getSerial());
			List<ItemAttributeValue> itemAttributeValues = new ArrayList<ItemAttributeValue>();
			for (ItemAttributeValue itemAttributeValue : slipForm.getItemAttributeValues())
			{
				ItemAttributeValue itemAttributeValue2 = new ItemAttributeValue();
				itemAttributeValue2.setItem(item);
				itemAttributeValue2.setId(null);
				itemAttributeValue2.setItemTypeAttribute(itemAttributeValue.getItemTypeAttribute());
				itemAttributeValue2.setAttributeValue(itemAttributeValue.getAttributeValue());
				itemAttributeValues.add(itemAttributeValue2);
			}
			item.setItemAttributeValues(itemAttributeValues);

			this.itemDao.save(item);

			SlipItem slipItem = new SlipItem();
			slipItem.setId(null);
			slipItem.setItem(item);
			slipItem.setItemType(item.getItemType());
//			slipItem.setQuantity(item.getQuantity());
			slipItem.setSlip(slip);
			slip.getSlipItems().add(slipItem);
		}

		slip = this.slipDao.save(slip);

		return slip;
	}
	public List<DeliverySlipInfo> getDeliverySlipInfo(Integer itemTypeId, Integer customerId)
	{
		return this.slipDao.getDeliverySlipInfo(itemTypeId, customerId);
	}
	
	public List<Object> getUnReplacedReturnSlip(int beginIndex, int pageSize)
	{
		return this.slipDao.getUnReplacedReturnSlip(beginIndex, pageSize);
	}
	
	public List<Slip> getReplacementSlips()
	{
		return this.slipDao.getReplacementSlips();
	}
	
	@Override
	public List<Object> getSlipListNew(Map<String, String> paramMap, int beginIndex, int pageSize)
	{ 
		return this.slipDao.getSlipListNew(paramMap, beginIndex, pageSize);
	}
	
	@Override
	public List<Slip> getPendingSlips(Map paramMap)
	{
		return this.slipDao.getPendingSlips(paramMap);
	}

	public boolean approveSlips(List<Integer> slipIds, User approvedBy)
	{
		return this.slipDao.approveSlips(slipIds, approvedBy);
	}
	
	@Override
	public boolean getItemAvailableStatus(Integer vendorId, Integer itemTypeId, double qty, boolean isBagAvailabilityCheck)
	{
		return this.slipDao.getItemAvailableStatus(vendorId, itemTypeId, qty, isBagAvailabilityCheck);
	}
	
	public List<CustomerBalance> getCustomerBalanceReportInfo(Date toDate)
	{
		return this.slipDao.getCustomerBalanceReportInfo(toDate);
	}
}
