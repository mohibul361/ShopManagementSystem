package com.sencillo.forms;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class Employer
{

	private Integer id;

	@NotEmpty
	private String firstname;
	private String lastname;
	@NotEmpty
	private String company;

	private List<Employee> employees; // one-to-many

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public String getCompany()
	{
		return company;
	}

	public void setCompany(String company)
	{
		this.company = company;
	}

	public List<Employee> getEmployees()
	{
		return employees;
	}

	public void setEmployees(List<Employee> employees)
	{
		this.employees = employees;
	}

	/* getters & setters */

}
