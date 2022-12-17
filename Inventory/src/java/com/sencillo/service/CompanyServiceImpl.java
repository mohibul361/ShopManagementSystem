package com.sencillo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.dao.CompanyDao;
import com.sencillo.model.Company;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService
{

	@Autowired
	private CompanyDao companyDao;
	
	@Override
	public Company getCompany(Integer id)
	{
		// TODO Auto-generated method stub
		return this.companyDao.getCompany(id);
	}

	@Override
	public void save(Company company)
	{
		// TODO Auto-generated method stub
		this.companyDao.save(company);
	}
	
}
