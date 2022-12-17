package com.sencillo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role
{
	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "role_name", nullable = false)
	private String roleName;

	@ManyToMany(cascade=CascadeType.ALL)	
	private List<Page> pages;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	public List<Page> getPages()
	{
		return pages;
	}

	public void setPages(List<Page> pages)
	{
		this.pages = pages;
	}
        
        @Override
	public String toString()
	{
		return "Role [id=" + id + ", roleName=" + roleName + ", pages=" + pages + "]";
	}

}