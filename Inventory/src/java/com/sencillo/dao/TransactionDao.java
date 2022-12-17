package com.sencillo.dao;

import java.util.List;
import java.util.Map;

import com.sencillo.enums.TransactionType;
import com.sencillo.model.Transaction;

public interface TransactionDao {
	public void save(Transaction transaction);

	public void edit(Transaction transaction);

	public void delete(Integer transactionId) throws Exception;
	
	public Transaction getTransaction(Integer id);

	public List<Object> getTransactionList(TransactionType transactionType, Integer recipientId, int beginIndex, int pageSize);
	
	public List<Transaction> getTransactionList(TransactionType transactionType, Integer customerOrVendorId);
	
	public Object[] getCustomerInfo(Integer customerId);
	
	public double getCustomerBalance(Integer customerId);

	public Object[] getVendorInfo(Integer vendorId);
	
	public List<Object> getTransactions(Map parameter, int offset, int numofRecords);
}
