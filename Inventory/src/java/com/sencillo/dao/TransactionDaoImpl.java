package com.sencillo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.validation.ConstraintViolationException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sencillo.enums.TransactionType;
import com.sencillo.model.Recipient;
import com.sencillo.model.Transaction;
import com.sencillo.model.Vendor;


@Repository
public class TransactionDaoImpl implements TransactionDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(Transaction transaction) {
		
		this.getCurrentSession().save(transaction);
		
		/*Slip slip = (Slip) this.getCurrentSession().get(Slip.class, collection.getSlip().getId());
		slip.setBalancePrice(slip.getBalancePrice()-collection.getAmount());	*/	
		
	}

	@Override
	public void edit(Transaction transaction) {
		this.getCurrentSession().update(transaction);
		
	}

	@Override
	public Transaction getTransaction(Integer id) {
		return (Transaction) this.getCurrentSession().get(Transaction.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getTransactionList(TransactionType transactionType, Integer recipientId, int beginIndex, int pageSize) 
	{
		List<Transaction> list = null;
		String sql;
		if(recipientId == null)
		{
			sql = "from Transaction t where t.transactionType=:transactionType order by t.date desc, t.id desc";
		}
		else
		{
			sql = "from Transaction t where t.transactionType=:transactionType and t.recipient.id=:recipientId"
					+ " order by t.date desc, t.id desc";
		}
		
		Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
		query.setFirstResult(beginIndex);
		query.setParameter("transactionType", transactionType);
		query.setMaxResults(pageSize);	
		if(recipientId != null)
		{
			query.setParameter("recipientId", recipientId);
		}
		list = query.list();
		int totalRecords;
		if(recipientId != null)
		{
			totalRecords = this.sessionFactory.getCurrentSession().createQuery(sql).setParameter("transactionType", transactionType).setParameter("recipientId", recipientId).list().size();
		}
		else
		{
			totalRecords = this.sessionFactory.getCurrentSession().createQuery(sql).setParameter("transactionType", transactionType).list().size();
		}
		
		List<Object> returnList = new ArrayList<Object>();
		returnList.add(list);
		returnList.add(totalRecords);
		return returnList;
	}

	public List<Transaction> getTransactionList(TransactionType transactionType, Integer customerOrVendorId)
	{
		String sql;
		if(TransactionType.RECEIVE == transactionType)
		{
			if(customerOrVendorId != null)
			{
				sql = "from Transaction t where t.recipient.id=:customerOrVendorId order by t.date desc, t.id desc";
			}
			else
			{
				sql = "from Transaction t order by t.date desc, t.id desc";				
			}
			
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			if(customerOrVendorId != null)
			{
				query.setParameter("customerOrVendorId", customerOrVendorId);
			}
			
			List<Transaction> list = query.list();
			return list;
		}
		return null;
		
	}
	
	@Override
	public Object[] getCustomerInfo(Integer customerId) {
		Recipient recipient = (Recipient) this.getCurrentSession().createQuery("select r from Recipient r where r.id="+customerId).uniqueResult();
		Object[] data = new Object[4];
		data[0] = recipient.getMobileNo1();
		data[1] = recipient.getAddress();
		Transaction collection = (Transaction) this.getCurrentSession().createQuery("select c from Transaction c where c.recipient.id=:customerId and c.id=(select max(t.id) from Transaction t where c.recipient.id=:customerId)").setParameter("customerId", customerId).uniqueResult();
		if(collection != null)
		{
			data[2] = collection.getAmount();
			data[3] = collection.getDate();
		}
		return data;
	}

	@Override
	public double getCustomerBalance(Integer customerId) {
		
		Double temp1 = (Double) this.getCurrentSession().createQuery("select sum(c.amount) from Transaction c where c.recipient.id="+customerId+" and c.transactionType=:transactionType").setParameter("transactionType", TransactionType.OPENINGBALNCE).uniqueResult();
		Double temp2 = (Double) this.getCurrentSession().createQuery("select sum(s.totalPrice) from Slip s where s.deliveredTo.id="+customerId).uniqueResult();
		Double temp3 = (Double) this.getCurrentSession().createQuery("select sum(s.deposit) from Slip s where s.deliveredTo.id="+customerId).uniqueResult();
		Double temp4 = (Double) this.getCurrentSession().createQuery("select sum(c.amount) from Transaction c where c.recipient.id="+customerId+ "and c.transactionType=:transactionType").setParameter("transactionType", TransactionType.RECEIVE).uniqueResult();
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
		return temp;
	}
	
	@Override
	public Object[] getVendorInfo(Integer vendorId) {
		Vendor vendor = (Vendor) this.getCurrentSession().createQuery("select v from Vendor v where v.id="+vendorId).uniqueResult();
		Object[] data = new Object[4];
		data[0] = vendor.getMobileNo1();
		data[1] = vendor.getAddress();
		Transaction collection = (Transaction) this.getCurrentSession().createQuery("select c from Transaction c where c.vendor.id=:vendorId and c.id=(select max(t.id) from Transaction t where c.vendor.id=:vendorId)").setParameter("vendorId", vendorId).uniqueResult();
		if(collection != null)
		{
			data[2] = collection.getAmount();
			data[3] = collection.getDate();
		}
		return data;
	}

	@Override
	public void delete(Integer transactionId) throws Exception
	{
		try {
			Transaction transaction = (Transaction) sessionFactory.getCurrentSession().load(Transaction.class, transactionId);
			if (transaction != null) {
				this.getCurrentSession().delete(transaction);
			}
		} catch (Exception c) {
			throw new Exception("Exception in delete");
		}
		
		
	}
	
	public List<Object> getTransactions(Map parameter, int offset, int numofRecords)
	{
		  try
	        {
	           
	            Integer recipientId = (Integer) parameter.get("recipientId");
	            Integer vendorId = (Integer) parameter.get("vendorId");
	            TransactionType transactionType = (TransactionType) parameter.get("transactionType");
	            
	            String mainQuerySt = "from Transaction o where 0=0 ";
	            String countQuerySt = "select count(o.id) from Transaction o where 0=0 ";

	            String querySt = "";

	            if (recipientId != null)
	            {
	                querySt += " AND o.recipient.id = "+recipientId;
	            }
	            if (vendorId != null)
	            {
	                querySt += " AND o.vendor.id = "+vendorId;
	            }
	            querySt += " AND o.transactionType="+transactionType.ordinal()
	            		+ "  order by o.date desc";
	            List<Transaction> list = this.sessionFactory.getCurrentSession().createQuery(mainQuerySt + querySt).setFirstResult(offset).setMaxResults(numofRecords).list();
	            long count = (Long) this.sessionFactory.getCurrentSession().createQuery(countQuerySt + querySt).list().get(0);

	            List<Object> result = new ArrayList<Object>();
	            result.add(list);
	            result.add(count);
	            result.add(count); // ????

	            return result;
	        }
	        catch (Exception e)
	        {
	            System.out.println("exc=" + e.getMessage());
	            e.printStackTrace();
	            return null;
	        }
	}
	
}
