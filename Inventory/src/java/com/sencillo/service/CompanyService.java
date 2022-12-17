package com.sencillo.service;

import com.sencillo.model.Company;

public interface CompanyService
{
	public Company getCompany(Integer id);

	public void save(Company company);
}
