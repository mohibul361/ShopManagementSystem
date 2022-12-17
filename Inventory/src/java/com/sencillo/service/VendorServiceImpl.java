package com.sencillo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.dao.VendorDao;
import com.sencillo.model.Vendor;

@Service
@Transactional
public class VendorServiceImpl implements VendorService{

	@Autowired
	private VendorDao vendorDao;
	
	@Override
	public void save(Vendor vendor) {
		this.vendorDao.save(vendor);		
	}

	@Override
	public void edit(Vendor vendor) {
		this.vendorDao.edit(vendor);		
	}

	@Override
	public Vendor getVendor(Integer id) {
		return this.vendorDao.getVendor(id);
	}

	@Override
	public List<Vendor> getVendorList() {
		return this.vendorDao.getVendorList();
	}

	@Override
	public List<Object> getVendorList(Map<String, Object> paramMap, int beginIndex, int pageSize) {
		return this.vendorDao.getVendorList(paramMap, beginIndex, pageSize);
	}
	
	@Override
	public void delete(Integer vendorId) throws Exception {
		this.vendorDao.delete(vendorId);
		
	}
	
	

}
