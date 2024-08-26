/**
 * 
 */
package com.bankpro.app.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankpro.app.dao.InvoiceRepository;
import com.bankpro.app.dao.PaymentRepository;
import com.bankpro.app.dao.VendorInfoRepository;
import com.bankpro.app.dto.InvoiceDTO;
import com.bankpro.app.model.AuditLogTrail;
import com.bankpro.app.model.DocInfoDetail;
import com.bankpro.app.model.InvoiceApprover;
import com.bankpro.app.model.InvoiceDetails;
import com.bankpro.app.model.InvoiceTypeMaster;
import com.bankpro.app.model.OrganizationDetail;
import com.bankpro.app.model.Payment;
import com.bankpro.app.model.Users;
import com.bankpro.app.model.Vendor;

/**
 * @author Lakharwal
 *
 */
@Service
public class InvoiceServices {
	private int startRow = 0;
	private Scanner reader = null;
	private int sessionUserId;
	private int orgID;
	@Autowired
	InvoiceRepository invoiceRep;
	
	@Autowired
	VendorInfoRepository vir;
	
	@Autowired
	PaymentRepository payRepo;

	@Transactional
	public void doCallForCreateInvoice(InvoiceDTO invDTOObj, String userName) {
		if(!invoiceRep.doCheckForDuplicateInvoiceForSameVendor(invDTOObj.getBeneficiaryID(), invDTOObj.getInvoiceNumber())){
			throw new IllegalArgumentException("Invoice No for the same Beneficiary can't be duplicate");
		}
		String beneName =null; 
		InvoiceDetails invDetail = new InvoiceDetails();
		List<?> venArray = invoiceRep.doGetVendorSupplierNameBasedOnVenId(Long.valueOf(invDTOObj.getBeneficiaryID()));
		if(venArray != null){
			 beneName = (String)venArray.get(0);			
		}
	     invDetail = doCommonInvoiceAction(invDetail, invDTOObj, beneName);
	     invDetail.setInvoiceOrgId(orgID);
	     invDetail.setInvoiceCreatedBy((int)sessionUserId);
	     invDetail.setInvoiceCreatedDate(new Date());
	     invDetail.setInvoicevenId(Long.valueOf(invDTOObj.getBeneficiaryID()));;
	     invDetail.setInvoiceBeneficiaryName(beneName);
	     if(invDTOObj.getSelectedIncoiveApproverList().isEmpty()){
	    	 invDetail.setInvoiceStatus(5);
	    	 invDetail.setInvoiceApprovalStatus(5);
	     }
		InvoiceDetails invID = invoiceRep.saveInvoice(invDetail);
		if(!invDTOObj.getSelectedIncoiveApproverList().isEmpty()){
			doCreateInvoiceApproval(invDTOObj.getSelectedIncoiveApproverList(), invID.getInvoiceId(), sessionUserId);
		}
		doSaveFilesOfManuallyAdded(invDTOObj, invID.getInvoiceId());
		AuditLogTrail alt = new AuditLogTrail("InvoiceDetail", "Created", "ALL","ALL", "ALL", userName, invID.getInvoiceId());		 		 
		invoiceRep.saveAuditTrail(alt);

	}
	
	@Transactional
	public void doSaveFilesOfManuallyAdded(InvoiceDTO vendDTO, long refID) {
		if (vendDTO.getNewAddedDocListForInvoice() != null) {
			for (int i = 0; i < vendDTO.getNewAddedDocListForInvoice().size(); i++) {
				DocInfoDetail newObj = new DocInfoDetail();
				LinkedHashMap<?, ?> DocMap = (LinkedHashMap<?, ?>) vendDTO.getNewAddedDocListForInvoice().get(i);
				newObj.setDocManuallyGivenFileName(DocMap.get("manuallyGivenFileName").toString());
				newObj.setDocFileName(DocMap.get("fileName").toString());
				newObj.setDocReferenceId((int) refID);
				newObj.setDocReferenceType("invoiceDetail");
				invoiceRep.saveDocInfo(newObj);
			}
		}

	}
	
	@Transactional
	public void doUpdateInvoice(InvoiceDTO invDTOObj, String userName) {
		List<InvoiceDetails> invList = invoiceRep.getInvoiceObjBasedOnID(invDTOObj.getInvoiceID());
		if (invList != null) {
			String beneName =null; 
			List<?> venArray = invoiceRep.doGetVendorSupplierNameBasedOnVenId(Long.valueOf(invDTOObj.getBeneficiaryID()));
			if(venArray != null){
				 beneName = (String)venArray.get(0);			
			}
			InvoiceDetails invObj = invList.get(0);
			 invObj = doCommonInvoiceAction(invObj, invDTOObj, beneName);
			 invObj.setInvoiceUpdatedDate(new Date());
			 invObj.setInvoiceUpdatedBy(sessionUserId);
			 if(!invDTOObj.getSelectedIncoiveApproverList().isEmpty()){
				 invObj.setInvoiceStatus(0);
				 invObj.setInvoiceApprovalStatus(0);
		     }
			invoiceRep.saveInvoice(invObj);
			if(!invDTOObj.getSelectedIncoiveApproverList().isEmpty()){
				invoiceRep.doDeleteAlreadySavedInvoiceApprover(invDTOObj.getInvoiceID());
				doCreateInvoiceApproval(invDTOObj.getSelectedIncoiveApproverList(), invDTOObj.getInvoiceID(), sessionUserId);
			}
			AuditLogTrail alt = new AuditLogTrail("InvoiceDetail", "Update", "ALL","ALL", "ALL", userName, invDTOObj.getInvoiceID());		 		 
			invoiceRep.saveAuditTrail(alt);
			}
	}
	
	public InvoiceDetails doCommonInvoiceAction(InvoiceDetails invObj, InvoiceDTO invDTOObj, String benName){
		invObj.setInvoiceBeneficiaryName(benName);
		invObj.setInvoiceType(invDTOObj.getInvoiceType());
		invObj.setInvoiceNumber(invDTOObj.getInvoiceNumber());
		invObj.setInvoiceDate(invDTOObj.getInvoiceDate());
		invObj.setInvoicePONumber(invDTOObj.getPoNumber());
		invObj.setInvoicePaymentTerm(invDTOObj.getPaymentTerm());
		invObj.setInvoiceDueDate(invDTOObj.getDueDate());
		invObj.setInvoiceAmount(invDTOObj.getNetInvoiceAmount());
		invObj.setInvoiceBasicAmount(invDTOObj.getAmount());   //basic amount
		invObj.setInvoiceDescription(invDTOObj.getDescription());
		invObj.setInvoiceTaxPrcnt(invDTOObj.getTaxPercent());
		invObj.setInvoiceTaxAmt(invDTOObj.getTaxAmount());
		invObj.setInvoiceTDSDeductionPrcnt(invDTOObj.getTdsDeductionPrecnt());
		invObj.setInvoiceTDSDeductionAmt(invDTOObj.getTdsDeductionAmt());
		invObj.setInvoiceOtherDeduction(invDTOObj.getOtherDeductionAmt());
		invObj.setInvoiceApproverComment(invDTOObj.getApproverNote());
		return invObj;
		
	}
	
	

	@Transactional
	public List<InvoiceDetails> doGetValueForInvoice(String userName) {
		Object[] row = invoiceRep.fetchInvoiceApprovalPendingList(userName);
		sessionUserId = Integer.valueOf(row[0].toString());
		orgID = ((OrganizationDetail)row[1]).getOrgID();
		List<InvoiceDetails> li = invoiceRep.createQueryForInvoice(orgID);
		System.out.println("size of the list is          " + li.size());

		return li;
	}
	
	@Transactional
	public void batchFileUploadOfCSV(File fullFileName, String userName) {
		try {
			System.out.println("file name final to upload      "+fullFileName);
			FileInputStream fis = new FileInputStream(fullFileName);
//			List<Users> sessionUserList= invoiceRep.getUserObejectBasedOnUserName(userName);
//			Users sessionUserObj = sessionUserList.get(0);
//			int userID = sessionUserObj.getUserId();
//			OrganizationDetail  orgObj = sessionUserObj.getUserOrganization();
//			int orgId = orgObj.getOrgID();
			reader = new Scanner(fis);
			int rowCount = countRows(fullFileName);
			System.out.println(" no. of row isw      " + rowCount);
			for (int i = 1; i <= rowCount; i++) {
				String[] finalArrayToPersist = buildFileReaderArray();
				if(finalArrayToPersist!= null){
					if(finalArrayToPersist[1] != "END"){
						doCreateNewInvoice(finalArrayToPersist,userName, sessionUserId, orgID);
						
						
					}else{
						System.out.println("in the end of the file now we have nothing to read");
					}
					
				}
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

	public String[] buildFileReaderArray() {
		String[] record = new String[30];
		if (reader.hasNextLine()) {
			if (startRow == 0) {
				String firstRow = reader.nextLine();
				System.out.println("in the first row     "+firstRow);
				startRow = 1;
				record = new String[0];
				return null;

			} else {
				record = reader.nextLine().split(",");
				 for (int i = 0; i < record.length; i++) {
				 System.out.println("record             "+record[i]);
				 }

				// we have to put further validation here as we return only
				// valid data from here to persist.
			}
		} else {
			record[1] = "END";
			return record;
		}
		return record;
	}
	
	@Transactional
	public void doCreateNewInvoice(String[] invArray, String userName,int userId, int orgId) {
		SimpleDateFormat ss= new SimpleDateFormat("dd/MM/yyyy");
		InvoiceDetails invObj = new InvoiceDetails();
		invObj.setInvoiceBeneficiaryName(invArray[0].trim());
		invObj.setInvoiceType(invArray[1].trim());
		invObj.setInvoiceNumber(invArray[2].trim());
		try {
			invObj.setInvoiceDate(ss.parse(invArray[3].toString().trim()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		invObj.setInvoicePONumber(invArray[7].trim());
		invObj.setInvoicePaymentTerm(invArray[6].trim());
		try {
			invObj.setInvoiceDueDate(ss.parse(invArray[4].toString().trim()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		invObj.setInvoiceAmount(Double.valueOf(invArray[5].toString().trim()));
		invObj.setInvoiceDescription(invArray[8].trim());
		invObj.setInvoiceTaxPrcnt(Float.valueOf(invArray[9].toString().trim()));
		invObj.setInvoiceTaxAmt(Double.valueOf(invArray[10].toString().trim()));
		invObj.setInvoiceTDSDeductionPrcnt(Float.valueOf(invArray[12].toString().trim()));
		invObj.setInvoiceTDSDeductionAmt(Double.valueOf(invArray[13].toString().trim()));
		invObj.setInvoiceOtherDeduction(Double.valueOf(invArray[14].toString().trim()));
		invObj.setInvoiceApproverComment(invArray[17].trim());	
		invObj.setInvoiceOrgId(orgId);
		invObj.setInvoiceCreatedBy(userId);
		invObj.setInvoiceCreatedDate(new Date());
		InvoiceDetails invId = invoiceRep.saveInvoice(invObj);
		AuditLogTrail alt = new AuditLogTrail("InvoiceDetail", "Create",
				"BatchUpload", "All", "All", userName, invId.getInvoiceId());
		invoiceRep.saveAuditTrail(alt);		
		
	}
	
	@Transactional
	public List<?> doGetValueOfBeneficiaryFromVendor(String userName) {
		List<?> li = invoiceRep.queryToFetchBeneficiaryFromVendor(orgID);
		System.out.println("size of the list is          " + li.size());
		return li;
	}
	
	@Transactional
	public List<?> doGetValueOfApproverForInvoice(String userName) {
		List<?> li = invoiceRep.queryToFetchApproverNameForInvoice(orgID);
		return li;
	}
	
	@Transactional
	public Map<String,Object[]> doGetValueOfApprovalPendingList(String userName) {
		Map<String, Object[]> finalMap = new java.util.HashMap<String, Object[]>();
		Object[] row = invoiceRep.fetchInvoiceApprovalPendingList(userName);
		System.out.println("3333333333333333333333    " + row);
		long sessionUserID = Long.valueOf(row[0].toString());
		List<String> listForVendorInvoiceWise = new ArrayList<>();
		List<?> invID = invoiceRep.getPendingListOfApproveInvoice(sessionUserID);
		System.out.println("invID                    fffffffff   "+invID);
		if (invID != null && !invID.isEmpty()) {
			List<String> dubList = new ArrayList<>();
			for (int i = 0; i < invID.size(); i++) {
				Object[] roww = (Object[]) invID.get(i);
				listForVendorInvoiceWise.add(roww[0].toString());
				dubList.add(roww[0].toString().concat("-").concat(roww[1].toString()));
			}
			System.out.println("dubList                   "+dubList);
			String invIDForQueryeee = listForVendorInvoiceWise.toString().replace("[", "").replace("]", "");
			List<?> vendObj = invoiceRep.getVendorBasedOnMultipleVendorId(invIDForQueryeee);
			Map<String,Object[]> mapForVendorWithIDWise = new java.util.HashMap();
			if(vendObj != null){
				for (Object obj : vendObj) {
					Object[] row2 = (Object[])obj;
					mapForVendorWithIDWise.put(row2[0].toString(), row2);
				}
				System.out.println("mapToMaintainVendorInvoiceWise        "+listForVendorInvoiceWise);
				System.out.println("mapForVendorWithIDWise                 "+mapForVendorWithIDWise);
				for (String object : dubList) {
					String[] id= object.split("-");
					if(mapForVendorWithIDWise.containsKey(id[0])){
				    	finalMap.put(id[1],mapForVendorWithIDWise.get(id[0]));
				    }
				}
				System.out.println("fianl list is           "+finalMap);
			}
		}
		System.out.println("rrrrrrrrrrrrr     " + invID);
		return finalMap;

	}
	
	@Transactional
	public List<InvoiceDetails> doGetValueForInvoiceBasisOnID(long invoiId) {
		List<InvoiceDetails> li = invoiceRep.createQueryForInvoiceBasedOnID(invoiId);
		System.out.println("size of the list is          " + li.size());
		return li;
	}
	
	@Transactional
	public void doCreateInvoiceApproval(List<?> approvalList, long invoiceId, int sessionUser) {
		boolean conditionForAccess = false;
		for (int i = 0; i < approvalList.size(); i++) {
			InvoiceApprover appVendorObj = null;
			LinkedHashMap<?, ?> approverName = (LinkedHashMap<?, ?>) approvalList.get(i);
			System.out.println("approverName              " + approverName);
			if (!conditionForAccess) {
				long userID = Long.valueOf(approverName.get("appUserID").toString());
				appVendorObj = new InvoiceApprover(userID, invoiceId, sessionUser, 1);				
				conditionForAccess = true;
			}else{
				long userID = Long.valueOf(approverName.get("appUserID").toString());
				appVendorObj = new InvoiceApprover(userID, invoiceId, sessionUser, 0);
				System.out.println("in the save 222222    "+userID);
			}
			appVendorObj.setAppCreatedDate(new Date());
			invoiceRep.saveInvoiceApprover(appVendorObj);
		}
	}
	
	@Transactional
	public void doUpdateApproveInvoice(String comment, long invId, String userName){
		LinkedHashMap<Long, InvoiceApprover> invApproverMapWithUserID = new LinkedHashMap<>();
		Object[] row = invoiceRep.fetchInvoiceApprovalPendingList(userName);
		long sessionUserID = Long.valueOf(row[0].toString());
		OrganizationDetail orgObj = (OrganizationDetail)row[1];
		List <InvoiceApprover> invAppList = invoiceRep.doGetApproverListForView(invId);
		if(invAppList != null && !invAppList.isEmpty()){
			for (InvoiceApprover invAppObj : invAppList) {
				invApproverMapWithUserID.put(invAppObj.getAppUserID(), invAppObj);
			}
			InvoiceApprover invObj = invApproverMapWithUserID.get(sessionUserID);
			udpateInvoiceApprover(invObj,sessionUserID, comment);			
			invApproverMapWithUserID.remove(sessionUserID);
			if (!invApproverMapWithUserID.isEmpty()) {
				Map.Entry<Long, InvoiceApprover> entry = invApproverMapWithUserID.entrySet().iterator().next();
				long key = entry.getKey();
				InvoiceApprover value = entry.getValue();
				value.setInvoiceApprovalAccess(1);
				invoiceRep.saveAppInvoice(value);
				InvoiceDetails invObjBasedOnId = invoiceRep.createQueryForInvoiceBasedOnID(invId).get(0);
				if(invObjBasedOnId.getInvoiceApprovalStatus() != 0){
					invObjBasedOnId.setInvoiceApprovalStatus(2);
					invoiceRep.saveInvoice(invObjBasedOnId);
				}
			} else{
				InvoiceDetails invObjBasedOnId = invoiceRep.createQueryForInvoiceBasedOnID(invId).get(0);
				invObjBasedOnId.setInvoiceStatus(1);
				invObjBasedOnId.setInvoiceApprovalStatus(1);
				InvoiceDetails invObjj = invoiceRep.saveInvoice(invObjBasedOnId);
				doCreateSuccessfullPaymentAfterAllInvoiceApproval(invObjj, userName, sessionUserID, orgObj.getOrgID());
				
			}			
		}
	}
	
	@Transactional
	public void udpateInvoiceApprover(InvoiceApprover invObj, long sessionUserID, String comment){
		invObj.setAppUpdatedBy((int)sessionUserID);
		invObj.setAppComment(comment);
		invObj.setAppUpdatedDate(new Date());
		invObj.setInvoiceApprovalAccess(2);
		invObj.setAppStatus(1);
		invoiceRep.saveAppInvoice(invObj);
	}
	
	@Transactional
	public void doUpdateDeclineInvoice(String comment, long invId, String userName){
		LinkedHashMap<Long, InvoiceApprover> invApproverMapWithUserID = new LinkedHashMap<>();
		Object[] row = invoiceRep.fetchInvoiceApprovalPendingList(userName);
		long sessionUserID = Long.valueOf(row[0].toString());
		List <InvoiceApprover> invAppList = invoiceRep.doGetApproverListForView(invId);
			for (InvoiceApprover invAppObj : invAppList) {
				invApproverMapWithUserID.put(invAppObj.getAppUserID(), invAppObj);
			}
			InvoiceApprover invObj = invApproverMapWithUserID.get(sessionUserID);
			udpateInvoiceForDecline(invObj,sessionUserID, comment);			
			InvoiceDetails invObjBasedOnId = invoiceRep.createQueryForInvoiceBasedOnID(invId).get(0);
			invObjBasedOnId.setInvoiceApprovalStatus(3);
			invoiceRep.saveInvoice(invObjBasedOnId);
	}
	
	@Transactional
	public void udpateInvoiceForDecline(InvoiceApprover invObj, long sessionUserID, String comment){
		invObj.setAppUpdatedBy((int)sessionUserID);
		invObj.setAppComment(comment);
		invObj.setAppUpdatedDate(new Date());
		invObj.setInvoiceApprovalAccess(2);
		invObj.setAppStatus(2);
		invoiceRep.saveAppInvoice(invObj);
	}
	
	@Transactional
	public List<InvoiceTypeMaster> getInvoiceType() {
		List<InvoiceTypeMaster> invoiceTypeObj = invoiceRep.getInvoiceType();
		return invoiceTypeObj;
	}
	
	@Transactional
	public List<InvoiceApprover> getApproverObjList(long invoiceId) {
		List<InvoiceApprover> invApprObjList = invoiceRep.getApproverObjList(invoiceId);
		return invApprObjList;
	}
	
	@Transactional
	public List<?> userNameBasedOnUserID() {
		List<?> invApprUserList = invoiceRep.userNameBasedOnUserID(orgID);
		return invApprUserList;
	}
	
	
	@Transactional
	public Map<String,Object[]> getApprovalHistoryList(String userName) {
		Map<String, Object[]> finalMap = new java.util.HashMap<String, Object[]>();
		
		List<Users> sessionUserList= invoiceRep.getUserObejectBasedOnUserName(userName);
		Users sessionUserObj = sessionUserList.get(0);
		int  orgId = sessionUserObj.getUserOrganization().getOrgID();
		long userId = sessionUserObj.getUserId();
		
		List<String> listForVendorInvoiceWise = new ArrayList<>();
		List<?> invID = invoiceRep.getApproveInvoiceHistory(userId, orgId);
		
		if (invID != null && !invID.isEmpty()) {
			List<String> dubList = new ArrayList<>();
			for (int i = 0; i < invID.size(); i++) {
				Object[] roww = (Object[]) invID.get(i);
				listForVendorInvoiceWise.add(roww[0].toString());
				dubList.add(roww[0].toString().concat("-").concat(roww[1].toString()));
			}
			System.out.println("dubList                   "+dubList);
			String invIDForQueryeee = listForVendorInvoiceWise.toString().replace("[", "").replace("]", "");
			List<?> vendObj = invoiceRep.getVendorBasedOnMultipleVendorId(invIDForQueryeee);
			Map<String,Object[]> mapForVendorWithIDWise = new java.util.HashMap();
			if(vendObj != null){
				for (Object obj : vendObj) {
					Object[] row2 = (Object[])obj;
					mapForVendorWithIDWise.put(row2[0].toString(), row2);
				}
				System.out.println("mapToMaintainVendorInvoiceWise        "+listForVendorInvoiceWise);
				System.out.println("mapForVendorWithIDWise                 "+mapForVendorWithIDWise);
				for (String object : dubList) {
					String[] id= object.split("-");
					if(mapForVendorWithIDWise.containsKey(id[0])){
				    	finalMap.put(id[1],mapForVendorWithIDWise.get(id[0]));
				    	System.out.println(" mapForVendorWithIDWise = " + mapForVendorWithIDWise.get(id[0]));
				    }
				}
				System.out.println("fianl list is           "+finalMap);
			}
			
		}
		System.out.println("rrrrrrrrrrrrr     " + invID);
		return finalMap;

	}
	
	@Transactional
	public Map<String,Object[]> searchInvoiceList(String startDate,String endDate,String name, long amountFrom,long amountTO, String userName) {
		Map<String, Object[]> finalMap = new java.util.HashMap<String, Object[]>();
		List<Users> sessionUserList= invoiceRep.getUserObejectBasedOnUserName(userName);
		Users sessionUserObj = sessionUserList.get(0);
		int  orgId = sessionUserObj.getUserOrganization().getOrgID();
		long userId = sessionUserObj.getUserId();
		
		
		List<String> listForVendorInvoiceWise = new ArrayList<>();
		List<?> invID = invoiceRep.searchInvoiceList(startDate,endDate,name,amountFrom,amountTO,orgId);
		
		if (invID != null && !invID.isEmpty()) {
			List<String> dubList = new ArrayList<>();
			for (int i = 0; i < invID.size(); i++) {
				Object[] roww = (Object[]) invID.get(i);
				listForVendorInvoiceWise.add(roww[0].toString());
				dubList.add(roww[0].toString().concat("-").concat(roww[1].toString()));
			}
			System.out.println("dubList "+dubList);
			String invIDForQueryeee = listForVendorInvoiceWise.toString().replace("[", "").replace("]", "");
			List<?> vendObj = invoiceRep.getVendorBasedOnMultipleVendorId(invIDForQueryeee);
			Map<String,Object[]> mapForVendorWithIDWise = new java.util.HashMap();
			if(vendObj != null){
				for (Object obj : vendObj) {
					Object[] row2 = (Object[])obj;
					mapForVendorWithIDWise.put(row2[0].toString(), row2);
				}
				for (String object : dubList) {
					String[] id= object.split("-");
					if(mapForVendorWithIDWise.containsKey(id[0])){
				    	finalMap.put(id[1],mapForVendorWithIDWise.get(id[0]));
				    	System.out.println(" mapForVendorWithIDWise = " + mapForVendorWithIDWise.get(id[0]));
				    }
				}
				System.out.println("fianl list is "+finalMap);
			}
			
		}
		
		return finalMap;
		
	}
	
	@Transactional
	public void doCreateSuccessfullPaymentAfterAllInvoiceApproval(InvoiceDetails invObj, String userName, long sessionUserID, int orgID){
		List<Vendor> vendList = vir.getVendorObjBasedOnIDToUpdate(invObj.getInvoicevenId());
		Payment payObj = new Payment();
		if(vendList != null && !vendList.isEmpty()) {
			Vendor venObj = vendList.get(0);
			payObj.setVendorDetail(venObj);
			payObj.setPaymentAmount(invObj.getInvoiceAmount());
			payObj.setPaymentProcessDate(new Date());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			payObj.setPaymentDate(cal.getTime());
			payObj.setPayOrgId(orgID);
			payObj.setCreatedBy((int)sessionUserID);
			payObj.setCreatedDate(new Date());
			payObj.setInvoiceDetail(invObj);
			long paymentId = payRepo.savePayment(payObj);
			AuditLogTrail alt = new AuditLogTrail("PaymentDetail", "Create", "Payment Create","New Payment", "New Payment", userName, paymentId);				
			payRepo.saveAuditTrail(alt);
			
		}
		
		
		
	}

	@Transactional
	public Map<String,Object[]> searchInvoiceApprovalHistoryList(String startDate,String endDate,String name, long amountFrom,long amountTO, String userName) {
		Map<String, Object[]> finalMap = new java.util.HashMap<String, Object[]>();
		List<Users> sessionUserList= invoiceRep.getUserObejectBasedOnUserName(userName);
		Users sessionUserObj = sessionUserList.get(0);
		int  orgId = sessionUserObj.getUserOrganization().getOrgID();
		long userId = sessionUserObj.getUserId();
		
		List<String> listForVendorInvoiceWise = new ArrayList<>();
		List<?> invID = invoiceRep.searchInvoiceApprovalHistoryList(startDate,endDate,name,amountFrom,amountTO,orgId);
		
		if (invID != null && !invID.isEmpty()) {
			List<String> dubList = new ArrayList<>();
			for (int i = 0; i < invID.size(); i++) {
				Object[] roww = (Object[]) invID.get(i);
				listForVendorInvoiceWise.add(roww[0].toString());
				dubList.add(roww[0].toString().concat("-").concat(roww[1].toString()));
			}
			System.out.println("dubList "+dubList);
			String invIDForQueryeee = listForVendorInvoiceWise.toString().replace("[", "").replace("]", "");
			List<?> vendObj = invoiceRep.getVendorBasedOnMultipleVendorId(invIDForQueryeee);
			Map<String,Object[]> mapForVendorWithIDWise = new java.util.HashMap();
			if(vendObj != null){
				for (Object obj : vendObj) {
					Object[] row2 = (Object[])obj;
					mapForVendorWithIDWise.put(row2[0].toString(), row2);
				}
				for (String object : dubList) {
					String[] id= object.split("-");
					if(mapForVendorWithIDWise.containsKey(id[0])){
				    	finalMap.put(id[1],mapForVendorWithIDWise.get(id[0]));
				    	System.out.println(" mapForVendorWithIDWise = " + mapForVendorWithIDWise.get(id[0]));
				    }
				}
				System.out.println("fianl list is "+finalMap);
			}
			
		}
		
		return finalMap;
		
	}
	
	@Transactional
	public List<InvoiceDetails> getSearchDataForInvoice(String dateFrom, String dateTo, String searchName, String amountFrom,
			String amountTo, int invoiceStatusField, int approvalStatusField,	int approvalStatus, String userName) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date fromDateAfterConversion = null;
		java.sql.Date toDateAfterConversion = null;
		try {
			if (dateFrom != null && dateFrom != "") {
				fromDateAfterConversion = new java.sql.Date(format.parse(dateFrom).getTime());
			}
			if (dateTo != null && dateTo != "") {
				toDateAfterConversion = new java.sql.Date(format.parse(dateTo).getTime());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		List<InvoiceDetails> searchedInvoiceList = invoiceRep.getDataBasedOnSearchCondition(fromDateAfterConversion, toDateAfterConversion, searchName, amountFrom,
			 amountTo, invoiceStatusField, approvalStatusField, approvalStatus, orgID);
		return searchedInvoiceList;
	}
	
	@Transactional
	public Integer doUpdateCancelInvoice(long invoiceID, String userName){
		LinkedHashMap<Long, InvoiceApprover> invApproverMapWithUserID = new LinkedHashMap<>();
		System.out.println("invoiceID      "+invoiceID);
		List<?> payList = invoiceRep.doGetPaymentBasedOnInvoiceID(invoiceID);
		if(payList != null && !payList.isEmpty()){
			if(payList.size() == 1){
				int approveStatusValue = Integer.valueOf(payList.get(0).toString());
				return approveStatusValue;
			}else{
				throw new IllegalArgumentException("More than one payment found for this selected invoice");
			}
			
		}
		return 1;
		
	}
	
	@Transactional
	public void doUpdateCancelInvoiceAfterCheck(long invoiceID, String userName) {
		invoiceRep.doUpdateInvoiceForInvoiceStatus(invoiceID,(int) sessionUserId);
		AuditLogTrail alt = new AuditLogTrail("InvoiceDetail", "Update", "Update Invoice", "Pending", "Cancelled", userName, invoiceID);
		invoiceRep.saveAuditTrail(alt);
		invoiceRep.udpateInvoiceApproverWhileCancel(invoiceID, 4, "",(int) sessionUserId);

	}
	

}
