package com.sencillo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.dao.TransactionDao;
import com.sencillo.enums.TransactionType;
import com.sencillo.model.Transaction;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionDao transactionDao;
	@Override
	public void save(Transaction transaction) {
		this.transactionDao.save(transaction);		
	}

	@Override
	public void edit(Transaction transaction) {
		this.transactionDao.edit(transaction);	
		
	}

	@Override
	public Transaction getTransaction(Integer id) {
		return this.transactionDao.getTransaction(id);	
	}

	@Override
	public List<Object> getTransactionList(TransactionType transactionType, Integer recipientId, int beginIndex, int pageSize) {
		return this.transactionDao.getTransactionList(transactionType, recipientId, beginIndex, pageSize);
	}

	@Override
	public List<Transaction> getTransactionList(TransactionType transactionType, Integer customerOrVendorId)
	{
		return this.transactionDao.getTransactionList(transactionType, customerOrVendorId);
	}
	
	@Override
	public Object[] getCustomerInfo(Integer customerId) {
		return this.transactionDao.getCustomerInfo(customerId);
	}

	@Override
	public double getCustomerBalance(Integer customerId) {
		// TODO Auto-generated method stub
		return this.transactionDao.getCustomerBalance(customerId);
	}

	@Override
	public Object[] getVendorInfo(Integer vendorId) {
		return this.transactionDao.getVendorInfo(vendorId);
	}

	@Override
	public void delete(Integer transactionId) throws Exception
	{
		this.transactionDao.delete(transactionId);
		
	}
	
	@Override
	public List<Object> getTransactions(Map parameter, int offset, int numofRecords)
	{
		return this.transactionDao.getTransactions(parameter, offset, numofRecords);
	}

}
