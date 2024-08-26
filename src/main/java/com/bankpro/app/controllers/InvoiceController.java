package com.bankpro.app.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bankpro.app.dto.ApproveInvoiceDTO;
import com.bankpro.app.dto.ApproveInvoiceLogDTO;
import com.bankpro.app.dto.BeneficiaryDetailDTO;
import com.bankpro.app.dto.InvoiceApproverUserDTO;
import com.bankpro.app.dto.InvoiceDTO;
import com.bankpro.app.dto.InvoiceTypeDTO;
import com.bankpro.app.model.InvoiceApprover;
import com.bankpro.app.model.InvoiceDetails;
import com.bankpro.app.model.InvoiceTypeMaster;
import com.bankpro.app.services.InvoiceServices;

@Controller
@RequestMapping("/create-invoice/**")
public class InvoiceController {
	private static final String uploadFolderName = "InvoiceUpload";
	private static final Logger LOGGER = Logger.getLogger(InvoiceController.class);

	@Autowired
	InvoiceServices invSer;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/createInvoice", method = RequestMethod.POST)
	public void createInvoice(@RequestBody InvoiceDTO invDTO, Principal pri) {
		invSer.doCallForCreateInvoice(invDTO, pri.getName());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updateInvoice", method = RequestMethod.PUT)
	public void udpateInvoice(@RequestBody InvoiceDTO invDTO, Principal pin) {
		System.out.println("user name in contriller is   " + pin.getName());
		invSer.doUpdateInvoice(invDTO, pin.getName());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getDataForView", method = RequestMethod.GET)
	public List<InvoiceDTO> invoiceDataForView(Principal principal) {
		List<InvoiceDetails> invoiceObj = invSer.doGetValueForInvoice(principal.getName());
		System.out.println("in the methis of get the valueeeeeeeeeeeeeeeeeee");
		List<InvoiceDTO> lii = InvoiceDTO.mapFromInvoiceEntities(invoiceObj);
		return lii;


	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/batchUpload", method = RequestMethod.POST)
	public void batchFileUploadToCreateVendor(@RequestParam("fileName") String fileName, Principal pri) {
//		String rootPath = System.getProperty("catalina.home");
		String rootPath = "/home/ubuntu/paymentvista/bankprowebapp/target/bankproapp/resources/public/";
		File dir = new File(rootPath + File.separator + "tmpFiles");
		String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());	
		File mainDir = new File("/home/ubuntu/paymentvista/UploadedDocFolder/" + currentDate + File.separator + uploadFolderName);
//		File mainDir = new File("C:/PaymentVistaFiles/BatchFileUpload/" + currentDate + File.separator + uploadFolderName);
		System.out.println("mainDir                   " + mainDir);
		if (!mainDir.exists())
			mainDir.mkdirs();		
		File mainFileToMove = new File(mainDir + File.separator + fileName);		
		Path movefrom = FileSystems.getDefault().getPath(dir.getAbsolutePath() + File.separator + fileName);
		Path target = FileSystems.getDefault().getPath(mainDir + File.separator + fileName);
		try {
			Files.move(movefrom, target, StandardCopyOption.REPLACE_EXISTING);
			invSer.batchFileUploadOfCSV(mainFileToMove, pri.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getDataForBeneficiary", method = RequestMethod.GET)
	public List<BeneficiaryDetailDTO> benefeciaryList(Principal principal) {
		List<?> venArray = invSer.doGetValueOfBeneficiaryFromVendor(principal.getName());
		if(venArray!= null){
			List<BeneficiaryDetailDTO> lii = BeneficiaryDetailDTO.mapFromvendorBeneficiryName(venArray);
			return lii;
		}		
		return null;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/invoiceApprover", method = RequestMethod.GET)
	public List<InvoiceApproverUserDTO> invoiceApproverList(Principal principal) {
		List<?> venArray = invSer.doGetValueOfApproverForInvoice(principal.getName());
		if(venArray!= null){
			List<InvoiceApproverUserDTO> lii = InvoiceApproverUserDTO.mapFromApproverEntity(venArray);
			return lii;
		}		
		return null;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/pendingApprovalInvoice", method = RequestMethod.GET)
	public List<ApproveInvoiceDTO> approvalPendingInvoices(Principal principal) {
		Map<String,Object[]> venObj = invSer.doGetValueOfApprovalPendingList(principal.getName());
		System.out.println("venArray            "+venObj.size());
		if(venObj!= null){
			List<ApproveInvoiceDTO> lii = ApproveInvoiceDTO.mapFromApproverList(venObj);
			return lii;
		}		
		return null;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/invoiceDataForView", method = RequestMethod.GET)
	public InvoiceDTO invoiceDataForViewOnApproval(@RequestParam("invId") long invoiceId) {
		System.out.println("invoiceId               "+invoiceId);
		List<InvoiceDetails> invoiceObj = invSer.doGetValueForInvoiceBasisOnID(invoiceId);
		System.out.println("in the methis of get the valueeeeeeeeeeeeeeeeeee");
		InvoiceDTO lii = InvoiceDTO.mapFromInvoiceEntity(invoiceObj.get(0));
		return lii;
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updateInvoiceApproval", method = RequestMethod.PUT)
	public void updateInvoiceApprovalLog(@RequestParam("invoiceID") long invoiceId, @RequestParam("comment") String comment, Principal prin) {
		System.out.println("user name in contriller is   " + invoiceId);
		invSer.doUpdateApproveInvoice(comment,invoiceId, prin.getName());
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updateInvoiceDecline", method = RequestMethod.PUT)
	public void updateInvoiceDeclineLog(@RequestParam("invoiceID") long invoiceId, @RequestParam("comment") String comment, Principal prin) {
		System.out.println("invoice id going to decline in controller is   " + invoiceId);
		invSer.doUpdateDeclineInvoice(comment,invoiceId, prin.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getInvoiceType", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<InvoiceTypeDTO> getInvoiceType() {
		System.out.println("In Get Invoice Type Method ");
		List<InvoiceTypeMaster> invoiceTypeObj = invSer.getInvoiceType();		 
		List<InvoiceTypeDTO> invType = InvoiceTypeDTO.mapFromInvoiceTypeEntities(invoiceTypeObj);
		System.out.println(" Total Invoice Type available = " + invType.size());
		return invType;

	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/approverLog", method = RequestMethod.GET)
	public List<ApproveInvoiceLogDTO> getApproverLog(@RequestParam("invId") long invoiceId, Principal pri) {
		System.out.println("In getApproverLog method for invoice id : " + invoiceId);
		Map<Integer, String> mapForUserNameWithID = new HashMap<Integer, String>();
		List<InvoiceApprover> approverInvoiceList = invSer.getApproverObjList(invoiceId);
		List<?> userNameWithIDList = invSer.userNameBasedOnUserID();
		if(userNameWithIDList != null){
			for (Object obj : userNameWithIDList) {
				Object[] objRow = (Object[])obj;
				String userName = objRow[1].toString() + " " + objRow[2].toString();
				mapForUserNameWithID.put(Integer.parseInt(objRow[0].toString()), userName);
			}
			List<ApproveInvoiceLogDTO> apprList = ApproveInvoiceLogDTO.mapFromApproverInvEntities(approverInvoiceList, mapForUserNameWithID);
			return apprList;
		}		
		return null;
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/approvalInvoiceHistory", method = RequestMethod.GET)
	public List<ApproveInvoiceDTO> approvalInvoicesHistory(Principal principal) {
		Map<String,Object[]> venObj = invSer.getApprovalHistoryList(principal.getName());
		System.out.println("Approve Invoice Object size = " + venObj.size());
		if(venObj!= null){
			System.out.println("venObj is not null");
			List<ApproveInvoiceDTO> invHistory = ApproveInvoiceDTO.mapFromApproverList(venObj);
			return invHistory;
		}		
		return null;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/searchInvoiceData", method = RequestMethod.GET)
	public List<ApproveInvoiceDTO> searchInvoiceData(@RequestParam("dateFrom") String dateFrom,@RequestParam("dateTo")
	         String dateTo,@RequestParam("Name") String name,
			@RequestParam("amountFrom") long amountFrom,@RequestParam("amountTo") long amountTo, Principal pri) {
		
		Map<String,Object[]> venObj = invSer.searchInvoiceList(dateFrom,dateTo,name,amountFrom,amountTo,pri.getName());
		System.out.println("venArray            "+venObj.size());
		if(venObj!= null){
			List<ApproveInvoiceDTO> lii = ApproveInvoiceDTO.mapFromApproverList(venObj);
			return lii;
		}		
		return null;
				
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/searchInvcAprvlHistoryData", method = RequestMethod.GET)
	public List<ApproveInvoiceDTO> searchInvoiceApprovalHistoryData(@RequestParam("dateFrom") String dateFrom,@RequestParam("dateTo") 
	         String dateTo,@RequestParam("Name") String name,
			@RequestParam("amountFrom") long amountFrom,@RequestParam("amountTo") long amountTo, Principal pri) {
		
		Map<String,Object[]> venObj = invSer.searchInvoiceApprovalHistoryList(dateFrom,dateTo,name,amountFrom,amountTo,pri.getName());
		if(venObj!= null){
			List<ApproveInvoiceDTO> lii = ApproveInvoiceDTO.mapFromApproverList(venObj);
			return lii;
		}		
		return null;
				
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/searchDataForInvoice", method = RequestMethod.GET)
	public List<InvoiceDTO> searchDataForInvoice(
			@RequestParam("fromDate") String dateFrom,
			@RequestParam("toDate") String dateTo,
			@RequestParam("searchName") String searchName,
			@RequestParam("fromAmt") String amountFrom,
			@RequestParam("toAmt") String amountTo,
			@RequestParam("invoiceStatusField") int invoiceStatusField,
			@RequestParam("approvalStatusField") int approvalStatusField, 
			@RequestParam("approvalStatus") int approvalStatus, Principal pri) {

		List<InvoiceDetails> invoiceObj = invSer.getSearchDataForInvoice(
				dateFrom, dateTo, searchName, amountFrom, amountTo, invoiceStatusField, approvalStatusField, approvalStatus, pri.getName());
		if (invoiceObj != null) {
			System.out.println("in the methis of get the valueeeeeeeeeeeeeeeeeee   "+invoiceObj.size());
			List<InvoiceDTO> lii = InvoiceDTO.mapFromInvoiceEntities(invoiceObj);
			return lii;
		}
		return null;

	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getSelectedApprover", method = RequestMethod.GET)
	public List<String> fetchSelectedApproverListForInvoice(@RequestParam("invId") long invoiceId, Principal pri) {
		System.out.println("In getApproverLog method for invoice id : " + invoiceId);
		Map<Integer, String> mapForUserNameWithID = new HashMap<Integer, String>();
		List<InvoiceApprover> approverInvoiceList = invSer.getApproverObjList(invoiceId);
		if(approverInvoiceList != null && !approverInvoiceList.isEmpty()){
		List<?> userNameWithIDList = invSer.userNameBasedOnUserID();
		List<String> newList = new  ArrayList();
		if(userNameWithIDList != null){
			String sss = null;
			for (Object obj : userNameWithIDList) {
				Object[] objRow = (Object[])obj;
				String userName = objRow[1].toString() + " " + objRow[2].toString();
				mapForUserNameWithID.put(Integer.parseInt(objRow[0].toString()), userName);
			}
			for (InvoiceApprover object : approverInvoiceList) {
				newList.add(mapForUserNameWithID.get((int)object.getAppUserID()));
			}			
			return newList;
		}	}	
		return null;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/cancelOfInvoice", method = RequestMethod.GET)
	public Integer updateInvoiceForCancelInvoice(@RequestParam("invID") long invID, Principal prin) {
		System.out.println("invoice id going to decline in controller is   " + invID);
		int approvalStatus = invSer.doUpdateCancelInvoice(invID, prin.getName());
		return approvalStatus;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/cancelOfInvoiceAfterCheck", method = RequestMethod.PUT)
	public void cancelOfInvoiceAfterCheck(@RequestParam("invID") long invID, Principal prin) {
		System.out.println("invoice id going to decline in controller is   "+ invID);
		invSer.doUpdateCancelInvoiceAfterCheck(invID, prin.getName());

	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }
	

}
