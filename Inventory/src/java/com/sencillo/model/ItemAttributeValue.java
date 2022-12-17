package com.sencillo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "item_attribute_value")
public class ItemAttributeValue
{
	@Id
	@GeneratedValue
	private Integer id;

	@NotEmpty
	@Column(name = "attribute_value", nullable = false)
	private String attributeValue;

	@ManyToOne
	@JoinColumn(name = "item_id", nullable = false)
	private Item item;

	@ManyToOne
	@JoinColumn(name = "item_type_attribute_id", nullable = false)
	private ItemTypeAttribute itemTypeAttribute;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getAttributeValue()
	{
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue)
	{
		this.attributeValue = attributeValue;
	}

	public Item getItem()
	{
		return item;
	}

	public void setItem(Item item)
	{
		this.item = item;
	}

	public ItemTypeAttribute getItemTypeAttribute()
	{
		return itemTypeAttribute;
	}

	public void setItemTypeAttribute(ItemTypeAttribute itemTypeAttribute)
	{
		this.itemTypeAttribute = itemTypeAttribute;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
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
		ItemAttributeValue other = (ItemAttributeValue) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}

}
