package com.sencillo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sencillo.editors.RecipientEditor;
import com.sencillo.editors.UserEditor;
import com.sencillo.editors.VendorEditor;
import com.sencillo.enums.TransactionType;
import com.sencillo.model.Recipient;
import com.sencillo.model.Transaction;
import com.sencillo.model.User;
import com.sencillo.model.Vendor;
import com.sencillo.service.RecipientService;
import com.sencillo.service.SlipService;
import com.sencillo.service.TransactionService;
import com.sencillo.service.UserService;
import com.sencillo.service.VendorService;
import com.sencillo.validator.TransactionValidator;

@Controller
public class TransactionController
{

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private SlipService slipService;

	@Autowired
	private RecipientService recipientService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private UserService userService;

	@Autowired
	TransactionValidator transactionValidator;

	@Autowired
	private RecipientEditor customerEditor;

	@Autowired
	private VendorEditor vendorEditor;

	@Autowired
	private UserEditor userEditor;

	@InitBinder
	private void dateBinder(WebDataBinder binder)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
		binder.registerCustomEditor(Recipient.class, this.customerEditor);
		binder.registerCustomEditor(Vendor.class, this.vendorEditor);
		binder.registerCustomEditor(User.class, this.userEditor);
	}

	@RequestMapping(value = "/collection")
	public String getCollection(@ModelAttribute Transaction transaction, Model m)
	{
		m.addAttribute("recipientList", this.recipientService.getRecipientList());
		transaction.setTransactionType(TransactionType.RECEIVE);
		m.addAttribute("transaction", transaction);
		return "collection";
	}

	@RequestMapping(value = "/collection/create", method = RequestMethod.POST)
	public String createCollections(@Valid @ModelAttribute Transaction transaction, BindingResult result, Model m,
			HttpSession session)
	{

		for (FieldError error : result.getFieldErrors())
		{
			System.out.println(error.getObjectName() + " - " + error.getField() + " - " + error.getDefaultMessage());
		}
		transactionValidator.validate(transaction, result);
		if (result.hasErrors())
		{
			return getCollection(transaction, m);
		}

		User user = this.userService.getUser((String) session.getAttribute("userName"));

		if (transaction.getId() == null)
		{
			transaction.setCreatedBy(user);
			this.transactionService.save(transaction);
		} else
		{
			this.transactionService.edit(transaction);
		}

		m.addAttribute("msg", "জমা সফলভাবে সংরক্ষিত হয়েছে।");
		return getCollection(new Transaction(), m);
	}

	@RequestMapping(value = "/collection/update/{pk}", method = RequestMethod.GET)
	public String updateCollections(@PathVariable Integer pk, @ModelAttribute Transaction transaction, Model m)
	{

		transaction = this.transactionService.getTransaction(pk);
		m.addAttribute("transaction", transaction);
		m.addAttribute("recipientList", this.recipientService.getRecipientList());
		return "collection";
	}

	@RequestMapping(value = "/transaction/delete/{pk}/{type}", method = RequestMethod.GET)
	public String deleteTransaction(@PathVariable Integer pk, @PathVariable Integer type, Model m) throws Exception
	{

		this.transactionService.delete(pk);
		if (type == 0)
		{
			return "redirect:/collections";
		} else if (type == 1)
		{
			return "redirect:/payments";
		} else
		{
			return "redirect:/openingBalances";
		}

	}

	@RequestMapping(value = "/customerInfo/{id}", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getCustomerInfo(@PathVariable("id") Integer customerId, Model m, Locale locale)
	{

		Object[] data = this.transactionService.getCustomerInfo(customerId);
		String returnText = "";
		if (locale.getLanguage() == "bn")
		{
			returnText += String.format("<p>মোবাইল: %s</p>", data[0]);
		} else
		{
			returnText += String.format("<p>Mobile: %s</p>", data[0]);
		}
		if (locale.getLanguage() == "bn")
		{
			returnText += String.format("<p>ঠিকানা: %s</p>", data[0]);
		} else
		{
			returnText += String.format("<p>Address: %s</p>", data[0]);
		}
		if (locale.getLanguage() == "bn")
		{
			if (data[2] == null)
			{
				returnText += String.format("<p>সর্বশেষ জমা: %s</p>", "কোন জমা নাই");
			} else
			{
				returnText += String.format("<p>সর্বশেষ জমা: %s</p>", data[2]);
			}

		} else
		{
			if (data[2] == null)
			{
				returnText += String.format("<p>Last Paid Amount: %s</p>", "No deposit Yet");
			} else
			{
				returnText += String.format("<p>Last Paid Amount: %s</p>", data[2]);
			}
		}
		if (locale.getLanguage() == "bn")
		{
			if (data[3] == null)
			{
				returnText += String.format("<p>সর্বশেষ জমার তারিখ: %s</p>", "কোন জমা নাই");
			} else
			{
				returnText += String.format("<p>সর্বশেষ জমার তারিখ: %s</p>",
						new SimpleDateFormat("EEE, d MMM yyyy").format(data[3]));
			}
		} else
		{
			if (data[3] == null)
			{
				returnText += String.format("<p>সর্বশেষ জমার তারিখ: %s</p>", "No deposit Yet");
			} else
			{
				returnText += String.format("<p>Last Payment Date: %s</p>",
						new SimpleDateFormat("yyyy-MM-dd").format(data[3]));
			}

		}

		return returnText;
	}

	@RequestMapping(value = "/customerBalance/{id}", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getCustomerBalance(@PathVariable("id") Integer customerId, Model m, Locale locale)
	{

		double balance = this.transactionService.getCustomerBalance(customerId);
		return Double.toString(balance);
	}

	@RequestMapping(value = "/collections")
	public String getAllCollection(Model m)
	{
		return "collectionList";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/collections", method = RequestMethod.POST)
	@ResponseBody
	public void getAllCollection(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jsonResult = new JSONObject();
		JSONArray dataArray = new JSONArray();
		try
		{
			int draw = Integer.parseInt(request.getParameter("draw"));
			int beginIndex = Integer.parseInt(request.getParameter("start"));
			int pageSize = Integer.parseInt(request.getParameter("length"));

			String recipientId = request.getParameter("recipientId");

			Map parameter = new HashMap();
			parameter.put("recipientId", recipientId != null && !"".equals(recipientId) ? new Integer(recipientId) : null);
			parameter.put("transactionType", TransactionType.RECEIVE);
			
			List<Object> resultList = this.transactionService.getTransactions(parameter, beginIndex, pageSize);
			List<Transaction> transactionList = (List<Transaction>) resultList.get(0);
			long recordsTotal = (Long) resultList.get(1);
			long recordsFiltered = (Long) resultList.get(2);
			Locale locale = LocaleContextHolder.getLocale();
			for (Transaction transaction : transactionList)
			{
				JSONArray ja = new JSONArray();
				ja.put(transaction.getRecipient().getName());
				ja.put(transaction.getTransactionNo());				
				ja.put(new SimpleDateFormat("dd-MM-yyyy").format(transaction.getDate()));
				ja.put(transaction.getAmount());
				ja.put(transaction.getRemarks());
				String editLink = String.format("<a href=collection/update/%s>এডিট করুন</a>", transaction.getId());
				ja.put(editLink);
				dataArray.put(ja);
			}

			try
			{
				jsonResult.put("recordsTotal", recordsTotal);
				jsonResult.put("recordsFiltered", recordsFiltered);
				jsonResult.put("data", dataArray);
				jsonResult.put("draw", draw);
			} catch (JSONException ex)
			{

			}

			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");
			PrintWriter out;
			try
			{
				out = response.getWriter();
				out.print(jsonResult);
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	// @RequestMapping(value = "/collections", method = RequestMethod.GET)
	// public String getAllCollection(Model m, HttpServletRequest request)
	// {
	// int page = 1;
	// int recordsPerPage = 50;
	// if (request.getParameter("page") != null)
	// {
	// page = Integer.parseInt(request.getParameter("page"));
	// }
	// String cname = null;
	// Integer customerId = new Integer(request.getParameter("cid"));
	//
	// customerId = new Integer(request.getParameter("cid"));
	// //cname = (String)request.getParameter("cname"); show garbage cname, thats why cname has been retrieved
	// if(customerId != -1)
	// {
	// cname = this.recipientService.getRecipient(customerId).getName();
	// }
	//
	// m.addAttribute("cid", customerId);
	// m.addAttribute("cname", cname);
	//
	// if (customerId == -1)
	// {
	// customerId = null;
	// }
	//
	// List<Object> returnList = this.transactionService.getTransactionList(TransactionType.RECEIVE, customerId,
	// (page - 1) * recordsPerPage, recordsPerPage);
	// int noOfRecords = (int) returnList.get(1);
	// int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
	// m.addAttribute("collectionList", returnList.get(0));
	// m.addAttribute("noOfPages", noOfPages);
	// m.addAttribute("currentPage", page);
	//
	// // m.addAttribute("recipientList", this.recipientService.getRecipientList());
	//
	// return "all_collection";
	// }

	@RequestMapping(value = "/collectionsByRecipient", method = RequestMethod.POST)
	public String getCollectionsByRecipient(HttpServletRequest request, Model m)
	{
		Integer customerId = null;
		if (!request.getParameter("deliveredTo").equals(""))
		{
			customerId = new Integer(request.getParameter("deliveredTo"));
		}

		String cname = request.getParameter("customerName");
		List<Transaction> collections = this.transactionService.getTransactionList(TransactionType.RECEIVE, customerId);
		m.addAttribute("collectionList", collections);
		m.addAttribute("customerId", customerId);
		m.addAttribute("customerName", cname);
		m.addAttribute("currentPage", 1);
		return "all_collection";
	}

	@RequestMapping(value = "/payment")
	public String getPayment(@ModelAttribute Transaction transaction, Model m)
	{
		m.addAttribute("vendorList", this.vendorService.getVendorList());
		transaction.setTransactionType(TransactionType.PAYMENT);
		m.addAttribute("transaction", transaction);
		return "payment";
	}

	@RequestMapping(value = "/payment/create", method = RequestMethod.POST)
	public String createPayment(@Valid @ModelAttribute Transaction transaction, BindingResult result, Model m,
			HttpSession session)
	{

		for (FieldError error : result.getFieldErrors())
		{
			System.out.println(error.getObjectName() + " - " + error.getField() + " - " + error.getDefaultMessage());
		}
		transactionValidator.validate(transaction, result);
		if (result.hasErrors())
		{
			return getPayment(transaction, m);
		}

		User user = this.userService.getUser((String) session.getAttribute("userName"));
		if (transaction.getId() == null)
		{
			transaction.setCreatedBy(user);
			this.transactionService.save(transaction);
		} else
		{
			this.transactionService.edit(transaction);
		}

		m.addAttribute("msg", "পরিশোধ সফলভাবে সংরক্ষিত হয়েছে।");
		return getPayment(new Transaction(), m);
	}

	@RequestMapping(value = "/payment/update/{pk}", method = RequestMethod.GET)
	public String updatePayments(@PathVariable Integer pk, @ModelAttribute Transaction transaction, Model m)
	{

		transaction = this.transactionService.getTransaction(pk);
		m.addAttribute("transaction", transaction);
		m.addAttribute("vendorList", this.vendorService.getVendorList());
		return "payment";
	}

	@RequestMapping(value = "/vendorInfo/{id}", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getVendorInfo(@PathVariable("id") Integer vendorId, Model m, Locale locale)
	{

		Object[] data = this.transactionService.getVendorInfo(vendorId);
		String returnText = "";
		if (locale.getLanguage() == "bn")
		{
			returnText += String.format("<p>মোবাইল: %s</p>", data[0]);
		} else
		{
			returnText += String.format("<p>Mobile: %s</p>", data[0]);
		}
		if (locale.getLanguage() == "bn")
		{
			returnText += String.format("<p>ঠিকানা: %s</p>", data[0]);
		} else
		{
			returnText += String.format("<p>Address: %s</p>", data[0]);
		}
		if (locale.getLanguage() == "bn")
		{
			if (data[2] == null)
			{
				returnText += String.format("<p>সর্বশেষ জমা: %s</p>", "কোন পরিশোধ নাই");
			} else
			{
				returnText += String.format("<p>সর্বশেষ জমা: %s</p>", data[2]);
			}

		} else
		{
			if (data[2] == null)
			{
				returnText += String.format("<p>Last Paid Amount: %s</p>", "No Payment Yet");
			} else
			{
				returnText += String.format("<p>Last Paid Amount: %s</p>", data[2]);
			}
		}
		if (locale.getLanguage() == "bn")
		{
			if (data[3] == null)
			{
				returnText += String.format("<p>সর্বশেষ জমার তারিখ: %s</p>", "কোন পরিশোধ নাই");
			} else
			{
				returnText += String.format("<p>সর্বশেষ জমার তারিখ: %s</p>",
						new SimpleDateFormat("EEE, d MMM yyyy").format(data[3]));
			}
		} else
		{
			if (data[3] == null)
			{
				returnText += String.format("<p>সর্বশেষ জমার তারিখ: %s</p>", "No payment Yet");
			} else
			{
				returnText += String.format("<p>Last Payment Date: %s</p>",
						new SimpleDateFormat("yyyy-MM-dd").format(data[3]));
			}

		}

		return returnText;
	}

//	@RequestMapping(value = "/payments")
//	public String getAllPayment(Model m, HttpServletRequest request)
//	{
//		// List<Transaction> payments = this.transactionService.getTransactionList(TransactionType.PAYMENT);
//		// m.addAttribute("paymentList", payments);
//		// return "all_payment";
//		int page = 1;
//		int recordsPerPage = 20;
//		if (request.getParameter("page") != null)
//		{
//			page = Integer.parseInt(request.getParameter("page"));
//		}
//		List<Object> returnList = this.transactionService.getTransactionList(TransactionType.PAYMENT, null, (page - 1)
//				* recordsPerPage, recordsPerPage);
//		int noOfRecords = (int) returnList.get(1);
//		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
//		m.addAttribute("paymentList", returnList.get(0));
//		m.addAttribute("noOfPages", noOfPages);
//		m.addAttribute("currentPage", page);
//
//		return "all_payment";
//	}
	
	@RequestMapping(value = "/payments")
	public String getAllPayment(Model m, HttpServletRequest request)
	{
		m.addAttribute("vendorList", this.vendorService.getVendorList());
		return "paymentList";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/payments", method=RequestMethod.POST)
	@ResponseBody
	public void getAllPayment(HttpServletRequest request, HttpServletResponse response)
	{

		JSONObject jsonResult = new JSONObject();
		JSONArray dataArray = new JSONArray();
		try
		{
			int draw = Integer.parseInt(request.getParameter("draw"));
			int beginIndex = Integer.parseInt(request.getParameter("start"));
			int pageSize = Integer.parseInt(request.getParameter("length"));

			String vendorId = request.getParameter("vendorId");

			Map parameter = new HashMap();
			parameter.put("vendorId", vendorId != null && !"".equals(vendorId) ? new Integer(vendorId) : null);
			parameter.put("transactionType", TransactionType.PAYMENT);
			
			List<Object> resultList = this.transactionService.getTransactions(parameter, beginIndex, pageSize);
			List<Transaction> transactionList = (List<Transaction>) resultList.get(0);
			long recordsTotal = (Long) resultList.get(1);
			long recordsFiltered = (Long) resultList.get(2);
			Locale locale = LocaleContextHolder.getLocale();
			for (Transaction transaction : transactionList)
			{
				JSONArray ja = new JSONArray();
				ja.put(transaction.getVendor().getName());
				ja.put(transaction.getTransactionNo());				
				ja.put(new SimpleDateFormat("dd-MM-yyyy").format(transaction.getDate()));
				ja.put(transaction.getAmount());
				ja.put(transaction.getRemarks());
				String editLink = String.format("<a href=payment/update/%s>এডিট করুন</a>", transaction.getId());
				ja.put(editLink);
				dataArray.put(ja);
			}

			try
			{
				jsonResult.put("recordsTotal", recordsTotal);
				jsonResult.put("recordsFiltered", recordsFiltered);
				jsonResult.put("data", dataArray);
				jsonResult.put("draw", draw);
			} catch (JSONException ex)
			{

			}

			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");
			PrintWriter out;
			try
			{
				out = response.getWriter();
				out.print(jsonResult);
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}


	@RequestMapping(value = "/openingBalance")
	public String getOpeningBalance(@ModelAttribute Transaction transaction, Model m)
	{
		m.addAttribute("vendorList", this.vendorService.getVendorList());
		m.addAttribute("recipientList", this.recipientService.getRecipientList());
		transaction.setTransactionType(TransactionType.OPENINGBALNCE);
		m.addAttribute("transaction", transaction);
		return "opening_balance";
	}

	@RequestMapping(value = "/openingBalance/create", method = RequestMethod.POST)
	public String createOpeningBalance(@Valid @ModelAttribute Transaction transaction, BindingResult result, Model m,
			HttpSession session)
	{

		for (FieldError error : result.getFieldErrors())
		{
			System.out.println(error.getObjectName() + " - " + error.getField() + " - " + error.getDefaultMessage());
		}
		transactionValidator.validate(transaction, result);
		if (result.hasErrors())
		{
			return getOpeningBalance(transaction, m);
		}

		User user = this.userService.getUser((String) session.getAttribute("userName"));

		if (transaction.getId() == null)
		{
			transaction.setCreatedBy(user);
			this.transactionService.save(transaction);
		} else
		{
			this.transactionService.edit(transaction);
		}

		m.addAttribute("msg", "ওপেনিং ব্যালেন্স সফলভাবে সংরক্ষিত হয়েছে।");
		return getOpeningBalance(new Transaction(), m);
	}

	@RequestMapping(value = "/openingBalances")
	public String getAllOpeningBalance(Model m, HttpServletRequest request)
	{
		int page = 1;
		int recordsPerPage = 20;
		if (request.getParameter("page") != null)
		{
			page = Integer.parseInt(request.getParameter("page"));
		}
		List<Object> returnList = this.transactionService.getTransactionList(TransactionType.OPENINGBALNCE, null,
				(page - 1) * recordsPerPage, recordsPerPage);
		int noOfRecords = (int) returnList.get(1);
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		m.addAttribute("openingBalanceList", returnList.get(0));
		m.addAttribute("noOfPages", noOfPages);
		m.addAttribute("currentPage", page);

		return "all_opening_balance";
	}

	@RequestMapping(value = "/openingBalance/update/{pk}", method = RequestMethod.GET)
	public String updateOpeningBalance(@PathVariable Integer pk, @ModelAttribute Transaction transaction, Model m)
	{

		transaction = this.transactionService.getTransaction(pk);
		return getOpeningBalance(transaction, m);

	}
}
