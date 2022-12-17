package com.sencillo.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sencillo.classes.Attribute;
import com.sencillo.classes.DashboardInfo;
import com.sencillo.classes.DeliveryItem;
import com.sencillo.classes.ItemAvailableStatus;
import com.sencillo.classes.ItemStatusCount;
import com.sencillo.classes.ItemStatusInfo;
import com.sencillo.classes.RecipientCount;
import com.sencillo.classes.VendorItemCount;
import com.sencillo.enums.ItemStatus;
import com.sencillo.enums.SlipType;
import com.sencillo.enums.TransactionType;
import com.sencillo.model.Item;
import com.sencillo.model.ItemAttributeValue;
import com.sencillo.model.ItemType;
import com.sencillo.model.Recipient;
import com.sencillo.model.Slip;
import com.sencillo.model.Transaction;
import com.sencillo.model.User;
import com.sencillo.model.Vendor;


@Repository
public class ItemDaoImpl implements ItemDao
{

	@Autowired
	private ItemTypeDao itemTypeDao;

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(Item item)
	{
		System.err.println("ser1 : " + item.getSerial());
		for (ItemAttributeValue itemAttributeValue : item.getItemAttributeValues())
		{
			System.err.println("se2r : " + itemAttributeValue.getAttributeValue());
		}

		this.getCurrentSession().save(item);

	}

	@Override
	public void edit(Item item)
	{
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				"delete ItemAttributeValue where item.id = " + item.getId());
		q.executeUpdate();

		this.getCurrentSession().update(item);

	}

	@Override
	public List<Item> getItemList()
	{
		@SuppressWarnings("unchecked")
		List<Item> list = sessionFactory.getCurrentSession().createQuery("from Item").list();
		return list;
	}

	@Override
	public Item getItem(Integer id)
	{
		return (Item) sessionFactory.getCurrentSession().get(Item.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Item getItemByStatus(String serialOrImei, ItemStatus status)
	{
		List<Item> list = null;
		if (status != null)
		{

			list = this.sessionFactory
					.getCurrentSession()
					.createQuery(
							"from Item i where i.serial = :serialOrImei or i.imei1=:serialOrImei or i.imei2=:serialOrImei and i.itemStatus = :status")
					.setParameter("serialOrImei", serialOrImei).setParameter("status", status).list();
		}
		return list.size() > 0 ? (Item) list.get(0) : null;
	}

	@Override
	public void updateQcSatus(List<Item> itemList, Integer userId)
	{
		User checkedBy = (User) this.sessionFactory.getCurrentSession().get(User.class, userId);
		for (Item item : itemList)
		{
			System.err.println("itemid.." + item.getId());
			Item dbItem = getItem(item.getId());
			dbItem.setItemStatus(ItemStatus.INSTALLED_AND_CHECKED);
			/*
			 * dbItem.setQcCheckedBy(checkedBy); dbItem.setQcCheckedDate(new Date());
			 */
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getItemList(Map<String, String> paramMap, int beginIndex, int pageSize)
	{
		List<Object> result = new ArrayList<Object>();
		String globalSearch = paramMap.get("globalSearchValue");
		String sql = "from Item i where i.name like '%" + globalSearch + "%' or i.serial like '%" + globalSearch + "%'";
		Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
		query.setFirstResult(beginIndex);
		query.setMaxResults(pageSize);
		List<Item> qcItemList = query.list();

		result.add(qcItemList);
		result.add(getTotalQcItem());
		result.add(getRecordsFiltered(sql));
		return result;
	}

	public int getRecordsFiltered(String sql)
	{
		return this.sessionFactory.getCurrentSession().createQuery(sql).list().size();
	}

	public int getTotalQcItem()
	{
		return this.sessionFactory.getCurrentSession().createQuery("from Item i").list().size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getItemDetails(String serialOrImei)
	{
		List<Object> result = new ArrayList<Object>();
		if (serialOrImei != null && !serialOrImei.isEmpty())
		{
			List<Item> list = this.sessionFactory
					.getCurrentSession()
					.createQuery(
							"from Item i where i.serial = :serialOrImei or i.imei1 = :serialOrImei or i.imei2 = :serialOrImei")
					.setParameter("serialOrImei", serialOrImei).list();
			Item item = list.size() > 0 ? (Item) list.get(0) : null;
			result.add(item);

			if (item != null)
			{
				List<Slip> slipList = (List<Slip>) this.sessionFactory
						.getCurrentSession()
						.createQuery(
								"Select s from SlipItem si join si.slip s where si.item.id = :itemId order by s.id")
						.setParameter("itemId", item.getId()).list();
				System.out.println("slipList.size()>> " + slipList.size());
				result.add(slipList.size() > 0 ? slipList : null);
			} else
			{
				result.add(null);
			}
		}
		return result;
	}

	@Override
	public Map<String, List<ItemStatusCount>> getDashBoardData()
	{
		int order = 0;
		Map<String, List<ItemStatusCount>> map = new LinkedHashMap<String, List<ItemStatusCount>>();
		List<ItemType> itemTypeList = this.itemTypeDao.getItemTypeList();
		for (ItemType itemType : itemTypeList)
		{
			List<ItemStatusCount> itemStatusCounts = new ArrayList<ItemStatusCount>();
			for (ItemStatus itemStatus : ItemStatus.values())
			{

				Long count = (Long) this.sessionFactory
						.getCurrentSession()
						.createQuery(
								"select count(*) FROM Item where itemStatus=:itemStatus and itemType.id=:itemTypeId")
						.setParameter("itemStatus", itemStatus).setParameter("itemTypeId", itemType.getId())
						.uniqueResult();

				List<RecipientCount> recipientCounts = new ArrayList<RecipientCount>();
				if (itemStatus == ItemStatus.DELIVERED)
				{
					@SuppressWarnings("unchecked")
					List<Object[]> recipientCountList = (List<Object[]>) this.sessionFactory
							.getCurrentSession()
							.createQuery(
									"Select r, count(*), i.itemType.id from SlipItem si join si.item i join si.slip s join s.deliveredTo r where i.itemType.id = :itemTypeId and i.itemStatus=:itemStatus and s.slipType=:slipType group by r.name, i.itemType.id")
							.setParameter("itemStatus", itemStatus).setParameter("itemTypeId", itemType.getId())
							.setParameter("slipType", SlipType.DELIVERY).list();

					System.out.println("recipientCountList.size():" + recipientCountList.size());
					for (Object[] temp : recipientCountList)
					{
						Recipient r = (Recipient) temp[0];
						int rc = (int) (long) temp[1];
						recipientCounts.add(new RecipientCount(r, "", rc));
					}
				}

				switch (itemStatus)
				{
				case DELIVERED:
					order = 1;
					break;
				case QC_DEFECTIVE:
					order = 2;
					break;
				case INSTALLED_AND_CHECKED:
					order = 3;
					break;
				case ON_HAND:
					order = 4;
					break;
				default:
					break;
				}

				itemStatusCounts.add(new ItemStatusCount(itemStatus, order, count.intValue(), recipientCounts));
			}

			Collections.sort(itemStatusCounts);

			Long count = (Long) this.sessionFactory.getCurrentSession()
					.createQuery("select count(*) FROM Item where itemType.id=:itemTypeId")
					.setParameter("itemTypeId", itemType.getId()).uniqueResult();

			int avaliableTotal = 0;
			for (ItemStatusCount itemStatusCount : itemStatusCounts)
			{
				if (itemStatusCount.getItemStatus() == ItemStatus.INSTALLED_AND_CHECKED
						|| itemStatusCount.getItemStatus() == ItemStatus.ON_HAND)
				{
					avaliableTotal += itemStatusCount.getCount();
				}

			}
			map.put(itemType.getName() + "#" + count + "#" + avaliableTotal, itemStatusCounts);
		}
		return map;
	}

	@Override
	public List<ItemStatusInfo> getDashBoardDataNew()
	{
		int order = 0;
		Map<String, List<ItemStatusInfo>> map = new LinkedHashMap<String, List<ItemStatusInfo>>();
		List<ItemType> itemTypeList = this.itemTypeDao.getItemTypeList();
		List<ItemStatusInfo> itemStatusInfoList = new ArrayList<ItemStatusInfo>();
		for (ItemType itemType : itemTypeList)
		{
			Long receivedTotal = (Long) this.sessionFactory.getCurrentSession()
					.createQuery("select count(*) FROM Item where itemType.id=:itemTypeId")
					.setParameter("itemTypeId", itemType.getId()).uniqueResult();

			// Long qcPassed = (Long) this.sessionFactory.getCurrentSession()
			// .createQuery("select count(*) FROM SlipItem si where si.item.itemStatus=:itemStatus and si.slip.slipType=:slipType and si.itemType.id=:itemTypeId")
			// .setParameter("itemStatus", ItemStatus.INSTALLED_AND_CHECKED).setParameter("slipType",
			// SlipType.QC_INSPECTION).setParameter("itemTypeId", itemType.getId()).uniqueResult();
			//
			// Long qcFailed = (Long) this.sessionFactory.getCurrentSession()
			// .createQuery("select count(*) FROM SlipItem si where si.item.itemStatus=:itemStatus and si.slip.slipType=:slipType and si.itemType.id=:itemTypeId")
			// .setParameter("itemStatus", ItemStatus.QC_DEFECTIVE).setParameter("slipType",
			// SlipType.QC_INSPECTION).setParameter("itemTypeId", itemType.getId()).uniqueResult();

			Long delivered = (Long) this.sessionFactory
					.getCurrentSession()
					.createQuery(
							"select count(*) FROM SlipItem si where si.slip.slipType=:slipType and si.itemType.id=:itemTypeId")
					.setParameter("slipType", SlipType.DELIVERY).setParameter("itemTypeId", itemType.getId())
					.uniqueResult();

			// Long retrunNormal = (Long) this.sessionFactory.getCurrentSession()
			// .createQuery("select count(*) FROM SlipItem si where si.item.itemStatus=:itemStatus and si.slip.slipType=:slipType and si.itemType.id=:itemTypeId")
			// .setParameter("itemStatus", ItemStatus.ON_HAND).setParameter("slipType",
			// SlipType.RETURN).setParameter("itemTypeId", itemType.getId()).uniqueResult();
			//
			// Long returnFaulty = (Long) this.sessionFactory.getCurrentSession()
			// .createQuery("select count(*) FROM SlipItem si where si.item.itemStatus=:itemStatus and si.slip.slipType=:slipType and si.itemType.id=:itemTypeId")
			// .setParameter("itemStatus", ItemStatus.RETURN_DEFECTIVE).setParameter("slipType",
			// SlipType.RETURN).setParameter("itemTypeId", itemType.getId()).uniqueResult();
			//
			// Long replacement = (Long) this.sessionFactory.getCurrentSession()
			// .createQuery("select count(*) FROM SlipItem si where si.slip.slipType=:slipType and si.itemType.id=:itemTypeId")
			// .setParameter("slipType", SlipType.REPLACEMENT).setParameter("itemTypeId",
			// itemType.getId()).uniqueResult();
			//
			// Long notQc = (Long)
			// this.sessionFactory.getCurrentSession().createQuery("select count(*) FROM Item where itemStatus=:itemStatus and itemType.id=:itemTypeId")
			// .setParameter("itemStatus", ItemStatus.ON_HAND).setParameter("itemTypeId",
			// itemType.getId()).uniqueResult();
			//
			// Long available = notQc+qcPassed;
			Long available = receivedTotal - delivered;
			itemStatusInfoList.add(new ItemStatusInfo(itemType, receivedTotal.intValue(),
					0, 0, delivered.intValue(), 0, 0, 0, available.intValue()));

		}
		return itemStatusInfoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DashboardInfo getDashBoardDataNew1()
	{
		Map<String, List<RecipientCount>> recipientCountMap = new LinkedHashMap<String, List<RecipientCount>>();
		List<ItemType> itemTypeList = this.itemTypeDao.getItemTypeList();
		List<ItemStatusInfo> itemStatusInfoList = new ArrayList<ItemStatusInfo>();
		for (ItemType itemType : itemTypeList)
		{

			List<Object[]> received = this.sessionFactory
					.getCurrentSession()
					.createQuery(
							"select sum(si.quantityInBag), sum(si.quantityInKg) FROM SlipItem si where si.slip.slipType=:slipType and si.itemType.id=:itemTypeId")
					.setParameter("slipType", SlipType.INCOMING).setParameter("itemTypeId", itemType.getId()).list();
			Double receivedTotalInBag = (Double) received.get(0)[0];
			Double receivedTotalInKg = (Double) received.get(0)[1];

			receivedTotalInBag = receivedTotalInBag != null ? receivedTotalInBag : 0.0;
			receivedTotalInKg = receivedTotalInKg != null ? receivedTotalInKg : 0.0;

			List<Object[]> delivered = (List<Object[]>) this.sessionFactory
					.getCurrentSession()
					.createQuery(
							"select sum(si.quantityInBag), sum(si.quantityInKg) FROM SlipItem si where si.slip.slipType=:slipType and si.itemType.id=:itemTypeId")
					.setParameter("slipType", SlipType.DELIVERY).setParameter("itemTypeId", itemType.getId()).list();
			System.out.print("delivered: " + delivered);
			Double deliveredTotalInBag = (Double) delivered.get(0)[0];
			Double deliveredTotalInKg = (Double) delivered.get(0)[1];

			deliveredTotalInBag = deliveredTotalInBag != null ? deliveredTotalInBag : 0.0;
			deliveredTotalInKg = deliveredTotalInKg != null ? deliveredTotalInKg : 0.0;

			Double availableInBag = receivedTotalInBag - deliveredTotalInBag;
			Double availableInKg = receivedTotalInKg - deliveredTotalInKg;
			itemStatusInfoList.add(new ItemStatusInfo(itemType, receivedTotalInBag
					.intValue(), receivedTotalInKg.intValue(), deliveredTotalInBag.intValue(), deliveredTotalInKg
					.intValue(), 0, 0, availableInBag.intValue(), availableInKg.intValue()));
		
		}

		DashboardInfo dashboardInfo = new DashboardInfo(itemStatusInfoList, recipientCountMap);
		return dashboardInfo;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<Object> getDailyTransactionReport(Date date)
	{
		List<Object> objects = new ArrayList<Object>();
		String query = "select s from Slip s where s.slipType=:slipType and s.slipDate=:date";
		List<Slip> incomingSlipList = this.sessionFactory.getCurrentSession().createQuery(query).setParameter("slipType", SlipType.INCOMING).setParameter("date", date).list();
		objects.add(incomingSlipList);
		
		query = "select s from Slip s where s.slipType=:slipType and s.slipDate=:date";
		List<Slip> deliverySlipList = this.sessionFactory.getCurrentSession().createQuery(query).setParameter("slipType", SlipType.DELIVERY).setParameter("date", date).list();
		objects.add(deliverySlipList);
		
		query = "select s from Transaction s where s.transactionType=:transactionType and s.date=:date";
		List<Transaction> paymentList = this.sessionFactory.getCurrentSession().createQuery(query).setParameter("transactionType", TransactionType.PAYMENT).setParameter("date", date).list();
		System.out.print("paymentList:size="+paymentList.size());
		objects.add(paymentList);
		
		query = "select s from Transaction s where s.transactionType=:transactionType and s.date=:date";
		List<Transaction> receiveList = this.sessionFactory.getCurrentSession().createQuery(query).setParameter("transactionType", TransactionType.RECEIVE).setParameter("date", date).list();
		System.out.print("receiveList:size="+receiveList.size());
		objects.add(receiveList);
		
		return objects;
	}
		
	public Map<ItemStatus, Integer> getItemStatusCountByAttribute(List<Attribute> attributeList, Integer itemTypeId)
	{
		Map<ItemStatus, Integer> itemStatusCount = new HashMap<ItemStatus, Integer>();

		String query = "select count(*) FROM Item i where i.itemStatus=:itemStatus and i.itemType.id=:itemTypeId and i.id in ";

		String subQuery = "";
		int i = 0;
		for (Attribute itemAttribute : attributeList)
		{
			if (!itemAttribute.getAttributeValue().equals("0"))
			{
				subQuery += "(select v.item.id from ItemAttributeValue v where ";
				subQuery += "(v.itemTypeAttribute.id =" + itemAttribute.getAttributeId() + " and v.attributeValue='"
						+ itemAttribute.getAttributeValue() + "')";
				if (i != 0)
				{
					subQuery += " ) ";
				}
				subQuery += " AND v.item.id in ";
				i++;
			}
		}
		subQuery = subQuery.substring(0, subQuery.length() - 17);
		query += subQuery;
		query += " )";
		System.out.println(query);
		for (ItemStatus itemStatus : ItemStatus.values())
		{
			Long count = (Long) this.sessionFactory.getCurrentSession().createQuery(query)
					.setParameter("itemStatus", itemStatus).setParameter("itemTypeId", itemTypeId).uniqueResult();

			System.out.println("itemStatus" + itemStatus.name() + " count>>>" + count);

			itemStatusCount.put(itemStatus, (int) (long) count);
		}
		return itemStatusCount;
	}

	@SuppressWarnings("unchecked")
	public List<DeliveryItem> getDeliveryReport(Integer recipientId, Integer itemTypeId)
	{
		String query = "select new com.dm.classes.DeliveryItem(si.item.itemType.name, si.item.serial, s.slipDate, s.deliveredTo.name) from SlipItem si join si.slip s where si.item.itemType.id = :itemTypeId and si.item.itemStatus=:itemStatus and s.slipType=:slipType and s.deliveredTo.id=:recipientId";

		List<DeliveryItem> deliveryItemList = this.sessionFactory.getCurrentSession().createQuery(query)
				.setParameter("itemTypeId", itemTypeId).setParameter("itemStatus", ItemStatus.DELIVERED)
				.setParameter("slipType", SlipType.DELIVERY).setParameter("recipientId", recipientId).list();
		return deliveryItemList;
	}

	/**
	 * This method is used for import imei from excel
	 * 
	 * @param serial
	 * @param imei1
	 * @param imei2
	 */
	public boolean updateIMEI(String serial, String imei1, String imei2)
	{
		boolean success = true;
		String query = null;
		try
		{
			if (imei1 != null && imei2 != null)
			{
				query = "update Item set imei1=:imei1, imei2=:imei2 where serial = :serial";
				this.sessionFactory.getCurrentSession().createQuery(query).setParameter("serial", serial)
						.setParameter("imei1", imei1).setParameter("imei2", imei2).executeUpdate();
			}
			if (imei1 != null && imei2 == null)
			{
				query = "update Item set imei1=:imei1 where serial = :serial";
				this.sessionFactory.getCurrentSession().createQuery(query).setParameter("serial", serial)
						.setParameter("imei1", imei1).executeUpdate();
			}

		} catch (Exception e)
		{
			success = false;
		}
		return success;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemAvailableStatus> getItemAvailableStatus()
	{
		List<ItemAvailableStatus> itemAvailableStatusList = null;
		String query = "select new com.dm.classes.ItemAvailableStatus(it.id, it.name, sum(vtc.balanceQuantityInBag), sum(vtc.balanceQuantityInKg))  from VendorItemCount vtc join vtc.itemType it group by vtc.itemType.id";
		itemAvailableStatusList = this.sessionFactory.getCurrentSession().createQuery(query).list();
		return itemAvailableStatusList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendorItemCount> getVendorItemCount(Integer itemTypeId)
	{
		/*
		 * String query = "select vtc from VendorItemCount vtc join vtc.itemType it where it.id="+itemTypeId;
		 * List<VendorItemCount> vendorItemCounts = this.sessionFactory.getCurrentSession().createQuery(query).list();
		 * return vendorItemCounts;
		 */

		List<VendorItemCount> vtcList = new ArrayList<>();
		List<Object[]> received = this.sessionFactory
				.getCurrentSession()
				.createQuery(
						"select si.slip.vendor, sum(si.quantityInBag), sum(si.quantityInKg) FROM SlipItem si where si.slip.slipType=:slipType and si.itemType.id=:itemTypeId group by si.slip.vendor order by si.slip.vendor.name")
				.setParameter("slipType", SlipType.INCOMING).setParameter("itemTypeId", itemTypeId).list();

		for (Object[] temp : received)
		{
			Vendor vendor = (Vendor) temp[0];
			Double receivedTotalInBag = (Double) temp[1];
			Double receivedTotalInKg = (Double) temp[2];

			receivedTotalInBag = receivedTotalInBag != null ? receivedTotalInBag : 0.0;
			receivedTotalInKg = receivedTotalInKg != null ? receivedTotalInKg : 0.0;

			List<Object[]> delivered = this.sessionFactory
					.getCurrentSession()
					.createQuery(
							"select sum(si.quantityInBag), sum(si.quantityInKg) FROM SlipItem si where si.slip.slipType=:slipType and si.itemType.id=:itemTypeId and si.vendor.id=:vendorId")
					.setParameter("slipType", SlipType.DELIVERY).setParameter("itemTypeId", itemTypeId)
					.setParameter("vendorId", vendor.getId()).list();

			Double deliveredTotalInBag = (Double) delivered.get(0)[0];
			Double deliveredTotalInKg = (Double) delivered.get(0)[1];

			deliveredTotalInBag = deliveredTotalInBag != null ? deliveredTotalInBag : 0.0;
			deliveredTotalInKg = deliveredTotalInKg != null ? deliveredTotalInKg : 0.0;

			double balanceQuantityInBag = receivedTotalInBag - deliveredTotalInBag;
			double balanceQuantityInKg = receivedTotalInKg - deliveredTotalInKg;
			if((balanceQuantityInBag > 0 && balanceQuantityInKg == 0) || (balanceQuantityInBag == 0 && balanceQuantityInKg > 0) || (balanceQuantityInBag !=0 && balanceQuantityInKg != 0))
			{
				VendorItemCount vtc = new VendorItemCount();
				vtc.setId(null);
				vtc.setItemType(null);
				vtc.setVendor(vendor);
				vtc.setBalanceQuantityInBag(balanceQuantityInBag);
				vtc.setBalanceQuantityInKg(balanceQuantityInKg);
				vtcList.add(vtc);
			}			
		}
		return vtcList;

	}
}
