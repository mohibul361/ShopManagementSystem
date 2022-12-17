package com.sencillo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sencillo.classes.DashboardInfo;
import com.sencillo.classes.VendorItemCount;
import com.sencillo.model.ItemType;
import com.sencillo.service.ItemService;
import com.sencillo.service.ItemTypeService;

@Controller
public class IndexController
{

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemTypeService itemTypeService;

	@RequestMapping("/index")
	public String index(Map<String, Object> map, HttpSession session)
	{		
		return "index";
	}
	
	@RequestMapping("/stock")
	public String getStockStatus(Map<String, Object> map, HttpSession session)
	{

		DashboardInfo dashboardInfo = this.itemService.getDashBoardDataNew1();
		map.put("dashBoardInfo", dashboardInfo);
		return "stock";
	}

	@RequestMapping("/dailyTransactions")
	public String getDailyTransactions(Model model)
	{

		Date date = new Date();
		List<Object> objects = this.itemService.getDailyTransactionReport(date);
		model.addAttribute("incomingSlipList", objects.get(0));
		model.addAttribute("deliverySlipList", objects.get(1));
		model.addAttribute("paymentList", objects.get(2));
		model.addAttribute("receiveList", objects.get(3));
		return "dailyTransactions";
	}
	
	/*
	 * @RequestMapping("/index") public String index(Map<String, Object> map) {
	 * 
	 * System.out.println("hello index");
	 * 
	 * List<ItemAvailableStatus> itemAvailableStatusList = this.itemService.getItemAvailableStatus();
	 * map.put("itemAvailableStatusList", itemAvailableStatusList); return "index"; }
	 */

	@RequestMapping("/vendorItemBalance/{itemTypeId}")
	public String getVendorBalanceByItemType(@PathVariable("itemTypeId") Integer itemTypeId, Model m)
	{

		ItemType itemType = this.itemTypeService.getItemType(itemTypeId);
		List<VendorItemCount> vendorItemCounts = this.itemService.getVendorItemCount(itemTypeId);
		m.addAttribute("vendorItemCounts", vendorItemCounts);
		m.addAttribute("itemType", itemType);
		return "vendorItemBalance";
	}
}
