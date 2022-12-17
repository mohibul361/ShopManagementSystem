package com.sencillo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sencillo.enums.RecipientType;
import com.sencillo.model.Recipient;
import com.sencillo.model.Vendor;

@Repository
public class RecipientDaoImpl implements RecipientDao
{
	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(Recipient customer)
	{
		this.getCurrentSession().save(customer);
	}

	@Override
	public void edit(Recipient customer)
	{
		this.getCurrentSession().update(customer);
	}

	@Override
	public Recipient getRecipient(Integer id)
	{
		return (Recipient) this.getCurrentSession().get(Recipient.class, id);
	}

	@Override
	public List<Recipient> getRecipientList()
	{
		@SuppressWarnings("unchecked")
		List<Recipient> list = sessionFactory.getCurrentSession().createQuery("from Recipient order by name ").list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getRecipientList(Map<String, Object> paramMap, int beginIndex, int pageSize)
	{
		List<Object> result = new ArrayList<Object>();
		String globalSearch = (String)paramMap.get("globalSearchValue");
				
		String sql = "Select s from Recipient s where (s.name like '%" + globalSearch + "%' or s.address like '%" + globalSearch + "%')";
		
		List<Vendor> vendorList;
		
		sql += " order by s.name asc";
		Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
		query.setFirstResult(beginIndex);
		query.setMaxResults(pageSize);
		vendorList = query.list();

		result.add(vendorList);
		result.add(getTotalSlip(sql));
		result.add(getRecordsFiltered(sql));
		return result;
	}

	
	public int getRecordsFiltered(String sql)
	{
		return this.sessionFactory.getCurrentSession().createQuery(sql).list().size();	
	}

	public int getTotalSlip(String sql)
	{
		return this.sessionFactory.getCurrentSession().createQuery(sql).list().size();
	}
	
	@Override
	public List<Recipient> getRecipientListByType(RecipientType recipientType)
	{
		@SuppressWarnings("unchecked")
		List<Recipient> list = sessionFactory.getCurrentSession().createQuery("from Recipient where recipientType=:recipientType").setParameter("recipientType", recipientType).list();
		return list;
	}

	@Override
	public void delete(Integer recipientId) throws Exception {

		try {
			Recipient recipient = (Recipient) sessionFactory.getCurrentSession().load(Recipient.class, recipientId);
			if (recipient != null) {
				this.getCurrentSession().delete(recipient);
			}
		} catch (ConstraintViolationException c) {
			throw new Exception("You can not delete this recipient, It is used in challans!!!");
		}
		
	}

}
