package com.sencillo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.dao.RecipientDao;
import com.sencillo.enums.RecipientType;
import com.sencillo.model.Recipient;

@Service
@Transactional
public class RecipientServiceImpl implements RecipientService
{

	@Autowired
	private RecipientDao recipientDao;
	
	@Override
	public void save(Recipient recipient)
	{
		this.recipientDao.save(recipient);
	}

	@Override
	public void edit(Recipient recipient)
	{
		this.recipientDao.edit(recipient);
	}

	@Override
	public Recipient getRecipient(Integer id)
	{
		return this.recipientDao.getRecipient(id);
	}

	@Override
	public List<Recipient> getRecipientList()
	{
		return this.recipientDao.getRecipientList();
	}

	@Override
	public List<Recipient> getRecipientListByType(RecipientType recipientType)
	{
		return this.recipientDao.getRecipientListByType(recipientType);
	}

	@Override
	public void delete(Integer recipientId) throws Exception {
		this.recipientDao.delete(recipientId);
				
	}

	@Override
	public List<Object> getRecipientList(Map<String, Object> paramMap, int beginIndex, int pageSize)
	{
		// TODO Auto-generated method stub
		return this.recipientDao.getRecipientList(paramMap, beginIndex, pageSize);
	}

	

}
