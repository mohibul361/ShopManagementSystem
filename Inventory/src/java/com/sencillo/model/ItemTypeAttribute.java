package com.sencillo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

import com.sencillo.enums.AttributeType;

@Entity
@Table(name = "item_type_attribute")
public class ItemTypeAttribute implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@Length(min = 2, max = 25)
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "attribute_type", nullable = false)
	private AttributeType attributeType;

	@Min(1)
	private int serial;

	@ManyToOne
	@JoinColumn(name = "item_type_id", nullable = false)
	private ItemType itemType;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="itemTypeAttribute")
	private List<ItemTypeAttributeComboData> itemTypeAttributeComboDatas;

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

	public int getSerial()
	{
		return serial;
	}

	public void setSerial(int serial)
	{
		this.serial = serial;
	}

	public AttributeType getAttributeType()
	{
		return attributeType;
	}

	public void setAttributeType(AttributeType attributeType)
	{
		this.attributeType = attributeType;
	}

	public ItemType getItemType()
	{
		return itemType;
	}

	public void setItemType(ItemType itemType)
	{
		this.itemType = itemType;
	}

	public List<ItemTypeAttributeComboData> getItemTypeAttributeComboDatas()
	{
		return itemTypeAttributeComboDatas;
	}

	public void setItemTypeAttributeComboDatas(List<ItemTypeAttributeComboData> itemTypeAttributeComboDatas)
	{
		this.itemTypeAttributeComboDatas = itemTypeAttributeComboDatas;
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
		ItemTypeAttribute other = (ItemTypeAttribute) obj;
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
		return "ItemTypeAttribute [id=" + id + ", name=" + name + ", attributeType=" + attributeType + ", serial=" + serial + ", itemType=" + itemType + "]";
	}
        
}
