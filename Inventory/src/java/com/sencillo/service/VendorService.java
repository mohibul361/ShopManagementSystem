package com.sencillo.service;

import java.util.List;
import java.util.Map;

import com.sencillo.model.Vendor;

public interface VendorService {
	
	public void save(Vendor vendor);

	public void edit(Vendor vendor);

	public Vendor getVendor(Integer id);

	public List<Vendor> getVendorList();
	
	public List<Object> getVendorList(Map<String, Object> paramMap, int beginIndex, int pageSize);
	
	public void delete(Integer vendorId) throws Exception;
}
