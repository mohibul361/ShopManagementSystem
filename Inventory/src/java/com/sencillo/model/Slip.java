package com.sencillo.model;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.sencillo.enums.SlipStatus;
import com.sencillo.enums.SlipType;

@Entity
@Table(name = "slip")
public class Slip implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "slip_number", nullable = false, unique = true)
	private String slipNumber;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "slip_date", nullable = false)
	private Date slipDate;

	@Column(name = "slip_type", nullable = false)
	private SlipType slipType;

	@Column(name = "slip_status", nullable = false)
	private SlipStatus slipStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@NotEmpty
	@Column(name = "remarks")
	private String remarks;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "return_from_id", nullable = true)
	private ReturnFrom returnFrom;

	/*
	 * @Column(name = "slip_item_total") private double slipItemTotal;
	 */

	@Column(name = "total_price")
	private double totalPrice;

	@Column(name = "challan_price")
	private double challanPrice;

	@Column(name = "prev_balance")
	private double prevBalance;

	@Column(name = "labour_cost")
	private double labourCost;

	@Column(name = "total_qty_in_bag")
	private double totalQtyInBag;

	@Column(name = "total_qty_in_kg")
	private double totalQtyInKg;
	
	@Column(name="transport_cost")
	private double transportCost;
	
	@NotNull
	@Column(name="deposit")
	private double deposit;
		
	@ManyToOne(optional = true)
	@JoinColumn(name = "vendor_id", nullable = true)	
	private Vendor vendor;

	@ManyToOne(optional = true)
	@JoinColumn(name = "customer_id", nullable = true)
	private Recipient deliveredTo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "slip", fetch = FetchType.LAZY)
	// @OneToMany(cascade = CascadeType.ALL, mappedBy = "slip", fetch=FetchType.EAGER)
	@NotEmpty(message = "Please add at least one item")
	List<SlipItem> slipItems;

	@ManyToOne
	@JoinColumn(name = "approved_by", nullable = true)
	//@NotNull
	private User approvedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approved_date", nullable = true)
	private Date approvedDate;

	@ManyToOne
	@JoinColumn(name = "parent_slip_id", nullable = true)
	private Slip parentSlip;

	public Slip() {
		slipItems = new ArrayList<SlipItem>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSlipNumber() {
		return slipNumber;
	}

	public void setSlipNumber(String slipNumber) {
		this.slipNumber = slipNumber;
	}

	public Date getSlipDate() {
		return slipDate;
	}

	public void setSlipDate(Date slipDate) {
		this.slipDate = slipDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<SlipItem> getSlipItems() {
		return slipItems;
	}

	public void setSlipItems(List<SlipItem> slipItems) {
		this.slipItems = slipItems;
	}

	public SlipType getSlipType() {
		return slipType;
	}

	public void setSlipType(SlipType slipType) {
		this.slipType = slipType;
	}

	public SlipStatus getSlipStatus() {
		return slipStatus;
	}

	public void setSlipStatus(SlipStatus slipStatus) {
		this.slipStatus = slipStatus;
	}

	public Recipient getDeliveredTo() {
		return deliveredTo;
	}

	public void setDeliveredTo(Recipient deliveredTo) {
		this.deliveredTo = deliveredTo;
	}

	// public User getApprovedBy()
	// {
	// return approvedBy;
	// }
	//
	// public void setApprovedBy(User approvedBy)
	// {
	// this.approvedBy = approvedBy;
	// }

	// public Date getApprovedDate()
	// {
	// return approvedDate;
	// }
	//
	// public void setApprovedDate(Date approvedDate)
	// {
	// this.approvedDate = approvedDate;
	// }

	public double getTransportCost() {
		return transportCost;
	}

	public void setTransportCost(double transportCost) {
		this.transportCost = transportCost;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public Slip getParentSlip() {
		return parentSlip;
	}

	public void setParentSlip(Slip parentSlip) {
		this.parentSlip = parentSlip;
	}

	public ReturnFrom getReturnFrom() {
		return returnFrom;
	}

	public void setReturnFrom(ReturnFrom returnFrom) {
		this.returnFrom = returnFrom;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	/*
	 * public void setSlipItemTotal(double slipItemTotal) { this.slipItemTotal = slipItemTotal; }
	 * 
	 * 
	 * public double getSlipItemTotal() { return slipItemTotal; }
	 */

	

	public double getLabourCost() {
		return labourCost;
	}

	public double getPrevBalance() {
		return prevBalance;
	}

	public void setPrevBalance(double prevBalance) {
		this.prevBalance = prevBalance;
	}

	public void setLabourCost(double labourCost) {
		this.labourCost = labourCost;
	}

	public double getTotalQtyInBag() {
		return totalQtyInBag;
	}

	public void setTotalQtyInBag(double totalQtyInBag) {
		this.totalQtyInBag = totalQtyInBag;
	}

	public double getTotalQtyInKg() {
		return totalQtyInKg;
	}

	public void setTotalQtyInKg(double totalQtyInKg) {
		this.totalQtyInKg = totalQtyInKg;
	}

	public double getChallanPrice() {
		return challanPrice;
	}

	public void setChallanPrice(double challanPrice) {
		this.challanPrice = challanPrice;
	}

	
	public User getApprovedBy()
	{
		return approvedBy;
	}

	public void setApprovedBy(User approvedBy)
	{
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate()
	{
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate)
	{
		this.approvedDate = approvedDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Slip other = (Slip) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
