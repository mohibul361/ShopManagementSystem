package com.sencillo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sencillo.classes.CustomerBalance;
import com.sencillo.classes.DeliverySlipCount;
import com.sencillo.classes.DeliverySlipInfo;
import com.sencillo.enums.ItemStatus;
import com.sencillo.enums.SlipStatus;
import com.sencillo.enums.SlipType;
import com.sencillo.enums.TransactionType;
import com.sencillo.model.Item;
import com.sencillo.model.Recipient;
import com.sencillo.model.Slip;
import com.sencillo.model.SlipItem;
import com.sencillo.model.User;

@Repository
public class SlipDaoImpl implements SlipDao
{
	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Slip save(Slip slip)
	{
		this.getCurrentSession().save(slip);
		this.getCurrentSession().refresh(slip);
		/*if (slip.getSlipType() == SlipType.INCOMING)
		{
			updateItemInfoForIS(slip);
		}
		if (slip.getSlipType() == SlipType.DELIVERY)
		{
			updateItemInfoForDS(slip);
		}*/

		return getSlip(slip.getId());
	}

	private void updateItemStatus(Slip slip, ItemStatus itemStatus)
	{
		boolean checkSlipItem = false;
		if (itemStatus == null)
		{
			checkSlipItem = true;
		}
		for (SlipItem slipItem : slip.getSlipItems())
		{
			if (checkSlipItem)
			{
				System.out.println("slipitem.isdeffective>> " + slipItem.getIsDefective());
//				if (slip.getSlipType() == SlipType.QC_INSPECTION)
//				{
//					itemStatus = slipItem.getIsDefective() ? ItemStatus.QC_DEFECTIVE : ItemStatus.ON_HAND;
//				}
//				if (slip.getSlipType() == SlipType.RETURN)
//				{
//					itemStatus = slipItem.getIsDefective() ? ItemStatus.RETURN_DEFECTIVE : ItemStatus.ON_HAND;
//				}

			}
			Item item = (Item) this.getCurrentSession().get(Item.class, slipItem.getItem().getId());
			item.setItemStatus(itemStatus);
		}
	}

	private void updateItemInfoForIS(Slip slip)
	{
		/*for (SlipItem slipItem : slip.getSlipItems())
		{
			ItemType itemType = (ItemType) this.getCurrentSession().get(ItemType.class, slipItem.getItemType().getId());
			Vendor vendor = slip.getVendor();
			String sql = "Select vtc from VendorItemCount vtc where vtc.itemType=:itemType and vtc.vendor=:vendor";
			VendorItemCount vtc = (VendorItemCount) this.sessionFactory.getCurrentSession().createQuery(sql).setParameter("itemType", itemType).setParameter("vendor", vendor).uniqueResult();
			if(vtc==null)
			{
				VendorItemCount vendorItemCount = new VendorItemCount();
				vendorItemCount.setId(null);
				vendorItemCount.setItemType(itemType);
				vendorItemCount.setVendor(vendor);
				vendorItemCount.setBalanceQuantityInBag(slipItem.getQuantityInBag());
				vendorItemCount.setBalanceQuantityInKg(slipItem.getQuantityInKg());
				this.getCurrentSession().save(vendorItemCount);
			}
			else
			{
				vtc.setBalanceQuantityInBag(vtc.getBalanceQuantityInBag()+slipItem.getQuantityInBag());
				vtc.setBalanceQuantityInKg(vtc.getBalanceQuantityInKg()+slipItem.getQuantityInKg());
				this.getCurrentSession().update(vtc);
			}
		}*/
	}

	private void updateItemInfoForDS(Slip slip)
	{
		/*for (SlipItem slipItem : slip.getSlipItems())
		{
			ItemType itemType = (ItemType) this.getCurrentSession().get(ItemType.class, slipItem.getItemType().getId());
			Vendor vendor = slipItem.getVendor();
			String sql = "Select vtc from VendorItemCount vtc where vtc.itemType=:itemType and vtc.vendor=:vendor";
			VendorItemCount vtc = (VendorItemCount) this.sessionFactory.getCurrentSession().createQuery(sql).setParameter("itemType", itemType).setParameter("vendor", vendor).uniqueResult();
			if(vtc==null)
			{
				
			}
			else
			{
				vtc.setBalanceQuantityInBag(vtc.getBalanceQuantityInBag()-slipItem.getQuantityInBag());
				vtc.setBalanceQuantityInKg(vtc.getBalanceQuantityInKg()-slipItem.getQuantityInKg());
				this.getCurrentSession().update(vtc);
			}
		}*/
	}
	@Override
	public void edit(Slip slip, List<SlipItem> slipItems2remove)
	{
		this.getCurrentSession().update(slip);
		for (SlipItem slipItem : slipItems2remove)
		{
			if (slipItem.getId() != null)
			{
				deleteSlipItem(slipItem.getId());
			}
		}
		
	}

	private void deleteSlipItem(Integer slipItemId)
	{
		SlipItem slipItem = (SlipItem) sessionFactory.getCurrentSession().load(SlipItem.class, slipItemId);
		if (slipItem != null)
		{
			System.out.println("slipItem delete >>" + slipItem.getId());
			this.getCurrentSession().delete(slipItem);
		}
	}

	/*
	 * @Override public List<Slip> getSlipList(Map<String, String> paramMap, int beginIndex, int pageSize) {
	 * 
	 * @SuppressWarnings("unchecked") List<Slip> list = sessionFactory.getCurrentSession().createQuery("from Slip").list(); return list; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getSlipList(Map paramMap, int beginIndex, int pageSize)
	{
		List<Object> result = new ArrayList<Object>();
		String slipNumber = (String)paramMap.get("slipNumber");
		
		String sql = "Select s from Slip s where (s.slipNumber like '%" + slipNumber + "%')";
		SlipType slipType = null;
		List<Slip> slipList;
		if (paramMap.containsKey("slipType"))
		{
			if (!paramMap.get("slipType").toString().equals("ALL"))
			{
				slipType = SlipType.valueOf(paramMap.get("slipType").toString());
				sql += " and s.slipType=:slipType";
				
			}
		}
		sql += " order by s.slipDate desc";
	/*	System.out.print("sdfsdf");
		if(paramMap.containsKey("fromDate") && paramMap.containsKey("toDate"))
		{ 
			System.out.print("ccccccc");
			sql += " and s.slipDate between :fromDate and :toDate";
		}*/
		Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
		query.setFirstResult(beginIndex);
		query.setMaxResults(pageSize);
		
		/*if(paramMap.containsKey("fromDate") && paramMap.containsKey("toDate"))
		{
			Date fromDate = (Date) paramMap.get("fromDate");
			Date toDate = (Date) paramMap.get("toDate");
			
			System.out.print("fdate:"+fromDate+" tdate:"+toDate);
			query = query.setParameter("fromDate", fromDate).setParameter("toDate", toDate);
		}*/
		if (slipType == null)
		{
			slipList = query.list();
		}
		else
		{
			slipList = query.setParameter("slipType", slipType).list();
		}
		

		result.add(slipList);
		result.add(getTotalSlip(sql, slipType));
		result.add(getRecordsFiltered(sql, slipType));
		return result;
	}

	/**
	 * This is used when global search is enable
	 * @param sql
	 * @param slipType
	 * @return
	 */
	public int getRecordsFiltered(String sql, SlipType slipType)
	{
		if (slipType != null)
		{
			return this.sessionFactory.getCurrentSession().createQuery(sql).setParameter("slipType", slipType).list().size();
		}
		else
		{
			return this.sessionFactory.getCurrentSession().createQuery(sql).list().size();
		}
	}

	public int getTotalSlip(String sql, SlipType slipType)
	{
		if (slipType != null)
		{
			return this.sessionFactory.getCurrentSession().createQuery(sql).setParameter("slipType", slipType).list().size();
		}
		else
		{
			return this.sessionFactory.getCurrentSession().createQuery(sql).list().size();
		}

	}

	@Override
	public Slip getSlip(Integer id)
	{
		Slip slip = (Slip) this.getCurrentSession().load(Slip.class, id);
		System.out.println("sliplist size : " + slip.getSlipItems());
		return slip;
	}

	public List<DeliverySlipInfo> getDeliverySlipInfo(Integer itemTypeId, Integer customerId)
	{
		@SuppressWarnings("unchecked")
		List<Object[]> deliveryDateWiseCount = (List<Object[]>) this.sessionFactory
				.getCurrentSession()
				.createQuery(
						"Select s.slipDate, count(si.id) from SlipItem si join si.slip s join s.deliveredTo r where si.itemType.id = :itemTypeId "
								+ "and s.slipType=:slipType and r.id=:customerId " + "group by s.slipDate").setParameter("itemTypeId", itemTypeId)
				.setParameter("slipType", SlipType.DELIVERY).setParameter("customerId", customerId).list();

		List<DeliverySlipInfo> deliverySlipInfos = new ArrayList<DeliverySlipInfo>();
		for (Object[] temp : deliveryDateWiseCount)
		{
			Date slipDate = (Date) temp[0];
			int dateCountTotal = (int) (long) temp[1];
			@SuppressWarnings("unchecked")
			List<DeliverySlipCount> deliverySlipCounts = (List<DeliverySlipCount>) this.sessionFactory
					.getCurrentSession()
					.createQuery(
							"Select new com.dm.classes.DeliverySlipCount(s.slipNumber, count(si.id), s.id) from SlipItem si join si.slip s join s.deliveredTo r where si.itemType.id = :itemTypeId "
									+ "and s.slipType=:slipType and s.slipDate=:slipDate and r.id=:customerId " + "group by s.id").setParameter("itemTypeId", itemTypeId)
					.setParameter("slipDate", slipDate).setParameter("slipType", SlipType.DELIVERY).setParameter("customerId", customerId).list();

			deliverySlipInfos.add(new DeliverySlipInfo(slipDate, deliverySlipCounts, dateCountTotal));
		}

		return deliverySlipInfos;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getUnReplacedReturnSlip(int beginIndex, int pageSize)
	{
//		String sql = "Select s from Slip s where s.slipType=:slipType and s.id not in (select parentSlip.id from Slip where parentSlip is not null)";
//		Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
//		query.setFirstResult(beginIndex);
//		query.setParameter("slipType", SlipType.RETURN);
//		query.setMaxResults(pageSize);
//		List<Slip> unReplacedReturnSlips = query.list();
//
//		int totalRecords = this.sessionFactory.getCurrentSession().createQuery(sql).setParameter("slipType", SlipType.RETURN).list().size();
		List<Object> returnList = new ArrayList<Object>();
//		returnList.add(unReplacedReturnSlips);
//		returnList.add(totalRecords);
		return returnList;
	}

	public List<Slip> getReplacementSlips()
	{
		@SuppressWarnings("unchecked")
		List<Slip> replacementSlips = null ;//= (List<Slip>) this.sessionFactory.getCurrentSession().createQuery("Select s from Slip s where s.slipType=:slipType ")
				//.setParameter("slipType", SlipType.REPLACEMENT).list();
		return replacementSlips;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getSlipListNew(Map<String, String> paramMap, int beginIndex, int pageSize)
	{
		String sql = "Select s from Slip s where s.slipType=:slipType";
		Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
		query.setFirstResult(beginIndex);
		query.setMaxResults(pageSize);

		SlipType slipType = SlipType.valueOf(paramMap.get("slipType"));
		List<Slip> slipList = query.setParameter("slipType", slipType).list();

		int totalRecords = this.sessionFactory.getCurrentSession().createQuery(sql).setParameter("slipType", slipType).list().size();
		List<Object> returnList = new ArrayList<Object>();
		returnList.add(slipList);
		returnList.add(totalRecords);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Slip> getPendingSlips(Map paramMap)
	{
		Date fromDate = (Date)paramMap.get("fromDate");
		Date toDate = (Date)paramMap.get("toDate");
		
		String sql = "Select s from Slip s where s.slipStatus=0 and s.slipDate between :fromDate and :toDate";
		Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
		List<Slip> slipList = query.setParameter("fromDate", fromDate).setParameter("toDate", toDate).list();
		return slipList;
		
	}
	
	public boolean approveSlips(List<Integer> slipIds, User approvedBy)
	{
		try
		{
			for(Integer id: slipIds)
			{
				Slip slip = (Slip) this.getCurrentSession().load(Slip.class, id);
				slip.setSlipStatus(SlipStatus.APPROVED);
				slip.setApprovedBy(approvedBy);
				slip.setApprovedDate(new Date());
				this.getCurrentSession().update(slip);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean getItemAvailableStatus(Integer vendorId, Integer itemTypeId, double qty, boolean isBagAvailabilityCheck)
	{
		System.out.print("vendorId:"+vendorId+" itemTypeId:"+itemTypeId+"qty:"+qty+"isBag:"+isBagAvailabilityCheck);
		Object[] received = (Object[]) this.sessionFactory
				.getCurrentSession()
				.createQuery(
						"select sum(si.quantityInBag), sum(si.quantityInKg) FROM SlipItem si where si.slip.slipType=:slipType and si.itemType.id=:itemTypeId and si.slip.vendor.id=:vendorId")
				.setParameter("slipType", SlipType.INCOMING).setParameter("itemTypeId", itemTypeId).setParameter("vendorId", vendorId).uniqueResult();

		Double receivedTotalInBag = (Double) received[0];
		Double receivedTotalInKg = (Double) received[1];

		receivedTotalInBag = receivedTotalInBag != null ? receivedTotalInBag : 0.0;
		receivedTotalInKg = receivedTotalInKg != null ? receivedTotalInKg : 0.0;
		
		List<Object[]> delivered = this.sessionFactory
				.getCurrentSession()
				.createQuery(
						"select sum(si.quantityInBag), sum(si.quantityInKg) FROM SlipItem si where si.slip.slipType=:slipType and si.itemType.id=:itemTypeId and si.vendor.id=:vendorId")
				.setParameter("slipType", SlipType.DELIVERY).setParameter("itemTypeId", itemTypeId)
				.setParameter("vendorId", vendorId).list();

		
		Double deliveredTotalInBag = (Double) delivered.get(0)[0];
		Double deliveredTotalInKg = (Double) delivered.get(0)[1];

		deliveredTotalInBag = deliveredTotalInBag != null ? deliveredTotalInBag : 0.0;
		deliveredTotalInKg = deliveredTotalInKg != null ? deliveredTotalInKg : 0.0;
		
		if(isBagAvailabilityCheck)
		{
			double availableQtyInBag = receivedTotalInBag-deliveredTotalInBag;
			if(qty<=availableQtyInBag)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			double availableQtyInKg = receivedTotalInKg-deliveredTotalInKg;
			if(qty<=availableQtyInKg)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CustomerBalance> getCustomerBalanceReportInfo(Date toDate)
	{
		
		List<Recipient> list = this.sessionFactory.getCurrentSession().createQuery("from Recipient order by name ").list();
		List<CustomerBalance> customerBalances = new ArrayList<CustomerBalance>();
		for(Recipient customer: list)
		{
			Double temp1 = (Double) this.getCurrentSession().createQuery("select sum(c.amount) from Transaction c where c.recipient.id="+customer.getId()+" and c.transactionType=:transactionType").setParameter("transactionType", TransactionType.OPENINGBALNCE).uniqueResult();
			Double temp2 = (Double) this.getCurrentSession().createQuery("select sum(s.totalPrice) from Slip s where s.deliveredTo.id="+customer.getId()).uniqueResult();
			Double temp3 = (Double) this.getCurrentSession().createQuery("select sum(s.deposit) from Slip s where s.deliveredTo.id="+customer.getId()).uniqueResult();
			Double temp4 = (Double) this.getCurrentSession().createQuery("select sum(c.amount) from Transaction c where c.recipient.id="+customer.getId()+ "and c.transactionType=:transactionType").setParameter("transactionType", TransactionType.RECEIVE).uniqueResult();
			if( temp1 == null)
			{
				temp1 = 0D;
			}
			if( temp2 == null)
			{
				temp2 = 0D;
			}
			if( temp3 == null)
			{
				temp3 = 0D;
			}
			if( temp4 == null)
			{
				temp4 = 0D;
			}
			double temp = temp1+temp2-temp3-temp4;
			if(temp != 0)
			{
				customerBalances.add(new CustomerBalance(customer, temp));
			}			
		}
		return customerBalances;
		
	}
}
