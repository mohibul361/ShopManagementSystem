package com.sencillo.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sencillo.model.Company;

@Repository
public class CompanyDaoImpl implements CompanyDao
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Company getCompany(Integer id)
	{
		return (Company) sessionFactory.getCurrentSession().get(Company.class, id);
	}

	@Override
	public void save(Company company)
	{
		this.sessionFactory.getCurrentSession().update(company);
	}

}
