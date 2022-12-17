package com.sencillo.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sencillo.classes.CustomerBalance;
import com.sencillo.classes.DeliverySlipInfo;
import com.sencillo.model.Slip;
import com.sencillo.model.SlipItem;
import com.sencillo.model.User;

public interface SlipDao
{
	public Slip save(Slip slip);

	public void edit(Slip slip, List<SlipItem> slipItems2remove);

	public List<Object> getSlipList(Map paramMap, int beginIndex, int pageSize);

	public Slip getSlip(Integer id);
	
	public List<DeliverySlipInfo> getDeliverySlipInfo(Integer itemTypeId, Integer customerId);
	
	public List<Object> getUnReplacedReturnSlip(int beginIndex, int pageSize);
	
	public List<Slip> getReplacementSlips();
	
	public List<Object> getSlipListNew(Map<String, String> paramMap, int beginIndex, int pageSize);
	
	public List<Slip> getPendingSlips(Map paramMap);
	
	public boolean approveSlips(List<Integer> slipIds, User approvedBy);
	
	public boolean getItemAvailableStatus(Integer vendorId, Integer itemTypeId, double qty, boolean isBagAvailabilityCheck);
	
	public List<CustomerBalance> getCustomerBalanceReportInfo(Date toDate);
}
