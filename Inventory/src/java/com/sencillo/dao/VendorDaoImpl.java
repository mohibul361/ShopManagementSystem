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




import com.sencillo.enums.SlipType;
import com.sencillo.model.Slip;
import com.sencillo.model.Vendor;

@Repository
public class VendorDaoImpl implements VendorDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(Vendor vendor) {
		this.getCurrentSession().save(vendor);
	}

	@Override
	public void edit(Vendor vendor) {
		this.getCurrentSession().update(vendor);
	}

	@Override
	public Vendor getVendor(Integer id) {
		return (Vendor) this.getCurrentSession().get(Vendor.class, id);
	}

	@Override
	public List<Vendor> getVendorList() {
		@SuppressWarnings("unchecked")
		List<Vendor> list = sessionFactory.getCurrentSession().createQuery("from Vendor order by name").list();
		return list;
	}

	@Override
	public void delete(Integer vendorId) throws Exception {

		try {
			Vendor vendor = (Vendor) sessionFactory.getCurrentSession().load(Vendor.class, vendorId);
			if (vendor != null) {
				this.getCurrentSession().delete(vendor);
			}
		} catch (ConstraintViolationException c) {
			throw new Exception("You can not delete this vendor, It is used in challans!!!");
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getVendorList(Map<String, Object> paramMap, int beginIndex, int pageSize)
	{
		List<Object> result = new ArrayList<Object>();
		String globalSearch = (String)paramMap.get("globalSearchValue");
				
		String sql = "Select s from Vendor s where (s.name like '%" + globalSearch + "%' or s.address like '%" + globalSearch + "%')";
		
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

}
