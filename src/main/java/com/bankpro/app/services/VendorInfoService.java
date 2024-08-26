/**
 * 
 */
package com.bankpro.app.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankpro.app.dao.InvoiceRepository;
import com.bankpro.app.dao.VendorInfoRepository;
import com.bankpro.app.dto.VendorInfoDTO;
import com.bankpro.app.model.ApproveVendor;
import com.bankpro.app.model.AuditLogTrail;
import com.bankpro.app.model.CompanyTypeMaster;
import com.bankpro.app.model.DocInfoDetail;
import com.bankpro.app.model.NewBankDetails;
import com.bankpro.app.model.OrganizationDetail;
import com.bankpro.app.model.PaymentMethodMaster;
import com.bankpro.app.model.StateMaster;
import com.bankpro.app.model.TermMaster;
import com.bankpro.app.model.Users;
import com.bankpro.app.model.Vendor;
import com.bankpro.app.model.VendorTypeMaster;

/**
 * @author Admin
 *
 */
@Service
public class VendorInfoService {
	int startRow = 0;
	Scanner reader = null;
	private int sessionUserId;
	private int orgID;
	
	@Autowired
	VendorInfoRepository vir;
	
	@Autowired
	InvoiceRepository invoiceRep;

	@Transactional
	public void createNewVendor(VendorInfoDTO vid, String userName) {
		Object[] row = vir.fetchUserIdAndOrgObjBasedOnUserName(userName);
		OrganizationDetail orgObj = ((OrganizationDetail)row[1]);		
		int  orgId = orgObj.getOrgID();
		String orgName = orgObj.getOrgName();
		Vendor vendObj = new Vendor();
	    vendObj = commonSaveForVendor(vendObj, vid);
	    vendObj.setVendorOrgID(orgId);
	    vendObj.setCreatedBy(sessionUserId);
	    vendObj.setCreatedDate(new Date());
	    
		
		Users userObj = new Users();
		userObj.setUserFirstName(vid.getFirstName());
		userObj.setUserLastName(vid.getLastName());
		userObj.setUserEmail(vid.getEmail());

		NewBankDetails bankObj = new NewBankDetails();
		bankObj.setBankName(vid.getBankName());
		bankObj.setBankBranchAddress(vid.getBranchAddress());
		bankObj.setBankAccountNo(vid.getAccountNo());
		bankObj.setBankAccountHolderName(vid.getAccName());
		bankObj.setBankIFSCCode(vid.getIfscCode());
		bankObj.setBankCreatedBy(sessionUserId);
		bankObj.setBankCreatedDate(new Date());

		vendObj.setUserId(userObj);
		vendObj.setNewBankID(bankObj);
		Vendor vendorObj = vir.saveVendorObj(vendObj);
		
		// Update Suplier Number with concatenation of firdt 3 charecter of orgName and VendorId
		String sortOrgName = "";
		if(orgName.length() > 3) {
			sortOrgName = orgName.substring(0, 3);
		}
		String suplierNum = sortOrgName+vendorObj.getId();
		vendorObj.setId(vendorObj.getId());
		vendorObj.setSupplierNumber(suplierNum);
				
		vir.saveVendorObj(vendorObj);
		
		// going to maintain the trail of updation
		AuditLogTrail alt = new AuditLogTrail("VendorDetail", "Create", "Vendor Create","New Vendor", "New Vendor", userName, vendorObj.getId());				
		vir.saveAuditTrail(alt);
		if(vid.getNewAddedDocListInVendor() != null && !vid.getNewAddedDocListInVendor().isEmpty()){
		   doSaveFilesOfManuallyAdded(vid, vendorObj.getId());
		}

	}
	
	@Transactional
	public List<Vendor> doVendorBasedOnId(long vendID) {
		List<Vendor> vendList = vir.getVendorObjBasedOnIDToUpdate(vendID);
		return vendList;
	}
	

	@Transactional
	public void doUpdateForVendor(VendorInfoDTO venDTO, String userName) {
		List<Vendor> vendList = vir.getVendorObjBasedOnIDToUpdate(venDTO.getVendID());
		if (vendList != null) {
			Vendor vendObj = vendList.get(0);
			 vendObj = commonSaveForVendor(vendObj, venDTO);	
			 vendObj.setUpdateDate(new Date());	
			 vendObj.setUpdatedBy(sessionUserId);

			// bank informationn udpate
			NewBankDetails bankObj = vendObj.getNewBankID();
			bankObj.setBankName(venDTO.getBankName());
			bankObj.setBankBranchAddress(venDTO.getBranchAddress());
			bankObj.setBankAccountNo(venDTO.getAccountNo());
			bankObj.setBankAccountHolderName(venDTO.getAccName());
			bankObj.setBankIFSCCode(venDTO.getIfscCode());
			bankObj.setBankUpdateDate(new Date());
			bankObj.setBankUpdatedBy(sessionUserId);

			// user info udpate
			Users userObj = vendObj.getUserId();
			userObj.setUserFirstName(venDTO.getFirstName());
			userObj.setUserLastName(venDTO.getLastName());
			userObj.setUserUpdatedDate(new Date());
			userObj.setUserUpdatedBy(sessionUserId);
			
			// save all object in vendor detail and then finally save vendor obj
			vendObj.setUserId(userObj);
			vendObj.setNewBankID(bankObj);
			long vendID = vir.saveVendor(vendObj);
			
			// goiing to maintain the trail of updation
			AuditLogTrail alt = new AuditLogTrail("VendorDetail", "Updation", "ALL","ALL", "ALL", userName, vendID);
			
			vir.saveAuditTrail(alt);
			if(venDTO.getNewAddedDocListInVendor() != null && !venDTO.getNewAddedDocListInVendor().isEmpty()){
				doSaveFilesOfManuallyAdded(venDTO, vendID);
			}			

		}

	}

	public Vendor commonSaveForVendor(Vendor vendObj, VendorInfoDTO venDTO) {
		vendObj.setVenBenCategorizeValue(1);  // 1 flag for identification that current line is related to vendor.
		vendObj.setSupplierName(venDTO.getSuplierName());
		vendObj.setSupplierNumber(venDTO.getSuplierNo());
		vendObj.setPan_number(venDTO.getPanNumber());
		vendObj.setVendorType(venDTO.getVendorType());
		vendObj.setCompanyType(venDTO.getCompanyType());
		vendObj.setPaymentMethod(venDTO.getPaymentMethod());
		vendObj.setPaymentTerms(venDTO.getPaymentTerm());
		vendObj.setCurrency(venDTO.getCurrency());
		vendObj.setMID(venDTO.getmID());
		vendObj.setCitiName(venDTO.getCitiName());
		vendObj.setPhoneNo(venDTO.getPhnNo());
		vendObj.setPaymentName(venDTO.getPaymentName());
		vendObj.setPrimaryEmailId(venDTO.getPrimEmailId());
		vendObj.setPinCode(venDTO.getPinCode());
		vendObj.setFaxNo(venDTO.getFaxNumber());
		vendObj.setAddressLine1(venDTO.getAddressLine1());
		vendObj.setAddressLine2(venDTO.getAddressLine2());
		vendObj.setAddressLine3(venDTO.getAddressLine3());
		vendObj.setCountryName(venDTO.getCountryID());
		vendObj.setStateId(venDTO.getStateId());
		System.out.println("venDTO.getAddressProof()    "+venDTO.getAddressProof());
		if (venDTO.getPanCardDoc() != null && venDTO.getPanCardDoc() != "") {
			vendObj.setPanCardDoc(venDTO.getPanCardDoc());
		}
		if (venDTO.getAddressProof() != null && venDTO.getAddressProof() != "") {
			vendObj.setAddressProofDoc(venDTO.getAddressProof());
		}
		if (venDTO.getCancelCheque() != null && venDTO.getCancelCheque() != "") {
			vendObj.setCencelChequeDoc(venDTO.getCancelCheque());
		}
		return vendObj;
	}

	@Transactional
	public List<Vendor> getValueForVendor(String userName) {
		Object[] row = vir.fetchUserIdAndOrgObjBasedOnUserName(userName);
		sessionUserId = Integer.valueOf(row[0].toString());
		orgID = ((OrganizationDetail)row[1]).getOrgID();
		List<Vendor> vendorObj = vir.getVendorList(orgID, 1); // 1 is pass to identify that current request is for fetch the vendor list 
		return vendorObj;
	}
	
	@Transactional
	public void doSaveFilesOfManuallyAdded(VendorInfoDTO vendDTO, long refID) {
		for (int i = 0; i < vendDTO.getNewAddedDocListInVendor().size(); i++) {
			DocInfoDetail newObj = new DocInfoDetail();
			LinkedHashMap<?, ?> DocMap = (LinkedHashMap<?, ?>) vendDTO.getNewAddedDocListInVendor().get(i);
			if (DocMap.get("manuallyGivenFileName") != null) {
				newObj.setDocManuallyGivenFileName(DocMap.get("manuallyGivenFileName").toString());
			}
			newObj.setDocFileName(DocMap.get("fileName").toString());
			newObj.setDocReferenceId((int) refID);
			newObj.setDocReferenceType("vendorDetail");
			vir.saveDocInfo(newObj);
		}

	}
	
	@Transactional
	public void deleteFileAndUpdateDocValueOfVendor(int rowID, String columnName, String userName){
		System.out.println("bank id   fff "+rowID);
		AuditLogTrail alt = new AuditLogTrail("VendorDetail", "Update",
				columnName, "All", "All", userName, Long.valueOf(rowID));
		vir.deleteDocInfoFromColumnOfVendor(rowID, columnName);
		vir.saveAuditTrail(alt);
	}
	
	@Transactional
	public void inactiveSelectedVendor(long vendId, String userName){
		System.out.println("bank id   fff "+vendId);
		AuditLogTrail alt = new AuditLogTrail("VendorDetail", "Update",
				"vendor status", "Active", "Inactive", userName, vendId);
		vir.changeVendorStatus(vendId);
		vir.saveAuditTrail(alt);
	}
	
	@Transactional
	public void batchFileUploadOfCSV(File fullFileName, String userName) {
		try {
			FileInputStream fis = new FileInputStream(fullFileName);
			List<Users> sessionUserList= vir.getUserObejectBasedOnUserName(userName);
			Users sessionUserObj = sessionUserList.get(0);
			int userID = sessionUserObj.getUserId();
			OrganizationDetail  orgObj = sessionUserObj.getUserOrganization();
			reader = new Scanner(fis);
			int rowCount = countRows(fullFileName);
			System.out.println(" no. of row isw      " + rowCount);
			for (int i = 1; i <= rowCount; i++) {
				String[] finalArrayToPersist = buildFileReaderArray();
				if(finalArrayToPersist!= null){
					if(finalArrayToPersist[1] != "END"){
						doCreateNewVendor(finalArrayToPersist,userName, userID, orgObj);
						
						
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
				startRow = 1;
				record = new String[0];
				return null;

			} else {
				String row = reader.nextLine();
				record = row.split(",");
//				for (int i = 0; i < record.length; i++) {
//					System.out.println("record             "+record[i]);
//				}
				
				// we have to put further validation here as we return only valid data from here to persist.
			}
		} else {
			record[1] = "END";
			return record;
		}
		return record;
	}
	
	@Transactional
	public void doCreateNewVendor(String[] vedorArray, String userName, int userId, OrganizationDetail orgObj){
		Vendor vendObj = new Vendor();
		vendObj.setSupplierName(vedorArray[0]);
		vendObj.setSupplierNumber(vedorArray[1]);
		vendObj.setPaymentName(vedorArray[2]);
		vendObj.setPan_number(vedorArray[3]);
		
		vendObj.setVendorType(vedorArray[5]);
		vendObj.setCompanyType(vedorArray[6]);
		vendObj.setPaymentMethod(vedorArray[7]);
		vendObj.setPaymentTerms(vedorArray[8]);
		vendObj.setMID(vedorArray[14]);
		vendObj.setCountryName(vedorArray[15]);
		vendObj.setCitiName(vedorArray[16]);
		vendObj.setCreatedDate(new Date());
		vendObj.setAddressLine1(vedorArray[22]);
		vendObj.setAddressLine2(vedorArray[23]);
		vendObj.setAddressLine3(vedorArray[24]);
		vendObj.setPinCode(vedorArray[25]);
		vendObj.setPhoneNo(vedorArray[17]);	
		vendObj.setVendorOrgID(orgObj.getOrgID());
		vendObj.setCreatedBy(userId);	
		
		
		NewBankDetails bankObj = new NewBankDetails();
		bankObj.setBankName(vedorArray[9]);
		bankObj.setBankBranchAddress(vedorArray[10]);
		bankObj.setBankAccountHolderName(vedorArray[11]);
		bankObj.setBankAccountNo(vedorArray[12]);		
		bankObj.setBankIFSCCode(vedorArray[13]);		
		bankObj.setBankCreatedDate(new Date());
		bankObj.setBankCreatedBy(userId);
		
		Users userObj = new Users();
		userObj.setUserContectNo(vedorArray[17]);
		userObj.setUserFirstName(vedorArray[18]);
		userObj.setUserLastName(vedorArray[19]);		
		userObj.setUserEmail(vedorArray[20]);
		userObj.setUserOrganization(orgObj);
		userObj.setUserCreatedDate(new Date());
		userObj.setUserCreatedBy(userId);
		userObj.setUserStatus("InActive");
		userObj.setUserAddress(vedorArray[21]);
		
		vendObj.setNewBankID(bankObj);
		vendObj.setUserId(userObj);		
		long vendID = vir.saveVendor(vendObj);
		
		AuditLogTrail alt = new AuditLogTrail("VendorDetail", "Create",
				"BatchUpload", "All", "All", userName, vendID);
		vir.saveAuditTrail(alt);
		
	}
	
	@Transactional
	public List<VendorTypeMaster> getVendorType() {
		List<VendorTypeMaster> vendorTypeObj = vir.getVendorTypeList();
		return vendorTypeObj;
	}
	
	@Transactional
	public List<CompanyTypeMaster> getCompanyType() {
		List<CompanyTypeMaster> companyTypeObj = vir.getCompanyTypeList();
		return companyTypeObj;
	}
	
	@Transactional
	public List<StateMaster> getStateList(int countryID) {
		List<StateMaster> stateList = vir.getStateList(countryID);
		return stateList;
	}
	
	@Transactional
	public List<TermMaster> getPaymentTermList() {
		List<TermMaster> termList = vir.getPaymentTermList();
		return termList;
	}
	
	@Transactional
	public List<PaymentMethodMaster> getPaymentMethodList() {
		List<PaymentMethodMaster> payMethodList = vir.getPaymentMethodList();
		return payMethodList;
	}
	
	@Transactional
	public List<Vendor> getVendorListForApproval(String userName) {
		List<Users> sessionUserList= vir.getUserObejectBasedOnUserName(userName);
		Users sessionUserObj = sessionUserList.get(0);
		int  orgId = sessionUserObj.getUserOrganization().getOrgID();
		System.out.println("In VendorInfoService : orgId = " + orgId);
		List<Vendor> vendorObj = vir.getVendorListForApproval(orgId);
		return vendorObj;
	}
	
	@Transactional
	public void approveVendor(long vendorId, String comment, String userName) {
		AuditLogTrail auditLog = new AuditLogTrail("VendorDetail", "Approve",
				"permissionFlag", "Pending", "Approve", userName, vendorId);
		vir.approveVendor(vendorId);
		vir.saveAuditTrail(auditLog);
		
		List<Users> sessionUserList= vir.getUserObejectBasedOnUserName(userName);
		Users sessionUserObj = sessionUserList.get(0);
		int userId = sessionUserObj.getUserId();
				
		ApproveVendor appVendor = new ApproveVendor();
		Vendor vndr = vir.getVendorObjBasedOnIDToUpdate(vendorId).get(0);
		
		//vndr.setId(vendorId);
		System.out.println("Vendor Id = " + vndr.getId());
		appVendor.setUserId(userId);
		appVendor.setCreatedBy(userId);
		appVendor.setUpdatedBy(userId);
		//appVendor.setVendorId(vndrId);
		appVendor.setVendorDetail(vndr);
		appVendor.setStatus("Approve");
		appVendor.setComment(comment);
		vir.saveApproveVendor(appVendor);
		
	}
	
	
	@Transactional
	public void declineVendor(long vendorId, String comment, String userName) {
		AuditLogTrail auditLog = new AuditLogTrail("VendorDetail", "Decline",
				"permissionFlag", "Pending", "Decline", userName, vendorId);
		vir.declineVendor(vendorId);
		vir.saveAuditTrail(auditLog);
		
		List<Users> sessionUserList= vir.getUserObejectBasedOnUserName(userName);
		Users sessionUserObj = sessionUserList.get(0);
		int userId = sessionUserObj.getUserId();
		
		ApproveVendor appVendor = new ApproveVendor();
		Vendor vndr = vir.getVendorObjBasedOnIDToUpdate(vendorId).get(0);
				
		appVendor.setUserId(userId);
		appVendor.setCreatedBy(userId);
		appVendor.setUpdatedBy(userId);
		//appVendor.setVendorId(vndrId);
		appVendor.setVendorDetail(vndr);
		appVendor.setStatus("Decline");
		appVendor.setComment(comment);
		vir.saveApproveVendor(appVendor);
		
	}
	
	@Transactional
	public List<ApproveVendor> getApproverObjList(long vendorId) {
		List<ApproveVendor> vendApprObjList = vir.getApproverObjList(vendorId);
		return vendApprObjList;
	}
	
	@Transactional
	public List<Vendor> searchVendorForApproval(String startDate, String endDate, String supplierName, String userName) {
		List<Users> sessionUserList= vir.getUserObejectBasedOnUserName(userName);
		Users sessionUserObj = sessionUserList.get(0);
		int  orgId = sessionUserObj.getUserOrganization().getOrgID();
		
		List<Vendor> vendorObj = vir.searchVendorForApproval(startDate,endDate,supplierName,orgId);
		return vendorObj;
	}
	
	@Transactional
	public List<ApproveVendor> getVendorApprovalHistoryList(String userName) {
		
		List<Users> sessionUserList= vir.getUserObejectBasedOnUserName(userName);
		Users sessionUserObj = sessionUserList.get(0);
		long userId = sessionUserObj.getUserId();
		System.out.println("In VendorInfoService : getVendorApprovalHistoryList method : userId = " + userId);
		List<ApproveVendor> vendorObj = vir.getVendorApprovalHistoryList(userId);
		return vendorObj;
	}
	
	@Transactional
	public List<Vendor> searchVendorApprovalHistory(String startDate, String endDate, String supplierName, String userName) {
		List<Users> sessionUserList= vir.getUserObejectBasedOnUserName(userName);
		Users sessionUserObj = sessionUserList.get(0);
		int  orgId = sessionUserObj.getUserOrganization().getOrgID();
		
		List<Vendor> vendorObj = vir.searchVendorApprovalHistory(startDate,endDate,supplierName,orgId);
		return vendorObj;
	}
	
	@Transactional
	public List<Vendor> searchVendorList(String suplierName, String showInactive, String userName) {
		List<Vendor> vendorList = vir.searchVendorList(suplierName, showInactive,orgID);
		return vendorList;
	}
	
	@Transactional
	public List<?> userNameBasedOnUserID() {
		List<?> invApprUserList = invoiceRep.userNameBasedOnUserID(orgID);
		return invApprUserList;
	}

}
