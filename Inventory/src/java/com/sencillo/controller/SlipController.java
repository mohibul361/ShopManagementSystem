package com.sencillo.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.util.JRLoader;
//import net.sf.jasperreports.view.JasperViewer;


import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
//import net.sf.jasperreports.export.Exporter;
//import net.sf.jasperreports.export.SimpleExporterInput;
//import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.ParamParent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sencillo.editors.ItemEditor;
import com.sencillo.editors.ItemTypeAttributeEditor;
import com.sencillo.editors.ItemTypeEditor;
import com.sencillo.editors.ProblemTypeEditor;
import com.sencillo.editors.RecipientEditor;
import com.sencillo.editors.RegionEditor;
import com.sencillo.editors.SlipEditor;
import com.sencillo.editors.UserEditor;
import com.sencillo.editors.VendorEditor;
import com.sencillo.enums.ItemStatus;
import com.sencillo.enums.SlipStatus;
import com.sencillo.enums.SlipType;
import com.sencillo.forms.SlipApprovalForm;
import com.sencillo.model.Item;
import com.sencillo.model.ItemType;
import com.sencillo.model.ItemTypeAttribute;
import com.sencillo.model.ProblemType;
import com.sencillo.model.Recipient;
import com.sencillo.model.Region;
import com.sencillo.model.Slip;
import com.sencillo.model.SlipItem;
import com.sencillo.model.User;
import com.sencillo.model.Vendor;
import com.sencillo.service.ItemService;
import com.sencillo.service.ItemTypeService;
import com.sencillo.service.ProblemTypeService;
import com.sencillo.service.RecipientService;
import com.sencillo.service.RegionService;
import com.sencillo.service.SlipService;
import com.sencillo.service.UserService;
import com.sencillo.service.VendorService;
import com.sencillo.validator.SlipValidator;


//import com.mysql.jdbc.Connection;
import java.sql.Connection;

@Controller
public class SlipController
{
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		binder.setAutoGrowCollectionLimit(2048);
	}

	private Logger logger = Logger.getLogger(SlipController.class);

	@Autowired
	private ItemTypeService itemTypeService;

	@Autowired
	private SlipService slipService;

	@Autowired
	private UserService userService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private RecipientService recipientService;

	@Autowired
	private ItemTypeEditor itemTypeEditor;

	@Autowired
	private ItemTypeAttributeEditor itemTypeAttributeEditor;

	@Autowired
	private ItemEditor itemEditor;

	@Autowired
	private RecipientEditor customerEditor;

	@Autowired
	private UserEditor userEditor;

	@Autowired
	private SlipEditor slipEditor;

	@Autowired
	SlipValidator slipValidator;

	@Autowired
	private ProblemTypeEditor problemTypeEditor;

	@Autowired
	private RegionEditor regionEditor;

	@Autowired
	private RegionService regionService;

	@Autowired
	private ProblemTypeService problemTypeService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private VendorEditor vendorEditor;

	@InitBinder
	private void dateBinder(WebDataBinder binder)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
		binder.registerCustomEditor(Item.class, this.itemEditor);
		binder.registerCustomEditor(ItemType.class, this.itemTypeEditor);
		binder.registerCustomEditor(ItemTypeAttribute.class, this.itemTypeAttributeEditor);
		binder.registerCustomEditor(Recipient.class, this.customerEditor);
		binder.registerCustomEditor(User.class, this.userEditor);
		binder.registerCustomEditor(Slip.class, this.slipEditor);
		binder.registerCustomEditor(Region.class, this.regionEditor);
		binder.registerCustomEditor(ProblemType.class, this.problemTypeEditor);
		binder.registerCustomEditor(Vendor.class, this.vendorEditor);
	}

	@ModelAttribute
	public void navigationInfo(Model m, HttpSession session) // this method is for showing page path
	{

		String re = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
				.getRequestURL().toString();
		System.out.println("request : " + re);
		/*
		 * Map<Page, List<Page>> pageMap = (Map<Page, List<Page>>) session.getAttribute("pageList"); for(Map.Entry<Page,
		 * List<Page>> entry: pageMap.entrySet()) {
		 * 
		 * }
		 */
		// m.addAttribute("", );
	}

	@RequestMapping(value = "/incomingSlip/create")
	public String createIncoming(@ModelAttribute Slip slip, Model m)
	{
		slip.setSlipType(SlipType.INCOMING);
		slip.setSlipStatus(SlipStatus.SAVED);
		m.addAttribute("itemTypeList", this.itemTypeService.getItemTypeList());
		// m.addAttribute("approverList", this.userService.getApproverList());
		m.addAttribute("vendorList", this.vendorService.getVendorList());
		m.addAttribute("type", "create");
		return "incomingSlip";
	}

	@RequestMapping(value = "/addNewItemToIS/{rowCount}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String addNewItemToIS(@PathVariable("rowCount") int rowCount, Model m)
	{
		// int rowSerial = rowCount + 1;
		int rowSerial = rowCount;
		rowCount--;
		String qtyInBagField = String
				.format("<input  type='text' class=qty-in-bag id=slipItems%s.quantityInBag name=slipItems[%s].quantityInBag value=''>",
						rowCount, rowCount);
		String qtyInKgField = String
				.format("<input type='text' class=qty-in-kg id=slipItems%s.quantityInKg name=slipItems[%s].quantityInKg value=''>",
						rowCount, rowCount);
		String unitPriceField = String.format(
				"<input type='text' class=unit-price id=slipItems%s.unitPrice name=slipItems[%s].unitPrice value=''>",
				rowCount, rowCount);
		String subTotalField = String
				.format("<input type='text' class=sub-total id=slipItems%s.subTotalPrice name=slipItems[%s].subTotalPrice value='' >",
						rowCount, rowCount);

		String remove = String.format("<a href='#' class=remove data-index=%s>remove</a>", rowCount);

		String returnText = String.format("<tr id=slipItems%s.wrapper><td>%s</td>", rowCount, rowSerial);

		returnText += String.format("<td><select name=slipItems[%s].itemType>", rowCount);
		for (ItemType itemType : this.itemTypeService.getItemTypeList())
		{
			System.err.println(">>" + itemType.getName());
			returnText += String.format("<option value='%s'>%s</option>", itemType.getId(), itemType.getName());
		}
		returnText += "</select></td>";

		returnText += String.format("<td>%s</td>", qtyInBagField);

		returnText += String.format("<td>%s</td>", qtyInKgField);

		// returnText += String.format("<td><select name=slipItems[%s].unit>", rowCount);
		// for (Unit unit : Unit.values())
		// {
		// returnText += String.format("<option value='%s'>%s</option>", unit.name(), unit.name());
		// }
		// returnText += "</select></td>";

		returnText += String.format("<td>%s</td>", unitPriceField);
		returnText += String.format("<td>%s</td>", subTotalField);
		returnText += String.format("<td>%s</td>", remove);
		returnText += "</tr>";

		returnText += String.format("<input type='hidden' id=slipItems%s.remove name=slipItems[%s].remove value=0>",
				rowCount, rowCount);
		returnText += String.format("<input type='hidden' id=slipItems%s.id name=slipItems[%s].id value=''></tr>",
				rowCount, rowCount);
		return returnText;
	}

	@RequestMapping(value = "/incomingSlip/create", method = RequestMethod.POST)
	public String saveSlipForm(@Valid Slip slip, BindingResult bindingResult, Model m, HttpSession session,
			RedirectAttributes redirectAttributes)
	{
		for (FieldError error : bindingResult.getFieldErrors())
		{
			System.out.println(error.getObjectName() + " - " + error.getField() + " - " + error.getDefaultMessage());
		}
		slipValidator.validate(slip, bindingResult);
		manageSlipItems(slip);
		if (bindingResult.hasErrors())
		{
			return createIncoming(slip, m);
		}
		setSlipExtraProperty(slip, SlipType.INCOMING, session);

		slip = this.slipService.save(slip);
		redirectAttributes.addFlashAttribute("slip", slip);
		return "redirect:/slipSuccess";
	}

	@RequestMapping(value = "/incomingSlip/update/{pk}", method = RequestMethod.GET)
	public String updateIncoming(@PathVariable Integer pk, @ModelAttribute Slip slip, Model m, boolean slipLoadRequired)
	{
		if (!slipLoadRequired)
		{
			m.addAttribute("slip", this.slipService.getSlip(pk));
		}
		m.addAttribute("itemTypeList", this.itemTypeService.getItemTypeList());
		m.addAttribute("vendorList", this.vendorService.getVendorList());
		m.addAttribute("approverList", this.userService.getApproverList());
		m.addAttribute("type", "update");
		return "incomingSlip";
	}

	@RequestMapping(value = "/incomingSlip/update/{pk}", method = RequestMethod.POST)
	public String updateIncoming(@PathVariable Integer pk, @Valid @ModelAttribute Slip slip, BindingResult result,
			HttpSession session, Model m, RedirectAttributes redirectAttributes)
	{
		for (FieldError error : result.getFieldErrors())
		{
			System.out.println(error.getObjectName() + " - " + error.getField() + " - " + error.getDefaultMessage());
		}
		List<SlipItem> slipItems2remove = manageSlipItems(slip);

		if (result.hasErrors())
		{
			return updateIncoming(pk, slip, m, true);
		}
		this.slipService.edit(slip, slipItems2remove);
		redirectAttributes.addFlashAttribute("slip", slip);
		return "redirect:/slipSuccess";
	}

	@RequestMapping(value = "/slipList")
	public String getSlipList(Model m)
	{
		m.addAttribute("slipTypeEnums", SlipType.values());
		return "slipList";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/slipList", method = RequestMethod.POST)
	public void searchData(HttpServletRequest request, HttpServletResponse response) throws ParseException
	{
		JSONObject jsonResult = new JSONObject();
		JSONArray dataArray = new JSONArray();

		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements())
		{
			String paramName = (String) params.nextElement();
			System.out.println("Attribute Name - " + paramName + ", Value - " + request.getParameter(paramName));
		}

		Map paramMap = new HashMap();
		String slipType = request.getParameter("slipType");
		String slipNumber = request.getParameter("slipNumber");

		if (slipType.isEmpty())
		{
			paramMap.put("slipType", "ALL");
		} else
		{
			paramMap.put("slipType", slipType);
		}
		paramMap.put("slipNumber", slipNumber);

		int beginIndex = Integer.parseInt(request.getParameter("start"));
		int pageSize = Integer.parseInt(request.getParameter("length"));
		int draw = Integer.parseInt(request.getParameter("draw"));

		List<Object> resultList = this.slipService.getSlipList(paramMap, beginIndex, pageSize);
		List<Slip> slips = (List<Slip>) resultList.get(0);
		int recordsTotal = (int) resultList.get(1);
		int recordsFiltered = (int) resultList.get(2);
		String editLink = "";
		/* SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy"); */
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		for (Slip s : slips)
		{
			JSONArray ja = new JSONArray();
			ja.put(s.getSlipNumber());
			ja.put(s.getSlipType().toString());
			ja.put(sdf.format(s.getSlipDate()));
			if (s.getSlipType() == SlipType.INCOMING)
			{
				ja.put(s.getVendor().getName());
			} else
			{
				ja.put(s.getDeliveredTo().getName());
			}

			try
			{
				ja.put(s.getTotalQtyInKg());
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
			ja.put(s.getRemarks());
			ja.put(sdf.format(s.getCreationDate()));
			ja.put(s.getCreatedBy().getFullName());
			switch (s.getSlipType())
			{
			case INCOMING:
				editLink = String.format("<a href=incomingSlip/update/%s>দেখুন</a>", s.getId());
				break;
			case DELIVERY:
				editLink = String.format("<a href=deliverySlip/update/%s>দেখুন</a>", s.getId());
				break;
			}
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

		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/export/slipList", method = RequestMethod.GET)
	public ModelAndView getExcel()
	{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("globalSearchValue", "");
		List<Object> resultList = this.slipService.getSlipList(paramMap, 0, 50);
		List<Slip> slipList = (List<Slip>) resultList.get(0);
		return new ModelAndView("SlipListExcel", "slipList", slipList);
	}

	/********************************************** Delivery Slip *******************************************************************/

	@RequestMapping(value = "/deliverySlip/create")
	public String createDelivery(@ModelAttribute Slip slip, Model m)
	{
		slip.setSlipType(SlipType.DELIVERY);
		slip.setSlipStatus(SlipStatus.SAVED);
		m.addAttribute("recipientList", this.recipientService.getRecipientList());
		m.addAttribute("itemTypeList", this.itemTypeService.getItemTypeList());
		m.addAttribute("vendorList", this.vendorService.getVendorList());
		m.addAttribute("approverList", this.userService.getApproverList());
		m.addAttribute("type", "create");
		return "deliverySlip";
	}

	@RequestMapping(value = "/addNewItemToDS/{rowCount}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String addNewItemToDS(@PathVariable("rowCount") int rowCount, Model m)
	{
		// int rowSerial = rowCount + 1;
		int rowSerial = rowCount;
		rowCount--;
		String qtyInBagField = String
				.format("<input  type='text' style='width:150px' class=qty-in-bag id=slipItems%s.quantityInBag name=slipItems[%s].quantityInBag value=''>",
						rowCount, rowCount);
		String qtyInKgField = String
				.format("<input type='text' style='width:150px' class=qty-in-kg id=slipItems%s.quantityInKg name=slipItems[%s].quantityInKg value=''>",
						rowCount, rowCount);
		String unitPriceField = String
				.format("<input type='text' style='width:150px' class=unit-price id=slipItems%s.unitPrice name=slipItems[%s].unitPrice value=''>",
						rowCount, rowCount);
		String subTotalField = String
				.format("<input type='text' style='width:150px' class=sub-total id=slipItems%s.subTotalPrice name=slipItems[%s].subTotalPrice value='' >",
						rowCount, rowCount);

		String remove = String.format("<a href='#' class=remove data-index=%s>remove</a>", rowCount);

		String returnText = String.format("<tr id=slipItems%s.wrapper><td>%s</td>", rowCount, rowSerial);

		returnText += String.format("<td><select class=vendor name=slipItems[%s].vendor>", rowCount);
		for (Vendor vendor : this.vendorService.getVendorList())
		{
			System.err.println(">>" + vendor.getName());
			returnText += String.format("<option value='%s'>%s</option>", vendor.getId(), vendor.getName());
		}
		returnText += "</select></td>";

		returnText += String.format("<td><select class=item-type name=slipItems[%s].itemType>", rowCount);
		for (ItemType itemType : this.itemTypeService.getItemTypeList())
		{
			System.err.println(">>" + itemType.getName());
			returnText += String.format("<option value='%s'>%s</option>", itemType.getId(), itemType.getName());
		}
		returnText += "</select></td>";

		returnText += String.format("<td>%s</td>", qtyInBagField);

		returnText += String.format("<td>%s</td>", qtyInKgField);

		// returnText += String.format("<td><select name=slipItems[%s].unit>", rowCount);
		// for (Unit unit : Unit.values())
		// {
		// returnText += String.format("<option value='%s'>%s</option>", unit.name(), unit.name());
		// }
		// returnText += "</select></td>";

		returnText += String.format("<td>%s</td>", unitPriceField);
		returnText += String.format("<td>%s</td>", subTotalField);
		returnText += String.format("<td>%s</td>", remove);
		returnText += "</tr>";

		returnText += String.format("<input type='hidden' id=slipItems%s.remove name=slipItems[%s].remove value=0>",
				rowCount, rowCount);
		returnText += String.format("<input type='hidden' id=slipItems%s.id name=slipItems[%s].id value=''></tr>",
				rowCount, rowCount);
		return returnText;
	}

	@RequestMapping(value = "/deliverySlip/create", method = RequestMethod.POST)
	public String saveSlip(@Valid Slip slip, BindingResult bindingResult, HttpSession session, Model m,
			RedirectAttributes redirectAttributes)
	{
		for (FieldError error : bindingResult.getFieldErrors())
		{
			System.out.println(error.getObjectName() + " - " + error.getField() + " - " + error.getDefaultMessage());
		}
		slipValidator.validate(slip, bindingResult);
		manageSlipItems(slip);
		if (bindingResult.hasErrors())
		{
			return createDelivery(slip, m);
		}
		setSlipExtraProperty(slip, SlipType.DELIVERY, session);

		slip = this.slipService.save(slip);
		redirectAttributes.addFlashAttribute("slip", slip);
		return "redirect:/slipSuccess";
	}

	@RequestMapping(value = "/deliverySlip/update/{pk}", method = RequestMethod.GET)
	public String updateDelivery(@PathVariable Integer pk, @ModelAttribute Slip slip, Model m, boolean slipLoadRequired)
	{
		if (!slipLoadRequired)
		{
			m.addAttribute("slip", this.slipService.getSlip(pk));
		}
		m.addAttribute("itemTypeList", this.itemTypeService.getItemTypeList());
		m.addAttribute("vendorList", this.vendorService.getVendorList());
		m.addAttribute("recipientList", this.recipientService.getRecipientList());
		m.addAttribute("approverList", this.userService.getApproverList());
		m.addAttribute("type", "update");
		return "deliverySlip";
	}

	@RequestMapping(value = "/deliverySlip/update/{pk}", method = RequestMethod.POST)
	public String updateDelivery(@PathVariable Integer pk, @Valid @ModelAttribute Slip slip, BindingResult result,
			HttpSession session, Model m, RedirectAttributes redirectAttributes)
	{
		for (FieldError error : result.getFieldErrors())
		{
			System.out.println(error.getObjectName() + " - " + error.getField() + " - " + error.getDefaultMessage());
		}
		List<SlipItem> slipItems2remove = manageSlipItems(slip);

		if (result.hasErrors())
		{
			return updateDelivery(pk, slip, m, true);
		}
		this.slipService.edit(slip, slipItems2remove);
		redirectAttributes.addFlashAttribute("slip", slip);
		return "redirect:/slipSuccess";
	}

	@RequestMapping(value = "/addItemToDS/{serial}/{rowCount}/{warranty}", method = RequestMethod.GET, produces = "text/html")
	public @ResponseBody
	String searchItem(@PathVariable("serial") String serial, @PathVariable("rowCount") int rowCount,
			@PathVariable("warranty") int warranty, Model m)
	{
		String returnText = "";
		Item item = this.itemService.getItemByStatus(serial, ItemStatus.INSTALLED_AND_CHECKED);
		if (item == null)
		{
			return returnText;
		}
		int rowSerial = rowCount + 1;

		String remove = String.format("<a href='#' class=remove data-index=%s>remove</a>", rowCount);
		returnText += String.format(
				"<tr id=slipItems%s.wrapper><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>",
				rowCount, rowSerial, item.getItemType().getName(), item.getName(), item.getSerial(), warranty, remove);
		returnText += String.format("<input type='hidden' name=slipItems[%s].item value=%s>", rowCount, item.getId());
		returnText += String.format("<input type='hidden' name=slipItems[%s].itemType value=%s>", rowCount, item
				.getItemType().getId());
		returnText += String.format("<input type='hidden' name=slipItems[%s].warranty value=%s>", rowCount, warranty);
		returnText += String.format("<input type='hidden' name=slipItems[%s].quantity value=%s>", rowCount, 1);
		returnText += String.format("<input type='hidden' id=slipItems%s.remove name=slipItems[%s].remove value=0>",
				rowCount, rowCount);
		returnText += String.format("<input type='hidden' id=slipItems%s.id name=slipItems[%s].id value=''></tr>",
				rowCount, rowCount);

		return returnText;
	}

	/*********************************** common method **************************************************/
	// Manage dynamically added or removed slipitems
	private List<SlipItem> manageSlipItems(Slip slip)
	{
		// Store the slipitems which shouldn't be persisted
		List<SlipItem> slipItems2remove = new ArrayList<SlipItem>();
		int slipItemTotal = 0;
		if (slip.getSlipItems() != null || !slip.getSlipItems().isEmpty())
		{
			for (Iterator<SlipItem> i = slip.getSlipItems().iterator(); i.hasNext();)
			{
				SlipItem slipItem = i.next();
				// If the remove flag is true, remove the slipitem from the list
				if (slipItem.getRemove() == 1)
				{
					slipItems2remove.add(slipItem);
					i.remove();
					// Otherwise, perform the links
				} else
				{
					slipItemTotal += slipItem.getQuantityInKg();
					slipItem.setSlip(slip);
				}
			}
		}
		slip.setTotalQtyInKg(slipItemTotal);
		return slipItems2remove;
	}

	@RequestMapping(value = "/slipSuccess")
	public String slipSuccess(@ModelAttribute("slip") Slip slip, Model m)
	{
		System.out.println("success ");
		m.addAttribute("slip", slip);
		return "slipSuccess";
	}

	private void setSlipExtraProperty(Slip slip, SlipType slipType, HttpSession session)
	{
		slip.setSlipType(slipType);
		slip.setSlipStatus(SlipStatus.SAVED);
		User user = this.userService.getUser((String) session.getAttribute("userName"));
		slip.setCreatedBy(user);
		Date d = (Date) new java.util.Date();
		slip.setCreationDate(d);
	}

	/**
	 * Handle request to download a PDF document
	 */
	@RequestMapping(value = "/downloadPDF/{slipId}", method = RequestMethod.GET)
	public ModelAndView downloadExcel(@PathVariable Integer slipId)
	{

		Slip slip = this.slipService.getSlip(slipId);
		// return a view which will be resolved by an excel view resolver
		return new ModelAndView("pdfView", "slip", slip);
	}

	// It works with jasper viewer
	// @SuppressWarnings("unchecked")
	// @RequestMapping(value = "/viewDSReport/{slipId}", method = RequestMethod.GET)
	// @ResponseBody
	// public void getDSReportByViewer(@PathVariable Integer slipId, HttpServletRequest request, HttpServletResponse
	// response)
	// throws JRException, IOException {
	//
	// // InputStream jasperStream = this.getClass().getResourceAsStream("/reports/ds.jasper");
	// // JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
	// JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("/reports/ds.jrxml"));
	// JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	// Map params = new HashMap<String, Integer>();
	// params.put("slip_id", slipId);
	// JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connectDB());
	//
	// JasperViewer.viewReport(jasperPrint, false);
	// }

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewDSReport/{slipId}", method = RequestMethod.GET)
	// @ResponseBody
	public ResponseEntity<byte[]> getDSReport(@PathVariable Integer slipId, HttpServletRequest request,
			HttpServletResponse response) throws JRException, IOException
	{

		try
		{
			JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("/reports/ds.jrxml"));
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			Map params = new HashMap<String, Integer>();
			params.put("slip_id", slipId);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connectDB());
			System.out.println("before pdf generation");

			byte[] contents = getPDFContents(jasperPrint);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "delivery_report.pdf";
			// headers.add("content-disposition", "attachment; filename=" + filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<>(contents, headers, HttpStatus.OK);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;

	}

	public static byte[] getPDFContents(JasperPrint jp) throws ClassNotFoundException, JRException, IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try
		{
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jp));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		} catch (JRException e)
		{
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewISReport/{slipId}", method = RequestMethod.GET)
	//@ResponseBody
	public ResponseEntity<byte[]> getISReport(@PathVariable Integer slipId, HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException, ClassNotFoundException
	{
		JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("/reports/is.jrxml"));
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		Map params = new HashMap<String, Integer>();
		params.put("slip_id", slipId);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connectDB());
		//JasperViewer.viewReport(jasperPrint, false);
		byte[] contents = getPDFContents(jasperPrint);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = "incoming_report.pdf";
		// headers.add("content-disposition", "attachment; filename=" + filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return new ResponseEntity<>(contents, headers, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value = "/viewISReport/{slipId}", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public byte[] getISReport(@PathVariable Integer slipId, HttpServletRequest request,
	 * HttpServletResponse response) throws JRException, IOException { InputStream jasperStream =
	 * this.getClass().getResourceAsStream("/reports/is.jasper"); Map params = new HashMap<String, Integer>();
	 * params.put("slip_id", slipId); JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
	 * JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connectDB());
	 * 
	 * Exporter exporter = new HtmlExporter(); final ByteArrayOutputStream out = new ByteArrayOutputStream();
	 * exporter.setExporterOutput(new SimpleHtmlExporterOutput(out)); exporter.setExporterInput(new
	 * SimpleExporterInput(jasperPrint)); exporter.exportReport(); return out.toByteArray(); }
	 */

	private Connection connectDB()
	{
		// //////////////////check the following three DB parameters and change them if necessery//////////////
		String databaseName = "jdbc:mysql://localhost:3306/inv_trial";
		String userName = "root";
		String password = "root";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection jdbcConnection = (Connection) DriverManager.getConnection(databaseName, userName, password);
			return jdbcConnection;
		} catch (Exception ex)
		{
			String connectMsg = "Could not connect to the database:" + ex.getMessage() + " " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
		}
		return null;

	}

	@RequestMapping(value = "/customerReport")
	public String getLedger(Model m)
	{
		m.addAttribute("recipientList", this.recipientService.getRecipientList());
		m.addAttribute("reportParameterCB", new ReportParameter());
		m.addAttribute("reportParameterCL", new ReportParameter());
		return "customerReport";
	}

	@RequestMapping(value = "/viewCLedger", method = RequestMethod.POST)
	//@ResponseBody
	public ResponseEntity<byte[]> getCReport(@ModelAttribute("reportParameterCL") ReportParameter reportParameter)
	{
		try
		{
			Map<String, Object> params = new HashMap<String, Object>();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = new SimpleDateFormat("yy/MM/dd").parse(reportParameter.getFromDate());
			Date toDate = new SimpleDateFormat("yy/MM/dd").parse(reportParameter.getToDate());
			Integer customerId = new Integer(reportParameter.getRecipient());
			Date fromDate1 = df.parse(df.format(fromDate));
			Date toDate1 = df.parse(df.format(toDate));
			params.put("fromdate", fromDate1);
			params.put("todate", toDate1);
			params.put("customer_id", customerId);

			// InputStream jasperStream = this.getClass().getResourceAsStream("/reports/CLedgerRoot.jasper");
			// JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("/reports/CLedgerRoot.jrxml"));
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connectDB());
//			JasperViewer.viewReport(jasperPrint, false);
			byte[] contents = getPDFContents(jasperPrint);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "customer_ledger_report.pdf";
			// headers.add("content-disposition", "attachment; filename=" + filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<>(contents, headers, HttpStatus.OK);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/viewCBalance", method = RequestMethod.POST)
	//@ResponseBody
	public ResponseEntity<byte[]> getCBalance(ReportParameter reportParameter)
	{
		try
		{
			Map<String, Object> params = new HashMap<String, Object>();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date toDate = new SimpleDateFormat("yy/MM/dd").parse(reportParameter.getToDate());
			Date toDate1 = df.parse(df.format(toDate));
			params.put("todate", toDate1);
			JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream(
					"/reports/customerBalance.jrxml"));
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connectDB());
			//JasperViewer.viewReport(jasperPrint, false);
			byte[] contents = getPDFContents(jasperPrint);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "customer_balance_report.pdf";
			// headers.add("content-disposition", "attachment; filename=" + filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<>(contents, headers, HttpStatus.OK);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/vendorReport")
	public String getVendorLedger(Model m)
	{
		m.addAttribute("vendorList", this.vendorService.getVendorList());
		m.addAttribute("reportParameterVB", new ReportParameter());
		m.addAttribute("reportParameterVL", new ReportParameter());
		return "vendorReport";
	}

	@RequestMapping(value = "/viewVLedger", method = RequestMethod.POST)
	//@ResponseBody
	public ResponseEntity<byte[]> getVReport(ReportParameter reportParameter)
	{
		try
		{
			Map<String, Object> params = new HashMap<String, Object>();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = new SimpleDateFormat("yy/MM/dd").parse(reportParameter.getFromDate());
			Date toDate = new SimpleDateFormat("yy/MM/dd").parse(reportParameter.getToDate());
			Integer vendorId = new Integer(reportParameter.getVendor());
			Date fromDate1 = df.parse(df.format(fromDate));
			Date toDate1 = df.parse(df.format(toDate));

			params.put("fromdate", fromDate1);
			params.put("todate", toDate1);
			params.put("vendor_id", vendorId);

			// InputStream jasperStream = this.getClass().getResourceAsStream("/reports/VLedgerRoot.jasper");
			// JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("/reports/VLedgerRoot.jrxml"));
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connectDB());
			//JasperViewer.viewReport(jasperPrint, false);
			byte[] contents = getPDFContents(jasperPrint);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "vendor_ledger_report.pdf";
			// headers.add("content-disposition", "attachment; filename=" + filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<>(contents, headers, HttpStatus.OK);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/viewVBalance", method = RequestMethod.POST)
	//@ResponseBody
	public ResponseEntity<byte[]> getVBalance(ReportParameter reportParameter)
	{
		try
		{
			Map<String, Object> params = new HashMap<String, Object>();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date toDate = new SimpleDateFormat("yy/MM/dd").parse(reportParameter.getToDate());
			Date toDate1 = df.parse(df.format(toDate));
			params.put("todate", toDate1);
			// InputStream jasperStream = this.getClass().getResourceAsStream("/reports/vendorBalance.jasper");
			// JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			JasperDesign jasperDesign = JRXmlLoader
					.load(getClass().getResourceAsStream("/reports/vendorBalance.jrxml"));
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connectDB());
			//JasperViewer.viewReport(jasperPrint, false);
			byte[] contents = getPDFContents(jasperPrint);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "vendor_balance_report.pdf";
			// headers.add("content-disposition", "attachment; filename=" + filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<>(contents, headers, HttpStatus.OK);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/deliveryReport")
	public String getDeliveryReport(Model m)
	{
		/* m.addAttribute("itemTypeList", this.itemTypeService.getItemTypeList()); */
		m.addAttribute("recipientList", this.recipientService.getRecipientList());
                m.addAttribute("reportParameter", new ReportParameter());
		return "deliveryReport";
	}

	@RequestMapping(value = "/viewDeliveryReport", method = RequestMethod.POST)
//	@ResponseBody
	public ResponseEntity<byte[]> getDeliveryReport(ReportParameter reportParameter)
	{
		try
		{
			// InputStream jasperStream = this.getClass().getResourceAsStream("/reports/deliveryReport.jasper");
			JasperDesign jasperDesign = JRXmlLoader.load(getClass()
					.getResourceAsStream("/reports/deliveryReport.jrxml"));
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			Map<String, Object> params = new HashMap<String, Object>();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = new SimpleDateFormat("yy/MM/dd").parse(reportParameter.getFromDate());
			Date toDate = new SimpleDateFormat("yy/MM/dd").parse(reportParameter.getToDate());

			Integer customerId = null;
			if (!reportParameter.getRecipient().equals(""))
			{
				customerId = new Integer(reportParameter.getRecipient());
			}

			Date fromDate1 = df.parse(df.format(fromDate));
			Date toDate1 = df.parse(df.format(toDate));

			String sql = "", cName = "";
			if (customerId == null)
			{
				sql = "select * from slip s join recipient r on s.customer_id=r.id join company where s.slip_type="
						+ SlipType.DELIVERY.ordinal() + " and s.slip_date between $P{fromdate} and $P{todate}";
				cName = "সব";
			} else
			{
				sql = "select * from slip s join recipient r on s.customer_id=r.id join company where s.slip_type="
						+ SlipType.DELIVERY.ordinal() + " and s.customer_id=" + customerId
						+ " and s.slip_date between $P{fromdate} and $P{todate}";
				Recipient recipient = this.recipientService.getRecipient(customerId);
				cName = recipient.getName();
			}

			params.put("fromdate", fromDate1);
			params.put("todate", toDate1);
			params.put("sql", sql);
			params.put("cname", cName);

			// JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connectDB());
			byte[] contents = getPDFContents(jasperPrint);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "delivery_report.pdf";
			// headers.add("content-disposition", "attachment; filename=" + filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<>(contents, headers, HttpStatus.OK);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
                return null;
	}

	@RequestMapping(value = "/viewDeliveryReportHtml", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getDeliveryReportHTML(HttpServletRequest request)
	{
		try
		{
			InputStream jasperStream = this.getClass().getResourceAsStream("/reports/deliveryReport.jasper");
			Map<String, Object> params = new HashMap<String, Object>();

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = new SimpleDateFormat("yy/MM/dd").parse(request.getParameter("fromDate"));
			Date toDate = new SimpleDateFormat("yy/MM/dd").parse(request.getParameter("toDate"));

			Date fromDate1 = df.parse(df.format(fromDate));
			Date toDate1 = df.parse(df.format(toDate));

			Integer customerId = null;
			// if(!reportParameter.getRecipient().equals(""))
			// {
			// customerId = new Integer(reportParameter.getRecipient());
			// }
			if (!request.getParameter("recipient").equals(""))
			{
				customerId = new Integer(request.getParameter("recipient"));
			}

			String sql = "", cName = "";
			if (customerId == null)
			{
				sql = "select * from slip s join recipient r on s.customer_id=r.id where s.slip_type="
						+ SlipType.DELIVERY.ordinal() + " and s.slip_date between $P{fromdate} and $P{todate}";
				cName = "সব";
			} else
			{
				sql = "select * from slip s join recipient r on s.customer_id=r.id where s.slip_type="
						+ SlipType.DELIVERY.ordinal() + " and s.customer_id=" + customerId
						+ " and s.slip_date between $P{fromdate} and $P{todate}";
				Recipient recipient = this.recipientService.getRecipient(customerId);
				cName = recipient.getName();
			}

			params.put("fromdate", fromDate1);
			params.put("todate", toDate1);
			params.put("sql", sql);
			params.put("cname", cName);

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connectDB());

			// Exporter exporter = new HtmlExporter();
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			// exporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
			// exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			// exporter.exportReport();
			return out.toByteArray();
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/incomingReport")
	public String getIncomingReport(Model m)
	{
		m.addAttribute("vendorList", this.vendorService.getVendorList());
                m.addAttribute("reportParameter", new ReportParameter());
		return "incomingReport";
	}

	@RequestMapping(value = "/viewIncomingReport", method = RequestMethod.POST)
	//@ResponseBody
	public ResponseEntity<byte[]> getIncomingReport( ReportParameter reportParameter)
	{
		try
		{
			// InputStream jasperStream = this.getClass().getResourceAsStream("/reports/incomingReport.jasper");
			JasperDesign jasperDesign = JRXmlLoader.load(getClass()
					.getResourceAsStream("/reports/incomingReport.jrxml"));
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			Map<String, Object> params = new HashMap<String, Object>();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = new SimpleDateFormat("yy/MM/dd").parse(reportParameter.getFromDate());
			Date toDate = new SimpleDateFormat("yy/MM/dd").parse(reportParameter.getToDate());

			Integer vendorId = null;
			if (!reportParameter.getVendor().equals(""))
			{
				vendorId = new Integer(reportParameter.getVendor());
			}

			Date fromDate1 = df.parse(df.format(fromDate));
			Date toDate1 = df.parse(df.format(toDate));

			String sql = "", vName = "";
			if (vendorId == null)
			{
				sql = "select * from slip s join vendor v on s.vendor_id=v.id join company where s.slip_type="
						+ SlipType.INCOMING.ordinal() + " and s.slip_date between $P{fromdate} and $P{todate}";
				vName = "সব";
			} else
			{
				sql = "select * from slip s join vendor v on s.vendor_id=v.id join company where s.slip_type="
						+ SlipType.INCOMING.ordinal() + " and s.vendor_id=" + vendorId
						+ " and s.slip_date between $P{fromdate} and $P{todate}";
				Vendor vendor = this.vendorService.getVendor(vendorId);
				vName = vendor.getName();
			}

			params.put("fromdate", fromDate1);
			params.put("todate", toDate1);
			params.put("sql", sql);
			params.put("vname", vName);

			// JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connectDB());
			byte[] contents = getPDFContents(jasperPrint);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "delivery_report.pdf";
			// headers.add("content-disposition", "attachment; filename=" + filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<>(contents, headers, HttpStatus.OK);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
                return null;

	}

	
	@RequestMapping(value = "/purchaseSaleReport")
	public String getpurchaseSaleReport(Model m)
	{
		m.addAttribute("itemTypeList", this.itemTypeService.getItemTypeList());
		m.addAttribute("vendorList", this.vendorService.getVendorList());
                m.addAttribute("reportParameter", new ReportParameter());
		return "purchaseSaleReport";
	}

	@RequestMapping(value = "/viewPurchaseSaleReport", method = RequestMethod.POST)
//	@ResponseBody
	public ResponseEntity<byte[]> getPurchaseSaleReport(ReportParameter reportParameter)
	{
		try
		{
//			InputStream jasperStream = this.getClass().getResourceAsStream("/reports/purchaseSale.jrxml");
                        JasperDesign jasperDesign = JRXmlLoader.load(getClass()
					.getResourceAsStream("/reports/purchaseSale.jrxml"));
                        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			Map<String, Object> params = new HashMap<String, Object>();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = new SimpleDateFormat("yy/MM/dd").parse(reportParameter.getFromDate());
			Date toDate = new SimpleDateFormat("yy/MM/dd").parse(reportParameter.getToDate());

			Integer vendorId = null;
			if (!reportParameter.getVendor().equals(""))
			{
				vendorId = new Integer(reportParameter.getVendor());
			}

			Date fromDate1 = df.parse(df.format(fromDate));
			Date toDate1 = df.parse(df.format(toDate));

			Vendor vendor = this.vendorService.getVendor(vendorId);
			String vName = vendor.getName();

			Integer itemTypeId = null;
			String itemType = reportParameter.getItemType();
			if (!itemType.equals(""))
			{
				itemTypeId = new Integer(itemType);
			}

			params.put("fromdate", fromDate1);
			params.put("todate", toDate1);
			params.put("vendor_id", vendorId);
			params.put("item_type_id", itemTypeId);
			params.put("vname", vName);

			//JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connectDB());
			byte[] contents = getPDFContents(jasperPrint);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "delivery_report.pdf";
			// headers.add("content-disposition", "attachment; filename=" + filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<>(contents, headers, HttpStatus.OK);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
                return null;
	}

	@RequestMapping(value = "/vendorItemBalanceReport")
	public String getVendorItemBalanceReport(Model m)
	{
		m.addAttribute("vendorList", this.vendorService.getVendorList());
                m.addAttribute("reportParameter", new ReportParameter());
		return "vendorItemBalanceReport";
	}

	@RequestMapping(value = "/viewVendorItemBalanceReport", method = RequestMethod.POST)
//	@ResponseBody
	public ResponseEntity<byte[]> getVendorItemBalanceReport(ReportParameter reportParameter)
	{
		try
		{
			JasperDesign jasperDesign = JRXmlLoader.load(getClass()
					.getResourceAsStream("/reports/vendorItemBalance.jrxml"));
                        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                        
			Integer vendorId = null;
			if (!reportParameter.getVendor().equals(""))
			{
				vendorId = new Integer(reportParameter.getVendor());
			}

			Map<String, Object> params = new HashMap<String, Object>();
			Vendor vendor = this.vendorService.getVendor(vendorId);
			String vName = vendor.getName();

			params.put("vid", vendorId);
			params.put("vname", vName);

//			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connectDB());
			byte[] contents = getPDFContents(jasperPrint);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "delivery_report.pdf";
			// headers.add("content-disposition", "attachment; filename=" + filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<>(contents, headers, HttpStatus.OK);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
                return null;
	}
	
	@RequestMapping(value = "/checkItemStatus/{vendorId}/{itemTypeId}/{qty}/{isBagCheck}")
	@ResponseBody
	public boolean checkItemAvailabilityStatus(@PathVariable("vendorId") Integer vendorId,
			@PathVariable("itemTypeId") Integer itemTypeId, @PathVariable("qty") double qty,
			@PathVariable("isBagCheck") boolean isBagCheck)
	{
		return this.slipService.getItemAvailableStatus(vendorId, itemTypeId, qty, isBagCheck);
	}

	@RequestMapping(value = "/approveSlips", method = RequestMethod.GET)
	public String approveSlips(Model m) throws ParseException
	{
		return "approve_slip";
	}

	@RequestMapping(value = "/findPendingSlips", method = RequestMethod.POST)
	public String findPendingSlips(HttpServletRequest request, Model m) throws ParseException
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate1 = new SimpleDateFormat("yy/MM/dd").parse(request.getParameter("fromDate"));
		Date fromDate = df.parse(df.format(fromDate1));

		Date toDate1 = new SimpleDateFormat("yy/MM/dd").parse(request.getParameter("toDate"));
		Date toDate = df.parse(df.format(toDate1));

		Map<String, Date> paramMap = new HashMap<String, Date>();
		paramMap.put("fromDate", fromDate);
		paramMap.put("toDate", toDate);

		List<Slip> pendingSlipList = this.slipService.getPendingSlips(paramMap);

		m.addAttribute("pendingSlipList", pendingSlipList);

		return "approve_slip";
	}

	@RequestMapping(value = "/approve", method = RequestMethod.POST)
	public @ResponseBody
	String approvePendingSlips(@RequestBody SlipIds slipIds)
	{
		System.out.print("userid: " + slipIds.getUserId());
		User approvedBy = this.userService.getUser(slipIds.getUserId());
		boolean isSuccess = this.slipService.approveSlips(slipIds.getSlipIds(), approvedBy);
		if (isSuccess)
		{
			return "চালান অনুমোদন সফল হয়েছে!";
		} else
		{
			return "চালান অনুমোদন বার্থ হয়েছে!";
		}
	}

	@RequestMapping(value = "/viewIC/{slipId}", method = RequestMethod.GET)
	public String viewIC(@PathVariable("slipId") Integer slipId, Model m)
	{
		m.addAttribute("slip", slipService.getSlip(slipId));
		return "viewIC";
	}

	@RequestMapping(value = "/viewDC/{slipId}", method = RequestMethod.GET)
	public String viewDC(@PathVariable("slipId") Integer slipId, Model m)
	{
		m.addAttribute("slip", slipService.getSlip(slipId));
		return "viewDC";
	}

	@RequestMapping(value = "/viewCustomerBalance", method = RequestMethod.GET)
	public String viewCustomerBalance(HttpServletRequest request, Model m) throws ParseException
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date toDate1 = new SimpleDateFormat("yy/MM/dd").parse(request.getParameter("toDateForCB"));
		Date toDate = df.parse(df.format(toDate1));
		m.addAttribute("customerBalances", slipService.getCustomerBalanceReportInfo(toDate));
		return "viewCustomerBalance";
	}

}

class SlipIds
{
	Integer userId;
	List<Integer> slipIds;

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public List<Integer> getSlipIds()
	{
		return slipIds;
	}

	public void setSlipIds(List<Integer> slipIds)
	{
		this.slipIds = slipIds;
	}

}

class ReportParameter
{
	private String fromDate;
	private String toDate;
	private String vendor;
	private String recipient;
	private String itemType;

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getVendor()
	{
		return vendor;
	}

	public void setVendor(String vendor)
	{
		this.vendor = vendor;
	}

	public String getRecipient()
	{
		return recipient;
	}

	public void setRecipient(String recipient)
	{
		this.recipient = recipient;
	}

	public String getItemType()
	{
		return itemType;
	}

	public void setItemType(String itemType)
	{
		this.itemType = itemType;
	}

}