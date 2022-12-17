package com.sencillo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "item_type")
public class ItemType
{
	@Id
	@GeneratedValue
	private Integer id;

	@Length(min = 2, max = 255)
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Length(min = 2, max = 6)
	@Column(name = "code", nullable = false, unique = true)
	private String code;

	@Column(name = "description", nullable = true)
	private String description;
	
	@Column(name = "low_mark", nullable = false, columnDefinition = "double default 0")
	private Double lowMark;
	
	@Column(name = "high_mark", nullable = false, columnDefinition = "double default 0")
	private Double highMark;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "itemType", fetch=FetchType.LAZY)
	@OrderBy("serial ASC")
	private List<ItemTypeAttribute> itemTypeAttributes;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Double getLowMark()
	{
		return lowMark;
	}

	public void setLowMark(Double lowMark)
	{
		this.lowMark = lowMark;
	}

	public Double getHighMark()
	{
		return highMark;
	}

	public void setHighMark(Double highMark)
	{
		this.highMark = highMark;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public List<ItemTypeAttribute> getItemTypeAttributes()
	{
		return itemTypeAttributes;
	}

	public void setItemTypeAttributes(List<ItemTypeAttribute> itemTypeAttributes)
	{
		this.itemTypeAttributes = itemTypeAttributes;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemType other = (ItemType) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "ItemType [id=" + id + ", name=" + name + ", code=" + code + ", description=" + description + "]";
	}

}
