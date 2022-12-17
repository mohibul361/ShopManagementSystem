package com.sencillo.service;

import java.util.List;
import java.util.Map;

import com.sencillo.enums.RecipientType;
import com.sencillo.model.Recipient;

public interface RecipientService
{
	public void save(Recipient recipient);
	
	public void edit(Recipient recipient);

	public Recipient getRecipient(Integer id);
	
	public List<Recipient> getRecipientList();
	
	public List<Object> getRecipientList(Map<String, Object> paramMap, int beginIndex, int pageSize);
	
	public List<Recipient> getRecipientListByType(RecipientType recipientType);
	
	public void delete(Integer recipientId) throws Exception;
}
