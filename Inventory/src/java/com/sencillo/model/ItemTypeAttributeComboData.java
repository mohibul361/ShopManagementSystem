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
@Table(name = "item_type_attribute_combo_data")
public class ItemTypeAttributeComboData
{
	@Id
	@GeneratedValue
	private Integer id;

	@NotEmpty
	@Column(name = "value", nullable = false)
	private String value;

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

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
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
		ItemTypeAttributeComboData other = (ItemTypeAttributeComboData) obj;
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
		return "ItemTypeAttributeComboData [id=" + id + ", value=" + value + ", itemTypeAttribute=" + itemTypeAttribute + "]";
	}

}
