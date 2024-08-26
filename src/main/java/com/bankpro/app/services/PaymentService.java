package com.bankpro.app.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankpro.app.dao.InvoiceRepository;
import com.bankpro.app.dao.PaymentRepository;
import com.bankpro.app.dao.VendorInfoRepository;
import com.bankpro.app.dto.PaymentDTO;
import com.bankpro.app.model.AuditLogTrail;
import com.bankpro.app.model.DocInfoDetail;
import com.bankpro.app.model.InvoiceDetails;
import com.bankpro.app.model.OrganizationDetail;
import com.bankpro.app.model.Payment;
import com.bankpro.app.model.PaymentApprover;
import com.bankpro.app.model.Users;
import com.bankpro.app.model.Vendor;

/**
 * @author Lakharwal
 *
 */

@Service
public class PaymentService {
	
	private long sessionUserId;
	private int orgID;
	private int startRow = 0;
	private Scanner reader = null;
	private Map<String, Object> mapToMaintainFetchQueryObjInBatchUpload = new HashMap();
	private List<String[]> errorList = new ArrayList<String[]>();
	
	@Autowired
	PaymentRepository payRepo;
	
	@Autowired
	InvoiceRepository invoiceRep;
	
	@Autowired
	VendorInfoRepository vir;
	
	

	/**
	 * @return the errorList
	 */
	public List<String[]> getErrorList() {
		return errorList;
	}

	/**
	 * @param errorList the errorList to set
	 */
	public void setErrorList(List<String[]> errorList) {
		this.errorList = errorList;
	}

	@Transactional
	public void addPayment(PaymentDTO payDTO, String userName) {
		Payment pay = new Payment();
		InvoiceDetails invDetail = null;
		List<Vendor> vendList = vir.getVendorObjBasedOnIDToUpdate(payDTO.getVendorId());
		if (vendList != null && !vendList.isEmpty()) {
			Vendor ven = vendList.get(0);
			pay.setVendorDetail(ven);

			if (payDTO.getInvoiceNo() != null && !payDTO.getInvoiceNo().trim().isEmpty()) {
				List<InvoiceDetails> invList = invoiceRep.getInvoiceObjBasedOnInvoiceNo(payDTO.getInvoiceNo().trim(), orgID);
				if (invList != null && !invList.isEmpty()) {
					invDetail = invList.get(0);
				} else {
					invDetail = new InvoiceDetails();
					invDetail.setInvoiceNumber(payDTO.getInvoiceNo());
					invDetail.setInvoiceCreatedBy((int) sessionUserId);
					invDetail.setInvoiceCreatedDate(new Date());
				}
			} else {
				invDetail = new InvoiceDetails();
				invDetail.setInvoiceCreatedBy((int) sessionUserId);
				invDetail.setInvoiceCreatedDate(new Date());
			}
			pay.setInvoiceDetail(invDetail);

			pay.setPaymentAmount(payDTO.getPaymentAmount());
			pay.setPaymentProcessDate(payDTO.getPaymentProcessDate());
			pay.setPaymentDate(payDTO.getPaymentDate());
			pay.setRemarks(payDTO.getRemarks());

			pay.setPayOrgId(orgID);
			pay.setCreatedBy((int) sessionUserId);
			pay.setCreatedDate(new Date());

			List<?> payAppr = payDTO.getSelectedPaymentApproverList();
			if (payAppr != null & !payAppr.isEmpty()) {
				for (int i = 0; i < payAppr.size(); i++) {
					PaymentApprover appVendorObj = new PaymentApprover();
					LinkedHashMap<?, ?> approverName = (LinkedHashMap<?, ?>) payAppr.get(i);
					if (i == 0) {
						appVendorObj.setPaymentApprovalAccess(1);
					}
					long userID = Long.valueOf(approverName.get("userId").toString());
					appVendorObj.setAppUserID(userID);
					appVendorObj.setAppCreatedBy((int) sessionUserId);
					appVendorObj.setAppCreatedDate(new Date());
					pay.getPayApprover().add(appVendorObj);
				}
			}

     		long paymentId = payRepo.savePayment(pay);
			doSaveFilesOfManuallyAdded(payDTO, paymentId);

			// Going to maintain the trail of updation
			AuditLogTrail alt = new AuditLogTrail("PaymentDetail", "Create",
					"Payment Create", "New Payment", "New Payment", userName, paymentId);
			payRepo.saveAuditTrail(alt);
		}
	}

	@Transactional
	public List<?> doGetValueOfBeneficiaryFromVendor(String userName) {
		List<?> li = invoiceRep.queryToFetchBeneficiaryFromVendor(orgID);
		return li;
	}
	
	@Transactional
	public void doUpdateApprovePayment(String comment, long paymentId,String userName) {
		LinkedHashMap<Long, PaymentApprover> payApproverMapWithUserID = new LinkedHashMap<>();
		List<PaymentApprover> payAppList = payRepo.doGetApproverListBasedOnPaymentIDWithStatusCondition(paymentId);
		if (payAppList != null && !payAppList.isEmpty()) {
			for (PaymentApprover payAppObj : payAppList) {
				payApproverMapWithUserID.put(payAppObj.getAppUserID(),payAppObj);
			}
			PaymentApprover payAppObj = payApproverMapWithUserID.get(sessionUserId);
			udpatePaymentApprover(payAppObj, sessionUserId, comment);
			payApproverMapWithUserID.remove(sessionUserId);
			if (!payApproverMapWithUserID.isEmpty()) {
				Map.Entry<Long, PaymentApprover> entry = payApproverMapWithUserID.entrySet().iterator().next();
				long key = entry.getKey();
				PaymentApprover value = entry.getValue();
				value.setPaymentApprovalAccess(1);
				payRepo.saveAppPayment(value);
				Payment payObj = payRepo.getPaymentObjBasedOnID(paymentId).get(0);
				if (payObj.getApprovalStatus() == 0) {
					payObj.setApprovalStatus(2);
					payRepo.savePayment(payObj);
				}
			} else {
				payRepo.updatePaymentStatusBasedOnPaymentID(paymentId, 1);
			}
			AuditLogTrail alt = new AuditLogTrail("PaymentDetail", "Approve", "Payment Approve","Payment Approve", 
					 "New Payment", userName, paymentId);				
			payRepo.saveAuditTrail(alt);
		}

	}
	
	@Transactional
	public List<PaymentApprover> doGetValueForApprovalPayment(String userName) {
		Object[] row = invoiceRep.fetchInvoiceApprovalPendingList(userName);
		sessionUserId = Long.valueOf(row[0].toString());
		orgID = ((OrganizationDetail)row[1]).getOrgID();
		List<PaymentApprover> payAppList = payRepo.getListFromPaymentApprovalPending(sessionUserId);
//		OrganizationDetail orgObj = (OrganizationDetail)row[1];		
		return payAppList;

	}
	
	@Transactional
	public void udpatePaymentApprover(PaymentApprover payAppObj, long sessionUserID, String comment){
		payAppObj.setAppUpdatedBy((int)sessionUserID);
		payAppObj.setAppComment(comment);
		payAppObj.setAppUpdatedDate(new Date());
		payAppObj.setPaymentApprovalAccess(2);
		payAppObj.setAppStatus(1);
		payRepo.saveAppPayment(payAppObj);
		
	}

	public List<?> getPaymentApproverList(String userName) {
		List<?> li = payRepo.getPaymentApprover(orgID);
		return li;
	}
	
/*	@Transactional
	public List<InvoiceDetails> getInvoiceForVendor(long vendorId, String userName) {
//		List<Users> sessionUserList= invoiceRep.getUserObejectBasedOnUserName(userName);
//		Users sessionUserObj = sessionUserList.get(0);
//		int  orgId = sessionUserObj.getUserOrganization().getOrgID();
		List<InvoiceDetails> invDetails = payRepo.getInvoiceForVendor(vendorId, orgID);
		System.out.println("In PaymentService Invoices Found Total invoices found = " + invDetails.size());
		return invDetails;
	}*/
	
	@Transactional
	public List<Payment> getPaymentList(String userName) {
		Object[] row = invoiceRep.fetchInvoiceApprovalPendingList(userName);
		sessionUserId = Long.valueOf(row[0].toString());
		orgID = ((OrganizationDetail)row[1]).getOrgID();
		List<Payment> payList = payRepo.getPaymentList(orgID);
		return payList;
	}
	
	@Transactional
	public void doUpdateDeclinedPayment(String comment, long paymentId,String userName) {
		Payment payObj = null;
		List<PaymentApprover> payAppList = payRepo.doGetApproverListBasedOnPaymentIDWithStatusCondition(paymentId);
		for (PaymentApprover payAppObj : payAppList) {
			if(sessionUserId == payAppObj.getAppUserID()){
				payAppObj.setAppComment(comment);
				payObj = payAppObj.getPayment();
			}
			payAppObj.setAppUpdatedBy((int)sessionUserId);
			payAppObj.setPaymentApprovalAccess(2);
			payAppObj.setAppUpdatedDate(new Date());
			payAppObj.setAppStatus(3);  //3 means its in now declined state
			payRepo.saveAppPayment(payAppObj);
		}
		if(payObj != null){
			payObj.setApprovalStatus(3);
			payRepo.savePayment(payObj);
		}		
		AuditLogTrail alt = new AuditLogTrail("paymentDetail", "Declined", "Payment Declined","Payment Declined", 
				"Declined", userName, paymentId);				
		payRepo.saveAuditTrail(alt);
	}
	
	@Transactional
	public List<PaymentApprover> getApprovalLog(long payId) {
		List<PaymentApprover> payList = payRepo.doGetApproverListBasedOnPayment(payId);
		return payList;
	}
	
	@Transactional
	public void doSaveFilesOfManuallyAdded(PaymentDTO payDTO, long refID) {
		if (payDTO.getNewAddedDocListForPayment() != null) {
			for (int i = 0; i < payDTO.getNewAddedDocListForPayment().size(); i++) {
				DocInfoDetail newObj = new DocInfoDetail();
				LinkedHashMap<?, ?> DocMap = (LinkedHashMap<?, ?>) payDTO.getNewAddedDocListForPayment().get(i);
				newObj.setDocManuallyGivenFileName(DocMap.get("manuallyGivenFileName").toString());
				newObj.setDocFileName(DocMap.get("fileName").toString());
				newObj.setDocReferenceId((int) refID);
				newObj.setDocReferenceType("paymentDetail");
				invoiceRep.saveDocInfo(newObj);
			}
		}

	}
	
	@Transactional
	public void batchFileUploadOfCSV(File fullFileName, String userName) {
		try {
			errorList = new ArrayList<String[]>();
			System.out.println("file name final to upload      "+fullFileName);
			List<String[]> persistList = new ArrayList<String[]>();
			FileInputStream fis = new FileInputStream(fullFileName);
			reader = new Scanner(fis);
			int rowCount = countRows(fullFileName);
			System.out.println(" no. of row isw      " + rowCount);
			for (int i = 1; i <= rowCount; i++) {
				String[] finalArrayToPersist = buildFileReaderArray(i);
				if(finalArrayToPersist!= null){
					if(finalArrayToPersist[1] != "END"){
						persistList.add(finalArrayToPersist);						
					}					
				}
			}
			if(errorList == null || errorList.isEmpty()){
				for (String[] persistArray : persistList) {
					doCreateNewPayment(persistArray,userName);					
				}
			}
			startRow = 0;
			reader =null;
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public int countRows(File fullFileName) {
		int count = 0;
		try {
			LineNumberReader ln = new LineNumberReader(new java.io.FileReader(fullFileName));
			while (ln.readLine() != null) {
				count++;
			}
			count++;
			ln.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;

	} 

	@Transactional
	public String[] buildFileReaderArray(int rowNo) {
		String[] record = new String[10];
		if (reader.hasNextLine()) {
			if (startRow == 0) {
				String firstRow = reader.nextLine();
				startRow = 1;
				record = new String[0];
				return null;

			} else {
				SimpleDateFormat ss= new SimpleDateFormat("dd/MM/yyyy");
				record = reader.nextLine().split(",", -1);
//				for (int i = 0; i < record.length; i++) {
//					System.out.println("record             " + record[i]);
//				}
				if (record[0] != null && !record[0].trim().isEmpty()) {
					List<?> venList = payRepo.doGetVendorBasedOnName(record[0].trim(), orgID);
					if (venList.size() == 1) {
						Object[] result = (Object[]) venList.get(0);
						mapToMaintainFetchQueryObjInBatchUpload.put(record[0].trim(), (Vendor) result[0]);
					} else {
						String errorMsg = "Invalid Beneficiary Name found";
						errorList.add(maintainErrorArray("A",String.valueOf(rowNo), record[0], errorMsg));
					}
				} else {
					String errorMsg = "Beneficiary name must be given.";
					errorList.add(maintainErrorArray("A",String.valueOf(rowNo), record[0], errorMsg));
				}
				 if(record[1] != null && !record[1].trim().isEmpty() && !record[1].trim().matches("[0-9]+(\\.){0,1}[0-9]*")){
					 String errorMsg = "Please enter the valid Payment amount value";
					 errorList.add(maintainErrorArray("B", String.valueOf(rowNo), record[1], errorMsg));
				 }else if(record[1] == null || record[1].trim().isEmpty()){
					 String errorMsg = "Payment amount field can not be empty";
					 errorList.add(maintainErrorArray("B", String.valueOf(rowNo), record[1], errorMsg));
				 }
				if (record[2] != null && !record[2].trim().isEmpty()) {
					List<InvoiceDetails> invList = payRepo.doGetInvoiceBasedOnInvoiceNo(record[2].trim(), orgID);
					if (invList.size() == 1) {
						InvoiceDetails invObj = (InvoiceDetails)invList.get(0);
						mapToMaintainFetchQueryObjInBatchUpload.put(record[2].trim(), invObj);
					} else {
						 String errorMsg = "Invalid invoice No. found";
						 errorList.add(maintainErrorArray("C", String.valueOf(rowNo), record[2], errorMsg));
					}
				} else {
					String errorMsg = "Invoice no field not be empty";
					 errorList.add(maintainErrorArray("C", String.valueOf(rowNo), record[2], errorMsg));
				}
				
				if(record[3] != null && !record[3].trim().isEmpty()){
					 try {
						ss.parse(record[3].trim());
					} catch (ParseException e) {
						String errorMsg = "Please input the correct Process date format(DD/MM/YYYY)";
						errorList.add(maintainErrorArray("D", String.valueOf(rowNo), record[3], errorMsg));
					}
				 }else{
					 String errorMsg = "Process date field can't be empty";
					 errorList.add(maintainErrorArray("D", String.valueOf(rowNo), record[3], errorMsg));
				 }
				
				if (record[4] != null && !record[4].trim().isEmpty()) {
					try {
						ss.parse(record[4].trim());
					} catch (ParseException e) {
						String errorMsg = "Please input the correct Payment date format(DD/MM/YYYY)";
						errorList.add(maintainErrorArray("E",String.valueOf(rowNo), record[4], errorMsg));
					}
				} else {
					String errorMsg = "Payment date field can't be empty";
					errorList.add(maintainErrorArray("E",String.valueOf(rowNo), record[4], errorMsg));
				}
			}
		} else {
			record[1] = "END";
			return record;
		}
		return record;
	}
	
	public void doCreateNewPayment(String[] record, String userName){
		SimpleDateFormat ss= new SimpleDateFormat("dd/MM/yyyy");
		Payment pay = new Payment();
		pay.setPaymentAmount(Double.valueOf(record[1].trim()));
		try {
			pay.setPaymentProcessDate(ss.parse(record[3].trim()));
			pay.setPaymentDate(ss.parse(record[4].trim()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pay.setRemarks(record[5]);		
		pay.setPayOrgId(orgID);
		pay.setCreatedBy((int)sessionUserId);
		pay.setCreatedDate(new Date());		
		if(mapToMaintainFetchQueryObjInBatchUpload.containsKey(record[0].trim())){
			Vendor venObj = (Vendor)mapToMaintainFetchQueryObjInBatchUpload.get(record[0].trim());
			pay.setVendorDetail(venObj);
		}
		if(mapToMaintainFetchQueryObjInBatchUpload.containsKey(record[2].trim())){
			InvoiceDetails invObj = (InvoiceDetails)mapToMaintainFetchQueryObjInBatchUpload.get(record[2].trim());
			pay.setInvoiceDetail(invObj);
		}
		
		long paymentId = payRepo.savePayment(pay);
		
		// Going to maintain the trail of updation
		AuditLogTrail alt = new AuditLogTrail("PaymentDetail", "Create", "Batch Upload","New Payment", "New Payment", userName, paymentId);				
		payRepo.saveAuditTrail(alt);
		
		
	}
	
	public String[] maintainErrorArray(String columnName, String rowNo, String fieldValue, String errorMsg){
		String[] errorArray = new String[5];
		errorArray[0] = columnName;
		errorArray[1] = rowNo;
		errorArray[2] = fieldValue;
		errorArray[3] = errorMsg;		
		return errorArray;
	}
	
	@Transactional
	public void doUpdateCancelPayment(long paymentId, String userName) {
		List<PaymentApprover> payAppList = payRepo.doGetApproverListBasedOnPayment(paymentId);
		System.out.println("payAppList          "+payAppList.size());
		if (payAppList != null && !payAppList.isEmpty()) {
//			boolean paymentFlag = false;
//			for (PaymentApprover payObj1 : payAppList) {
//				if (payObj1.getAppStatus() == 1) {
//					paymentFlag = true;
//					System.out.println("the user name founddddddddd");
//					throw new IllegalArgumentException("The selected payment can not be cancel becouse one of
//                              your approver is already approve same payment");
//				}
//			}
//			if (!paymentFlag) {
//				System.out.println("paymentFlag       "+paymentFlag);
				for (PaymentApprover payObj2 : payAppList) {
					payObj2.setAppStatus(4);
					payObj2.setAppUpdatedBy((int)sessionUserId);
					payObj2.setAppUpdatedDate(new Date());
					payRepo.saveAppPayment(payObj2);
				}
				payRepo.updatePaymentStatusBasedOnPaymentID(paymentId, 4);
				AuditLogTrail alt = new AuditLogTrail("PaymentDetail","Cancel", "Cancel Payment", "Pending", "Cancel",userName, paymentId);
				payRepo.saveAuditTrail(alt);
//			}
		}else{
			payRepo.updatePaymentStatusBasedOnPaymentID(paymentId, 4);
			AuditLogTrail alt = new AuditLogTrail("PaymentDetail","Cancel", "Cancel Payment", "Pending", "Cancel", userName, paymentId);
			payRepo.saveAuditTrail(alt);
		}
	}
	
	@Transactional
	public void savePaymentAndPaymentApproval(String approverComment, String[] dataRequestList, String saveCondition,
			String[] approverList, int reqListSize, String userName) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, String> mapForFinalVlaue = new HashMap();
		if (dataRequestList != null) {
			System.out.println("dataRequestList       " + dataRequestList);
			System.out.println("dataRequestList  size     "+ dataRequestList.length);
			if (reqListSize == 1) {
				for (String demo1 : dataRequestList) {
					demo1 = demo1.replaceAll("\"", "").replace("{", "").replace("}", "").intern();
					String[] finalValue = demo1.split(":");
					mapForFinalVlaue.put(finalValue[0], finalValue[1]);
				}
				System.out.println("map value in     " + mapForFinalVlaue);
				commonModifyOfPayment(mapForFinalVlaue, approverComment, saveCondition, approverList, userName, format);
			} else {
				for (int i = 0; i < dataRequestList.length; i++) {
					System.out.println("dataRequestList        "+ dataRequestList[i]);
					String[] firstSplit = dataRequestList[i].toString().split(",");
					for (String demo1 : firstSplit) {
						demo1 = demo1.replaceAll("\"", "").replace("{", "").replace("}", "").intern();
						String[] finalValue = demo1.split(":");
						mapForFinalVlaue.put(finalValue[0], finalValue[1]);
					}
					System.out.println("mapForFinalVlaue     "+ mapForFinalVlaue);
					commonModifyOfPayment(mapForFinalVlaue, approverComment, saveCondition, approverList, userName, format);

				}
			}
		}

	}
	
	
	public void commonModifyOfPayment(Map<String, String> mapForFinalVlaue, String approverComment, String saveCondition,
            String[] approverList, String userName, SimpleDateFormat format){
		double payAmt = Double.valueOf(mapForFinalVlaue.get("payAmt").toString());
		java.sql.Date proDate = null;
		try {
			proDate = new java.sql.Date(format.parse(mapForFinalVlaue.get("proDate")).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long paymentID = Long.valueOf(mapForFinalVlaue.get("payID").toString());				
		Payment payObj = payRepo.getPaymentObjBasedOnID(paymentID).get(0);
		if(!saveCondition.equalsIgnoreCase("draft")){
			if(approverList != null){
				payObj = paymentApproverCreation(approverList, payObj);
			}					
		}
		payObj.setPaymentAmount(payAmt);
		payObj.setPaymentProcessDate(proDate);
		payObj.setRemarks(approverComment);
		payObj.setUpdatedBy((int)sessionUserId);
		payObj.setUpdatedDate(new Date());
		payRepo.savePayment(payObj);
		AuditLogTrail alt = new AuditLogTrail("PaymentDetail","Update", "Update Payment", "ALL", "ALL", userName, paymentID);
		payRepo.saveAuditTrail(alt);
	}
	
	public Payment paymentApproverCreation(String[] approverList, Payment payObj) {
		if (approverList != null) {
			int i = 0;
			Map<String, String> mapForFinalVlaue = new HashMap();
			System.out.println("dataRequestList       " + approverList);
			System.out.println("dataRequestList  size     "+ approverList.length);
			for (String appList : approverList) {
				PaymentApprover appVendorObj = new PaymentApprover();
				i++;
				String[] firstSplit = appList.split(",");
				for (String demo2 : firstSplit) {
					demo2 = demo2.replaceAll("\"", "").replace("{", "").replace("}", "").intern();
					String[] finalValue = demo2.split(":");
					mapForFinalVlaue.put(finalValue[0], finalValue[1]);
				}
				System.out.println("mapForFinalVlaue      "+mapForFinalVlaue +"   gggggggggggggg    "+i);
				if (i == 1) {
					appVendorObj.setPaymentApprovalAccess(1);
				}
				long userID = Long.valueOf(mapForFinalVlaue.get("userId"));
				appVendorObj.setAppUserID(userID);
				appVendorObj.setAppCreatedBy((int) sessionUserId);
				appVendorObj.setAppCreatedDate(new Date());
				payObj.getPayApprover().add(appVendorObj);
			}

		}
		return payObj;
	}
	
	@Transactional
	public List<Payment> fetchPaymentReleaseList() {
		List<Payment> payList = payRepo.getPaymentReleaseList(orgID);
		return payList;
	}
	
	@Transactional
	public List<?> fetchAccountForPaymentRelease(){
		List<?> payList = payRepo.fetchAccountListForPaymentRelBasedOnORG(orgID);
		return payList;
	}
	
	@Transactional
	public List<Payment> fetchQueryDataToSearch(String fromDate, String toDate,
			String searchName, int approvalStatus, double fromAmt, double toAmt, String searchCondition) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date fromDateAfterConversion = null;
		java.sql.Date toDateAfterConversion = null;
		try {
			if (fromDate != null && fromDate != "") {
				fromDateAfterConversion = new java.sql.Date(format.parse(fromDate).getTime());
			}
			if (toDate != null && toDate != "") {
				toDateAfterConversion = new java.sql.Date(format.parse(toDate).getTime());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		List<Payment> payList = payRepo.getDataBasedOnSearchCondition(fromDateAfterConversion, toDateAfterConversion, searchName,
				approvalStatus, fromAmt, toAmt, orgID, searchCondition);
		return payList;
	}
	
	@Transactional
	public List<Payment> fetchPaymentHistoryList() {
		List<Payment> payList = payRepo.getPaymentHistoryList(orgID);
		return payList;
	}
	
	@Transactional
	public void doUpdatePaymentForRelease(String[] payRelease, String userName){
		if(payRelease != null){
			for (String payIDValue : payRelease) {
				System.out.println("in the loooooppppppppp          "+payIDValue);
				long payID = Long.valueOf(payIDValue.replace("\"", "").replace("}", "").split(":")[1]);
				System.out.println("payIDforF forFianl relaease         "+payID);
				Payment payObj = payRepo.getPaymentObjBasedOnID(payID).get(0);
				if(payObj != null){
					payObj.setApprovalStatus(6);
					payObj.setUpdatedBy((int)sessionUserId);
					payObj.setUpdatedDate(new Date());					
					
					InvoiceDetails invObj = payObj.getInvoiceDetail();
					invObj.setInvoiceStatus(6);
					invObj.setInvoiceUpdatedBy((int)sessionUserId);
					invObj.setInvoiceUpdatedDate(new Date());
					
					payObj.setInvoiceDetail(invObj);
					payRepo.savePayment(payObj);
					AuditLogTrail alt = new AuditLogTrail("PaymentDetail","Update", "Update Payment", "Approve", "Scheduled", userName,
							payObj.getPaymentId());
					payRepo.saveAuditTrail(alt);
					
					AuditLogTrail altInvoice = new AuditLogTrail("InvoiceDetail","Update", "Update Invoice", "Approve", "Scheduled",
							       userName, invObj.getInvoiceId());
					payRepo.saveAuditTrail(altInvoice);
				}
				
			}
		}	
		
	}
	
	public List<InvoiceDetails> getInvoiceObjBasedOnInvoiceNo(String invNo){
		List<InvoiceDetails> invList = invoiceRep.getInvoiceObjBasedOnInvoiceNo(invNo.trim(), orgID);
		return invList;
	}
		
	
}
