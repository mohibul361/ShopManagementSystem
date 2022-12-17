package com.sencillo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.sencillo.enums.ItemStatus;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "item")
public class Item
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Column(name = "quantity", nullable = false)
	private double quantity;

	@NotEmpty
	@Column(name="serial", nullable = false)
	private String serial;
	
	@Column(name="imei1", nullable = true)
	private String imei1;
	
	@Column(name="imei2", nullable = true)
	private String imei2;
			
	@NotNull
	@DateTimeFormat(pattern = "dd/mm/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "purchase_date", nullable = false)
	private Date purchaseDate;
	
	@NotNull
	@DateTimeFormat(pattern = "dd/mm/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "warranty_date", nullable = false)
	private Date warrantyDate;
	
	@NotNull
	@Column(name="item_status", nullable=false)
	private ItemStatus itemStatus;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "customer_warranty_date", nullable = true)
	private Date customerwarrantyDate;
	
	@ManyToOne
	@JoinColumn(name = "item_type_id", nullable = false)
	private ItemType itemType;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "item", fetch=FetchType.EAGER)
	private List<ItemAttributeValue> itemAttributeValues;
	
	@Transient
	private int warranty;
	
	public Item()
	{
		itemAttributeValues = new ArrayList<ItemAttributeValue>();
	}
	
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

	public double getQuantity()
	{
		return quantity;
	}

	public void setQuantity(double quantity)
	{
		this.quantity = quantity;
	}

	public String getSerial()
	{
		return serial;
	}

	public void setSerial(String serial)
	{
		this.serial = serial;
	}

	public Date getPurchaseDate()
	{
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate)
	{
		this.purchaseDate = purchaseDate;
	}

	public Date getWarrantyDate()
	{
		return warrantyDate;
	}

	public void setWarrantyDate(Date warrantyDate)
	{
		this.warrantyDate = warrantyDate;
	}

	public Date getCustomerwarrantyDate()
	{
		return customerwarrantyDate;
	}

	public void setCustomerwarrantyDate(Date customerwarrantyDate)
	{
		this.customerwarrantyDate = customerwarrantyDate;
	}

	public ItemStatus getItemStatus()
	{
		return itemStatus;
	}

	public void setItemStatus(ItemStatus itemStatus)
	{
		this.itemStatus = itemStatus;
	}

	public ItemType getItemType()
	{
		return itemType;
	}

	public void setItemType(ItemType itemType)
	{
		this.itemType = itemType;
	}

	public List<ItemAttributeValue> getItemAttributeValues()
	{
		return itemAttributeValues;
	}

	public void setItemAttributeValues(List<ItemAttributeValue> itemAttributeValues)
	{
		this.itemAttributeValues = itemAttributeValues;
	}

	public int getWarranty()
	{
		return warranty;
	}

	public void setWarranty(int warranty)
	{
		this.warranty = warranty;
	}
	
	public String getImei1()
	{
		return imei1;
	}

	public void setImei1(String imei1)
	{
		this.imei1 = imei1;
	}

	public String getImei2()
	{
		return imei2;
	}

	public void setImei2(String imei2)
	{
		this.imei2 = imei2;
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
		Item other = (Item) obj;
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
