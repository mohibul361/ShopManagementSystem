package com.sencillo.forms;

import javax.persistence.Transient;

public class Employee {

    private Integer id;

    @Transient // means "not a DB field"
    private Integer remove; // boolean flag

    private String firstname;
    private String lastname;

    private Employer employer; // many-to-one

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getRemove()
	{
		return remove;
	}

	public void setRemove(Integer remove)
	{
		this.remove = remove;
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

	public Employer getEmployer()
	{
		return employer;
	}

	public void setEmployer(Employer employer)
	{
		this.employer = employer;
	}

    /* getters & setters */

}
