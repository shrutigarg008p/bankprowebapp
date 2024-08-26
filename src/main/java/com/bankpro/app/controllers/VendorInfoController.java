/**
 * 
 */
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

import com.bankpro.app.dto.ApproveVendorDTO;
import com.bankpro.app.dto.CompanyTypeDTO;
import com.bankpro.app.dto.PaymentMethodMasterDTO;
import com.bankpro.app.dto.StateMasterDTO;
import com.bankpro.app.dto.TermMasterDTO;
import com.bankpro.app.dto.VendorInfoDTO;
import com.bankpro.app.dto.VendorTypeDTO;
import com.bankpro.app.model.ApproveVendor;
import com.bankpro.app.model.CompanyTypeMaster;
import com.bankpro.app.model.PaymentMethodMaster;
import com.bankpro.app.model.StateMaster;
import com.bankpro.app.model.TermMaster;
import com.bankpro.app.model.Vendor;
import com.bankpro.app.model.VendorTypeMaster;
import com.bankpro.app.services.InvoiceServices;
import com.bankpro.app.services.VendorInfoService;

/**
 * @author Admin
 *
 */
@Controller
@RequestMapping("/vendorInfo/**")
public class VendorInfoController {

	@Autowired
	VendorInfoService vis;
	
	private static final String uploadFolderName = "VendorUpload";
	private static final Logger LOGGER = Logger.getLogger(VendorInfoController.class);

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/saveVendorInfo", method = RequestMethod.POST)
	public void createVendorInfo(@RequestBody VendorInfoDTO vendorObj, Principal princ) {
		vis.createNewVendor(vendorObj, princ.getName());

	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/viewDataOfVendor", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<VendorInfoDTO> vendorListView(Principal pri) {
		System.out.println("in the vendiorrrrr get the valueeeeeeeeeeeeeeeeeee");
		List<Vendor> vendorObj = vis.getValueForVendor(pri.getName());		 
		List<VendorInfoDTO> lii = VendorInfoDTO.mapVendorList(vendorObj);
		return lii;

	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updateVendorDetail", method = RequestMethod.PUT)
	public void updateVendorInfoRequest(@RequestBody VendorInfoDTO venDTO, Principal prin) {
		vis.doUpdateForVendor(venDTO, prin.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/deleteDocInfoFromVendor", method = RequestMethod.PUT)
	public void updateDocInfoFromOrgColumnn(
			@RequestParam("rowId") int rowId,
			@RequestParam("columnName") String columnName, Principal prin) {
		System.out.println("going to update the value of edit registrationnnnnnnn    gggg       "+ columnName);
		vis.deleteFileAndUpdateDocValueOfVendor(rowId, columnName, prin.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/inactiveSelectedVendor", method = RequestMethod.PUT)
	public void inactiveSelectedVendor(@RequestBody VendorInfoDTO venDTO, Principal prin) {
		System.out.println("new file size is       "+venDTO.getVendID());
		vis.inactiveSelectedVendor(venDTO.getVendID(), prin.getName());
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
			vis.batchFileUploadOfCSV(mainFileToMove, pri.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getVendorType", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<VendorTypeDTO> getVendorType() {
		System.out.println("In Get Vendor Type Method ");
		List<VendorTypeMaster> vendorTypeObj = vis.getVendorType();		 
		List<VendorTypeDTO> vType = VendorTypeDTO.mapFromVendorTypeEntities(vendorTypeObj);
		System.out.println(" Total Vendor Type available = " + vType.size());
		return vType;

	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getCompanyType", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<CompanyTypeDTO> getCompanyType() {
		System.out.println("In Get Company Type Method ");
		List<CompanyTypeMaster> companyTypeObj = vis.getCompanyType();		 
		List<CompanyTypeDTO> cType = CompanyTypeDTO.mapFromCompanyTypeEntities(companyTypeObj);
		System.out.println(" Total Comapny Type available = " + cType.size());
		return cType;

	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getStatList", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<StateMasterDTO> getStateList(@RequestParam("countryID") int countryId) {
		List<StateMaster> stateObj = vis.getStateList(countryId);
		List<StateMasterDTO> stateList = StateMasterDTO.mapFromStateEntities(stateObj);
		return stateList;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getPaymentTerm", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<TermMasterDTO> getPaymentTermList() {
		List<TermMaster> termObj = vis.getPaymentTermList();
		List<TermMasterDTO> termList = TermMasterDTO.mapFromTermEntities(termObj);
		return termList;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getPaymentMethod", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<PaymentMethodMasterDTO> getPaymentMethodList() {
		List<PaymentMethodMaster> payMethodObj = vis.getPaymentMethodList();
		List<PaymentMethodMasterDTO> payMethodList = PaymentMethodMasterDTO.mapFromPaymentMethodEntities(payMethodObj);
		return payMethodList;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getVendorForApproval", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<VendorInfoDTO> vendorListForApproval(Principal pri) {
		System.out.println("In Get vendor for Approval method : VendorInfoController ");
		List<Vendor> vendorObj = vis.getVendorListForApproval(pri.getName());		 
		List<VendorInfoDTO> vendorList = VendorInfoDTO.mapVendorList(vendorObj);
		return vendorList;

	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/approveVendor", method = RequestMethod.PUT)
	public void approveVendor(@RequestParam("vendorId") long vendorId,@RequestParam("comment") String comment, Principal pri) {
		System.out.println("In Approve Vendor Method to approve Vendor : " + vendorId);
		String userName = pri.getName();
		vis.approveVendor(vendorId,comment,userName);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/declineVendor", method = RequestMethod.PUT)
	public void declineVendor(@RequestParam("vendorId") long vendorId,@RequestParam("comment") String comment, Principal pri) {
		System.out.println("In Decline Vendor Method to decline Vendor : " + vendorId);
		String userName = pri.getName();
		vis.declineVendor(vendorId,comment,userName);
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/approverLog", method = RequestMethod.GET)
	public List<ApproveVendorDTO> getApproverLog(@RequestParam("vendorId") long vendorId, Principal pri) {
		Map<Integer, String> mapForUserNameWithID = new HashMap<Integer, String>();
		List<ApproveVendor> approverVendorList = vis.getApproverObjList(vendorId);
		List<?> userNameWithIDList = vis.userNameBasedOnUserID();
		if (userNameWithIDList != null) {
			for (Object obj : userNameWithIDList) {
				Object[] objRow = (Object[]) obj;
				String userName = objRow[1].toString() + " "+ objRow[2].toString();
				mapForUserNameWithID.put(Integer.parseInt(objRow[0].toString()), userName);
			}
			if (approverVendorList != null && !approverVendorList.isEmpty()) {
				List<ApproveVendorDTO> lii = ApproveVendorDTO.mapFromApproverVendEntities(approverVendorList,mapForUserNameWithID);
				System.out.println("ffffffffffffsdfff    " + lii.size());
				return lii;
			}
		}
		return null;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/searchVendor", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<VendorInfoDTO> searchVendorListForApproval(@RequestParam("dateFrom") String dateFrom,@RequestParam("dateTo") String dateTo, @RequestParam("supplierName") String supplierName, Principal pri) {
		
		List<Vendor> vendorObj = vis.searchVendorForApproval(dateFrom,dateTo,supplierName,pri.getName());	
		
		List<VendorInfoDTO> vendorDTOList = VendorInfoDTO.mapVendorList(vendorObj);
		return vendorDTOList;
		
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
    	System.out.println("in exceptionnnnnnnnnnnnn     "+exc.getMessage()+"       rr     "+exc);
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getVendorOnID", method = RequestMethod.GET)
	public List<VendorInfoDTO> getVendorForInvoiceTrail(@RequestParam("vendorID") long vendID) {
		System.out.println("In Get Vendor Type Method    "+vendID);
		List<Vendor> vendorTypeObj = vis.doVendorBasedOnId(vendID);		 
		List<VendorInfoDTO> vType = VendorInfoDTO.mapVendorList(vendorTypeObj);
		return vType;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getVendorApprovalHistory", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<VendorInfoDTO> vendorApprovalHistory(Principal pri) {
		List<Vendor> vendorObjList =new ArrayList<>();
		System.out.println("In Get vendor Approval History method : VendorInfoController ");
		List<ApproveVendor> appVendorObj = vis.getVendorApprovalHistoryList(pri.getName());	
		System.out.println("Total Approved list  = " + appVendorObj.size());
		for (ApproveVendor approveVendor1 : appVendorObj) {
			vendorObjList.add(approveVendor1.getVendorDetail());
		}	
		List<VendorInfoDTO> vendorList = VendorInfoDTO.mapVendorList(vendorObjList);
		return vendorList;
	}
	

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/searchVendorApprovalHistory", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<VendorInfoDTO> searchVendorApprovalHistory(@RequestParam("dateFrom") String dateFrom,@RequestParam("dateTo") String dateTo, @RequestParam("supplierName") String supplierName, Principal pri) {	
		System.out.println("In Search Vendor for approval History method with Fname = " + supplierName);

		List<Vendor> vendorObj = vis.searchVendorApprovalHistory(dateFrom,dateTo,supplierName,pri.getName());	
		
		List<VendorInfoDTO> vendorDTOList = VendorInfoDTO.mapVendorList(vendorObj);
		return vendorDTOList;
		
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/searchBySuplierName", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<VendorInfoDTO> searchVendirList(@RequestParam("suplierName") String suplierName, @RequestParam("showInactive") String showInactive, Principal pri) {
		
		List<Vendor> vendorList = vis.searchVendorList(suplierName, showInactive,pri.getName());
		System.out.println(" UserRegistorController total users =    " + vendorList.size());
		
		List<VendorInfoDTO> vendorDTOList = VendorInfoDTO.mapVendorList(vendorList);
		return vendorDTOList;
	}

}
