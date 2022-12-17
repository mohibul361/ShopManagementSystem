package com.sencillo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.sencillo.enums.Unit;

@Entity
@Table(name = "slip_item")
public class SlipItem
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "quantity_in_bag", nullable = false, columnDefinition = "int default 0")
	private double quantityInBag;
	
	@Column(name = "quantity_in_kg", nullable = false, columnDefinition = "int default 0")
	private double quantityInKg;
	
	@Column(name="unit_price", columnDefinition = "int default 0")
	private double unitPrice;
	
	@Column(name="sub_total_price", columnDefinition = "int default 0")
	private double subTotalPrice;
	
	@ManyToOne()
	@JoinColumn(name = "slip_id", nullable = false)
	private Slip slip;

	@ManyToOne(optional = true)
	@JoinColumn(name = "item_id", nullable = true)
	private Item item;

	@ManyToOne
	@JoinColumn(name = "item_type_id", nullable = false)
	private ItemType itemType;

	@ManyToOne
	@JoinColumn(name = "vendor_id", nullable = true)
	private Vendor vendor;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "return_item_info_id", nullable = true)
	private ReturnItemInfo returnItemInfo;
	
	@Transient
	private boolean isDefective;

	@Transient
	private Integer remove; // boolean flag

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	
	public Slip getSlip()
	{
		return slip;
	}

	public void setSlip(Slip slip)
	{
		this.slip = slip;
	}

	public Item getItem()
	{
		return item;
	}

	public void setItem(Item item)
	{
		this.item = item;
	}
	
	public boolean getIsDefective()
	{
		return isDefective;
	}

	public void setIsDefective(boolean isDefective)
	{
		this.isDefective = isDefective;
	}

	public ItemType getItemType()
	{
		return itemType;
	}

	public void setItemType(ItemType itemType)
	{
		this.itemType = itemType;
	}

	public Integer getRemove()
	{
		return remove;
	}

	public void setRemove(Integer remove)
	{
		this.remove = remove;
	}

	public ReturnItemInfo getReturnItemInfo()
	{
		return returnItemInfo;
	}

	public void setReturnItemInfo(ReturnItemInfo returnItemInfo)
	{
		this.returnItemInfo = returnItemInfo;
	}

	
	public double getQuantityInBag() {
		return quantityInBag;
	}

	public void setQuantityInBag(double quantityInBag) {
		this.quantityInBag = quantityInBag;
	}

	public double getQuantityInKg() {
		return quantityInKg;
	}

	public void setQuantityInKg(double quantityInKg) {
		this.quantityInKg = quantityInKg;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getSubTotalPrice() {
		return subTotalPrice;
	}

	public void setSubTotalPrice(double subTotalPrice) {
		this.subTotalPrice = subTotalPrice;
	}

	
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
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
		SlipItem other = (SlipItem) obj;
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
