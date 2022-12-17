package com.sencillo.dao;

import com.sencillo.model.Company;

public interface CompanyDao
{
	public Company getCompany(Integer id);
	
	public void save(Company company);
}
