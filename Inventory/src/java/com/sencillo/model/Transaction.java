package com.sencillo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.sencillo.enums.TransactionType;

@Table(name="transaction")
@Entity
public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="recipient_id", nullable=true)
	private Recipient recipient;
	
	@ManyToOne
	@JoinColumn(name="vendor_id", nullable=true)
	private Vendor vendor;
	
	@Column(name="transaction_no", nullable=true)
	private String transactionNo;
	
	@NotNull
	@Column(name="amount", nullable=false)
	private double amount;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="date", nullable=false)
	private Date date;
	
	@NotEmpty
	@Column(name="remarks")
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)	
	private User createdBy;
	
	@Column(name="transaction_type")
	private TransactionType transactionType;
	
	/*@ManyToOne
	@JoinColumn(name="slip_id", nullable=false)
	private Slip slip;*/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	
	/*public Slip getSlip() {
		return slip;
	}

	public void setSlip(Slip slip) {
		this.slip = slip;
	}*/
	
	
	public String getRemarks() {
		return remarks;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
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
		Transaction other = (Transaction) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
		
	
	
}

