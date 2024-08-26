/**
 * 
 */
package com.bankpro.app.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.bankpro.app.dto.AuditTrailLogDTO;
import com.bankpro.app.dto.BankDetailsDTO;
import com.bankpro.app.dto.CountryMasterDTO;
import com.bankpro.app.dto.DocInfoDetailDTO;
import com.bankpro.app.dto.FirstLoginRequiredDetailDTO;
import com.bankpro.app.dto.NewUserRegistorDTO;
import com.bankpro.app.model.AuditLogTrail;
import com.bankpro.app.model.CountryMaster;
import com.bankpro.app.model.DocInfoDetail;
import com.bankpro.app.model.NewBankDetails;
import com.bankpro.app.model.OrganizationDetail;
import com.bankpro.app.model.Users;
import com.bankpro.app.services.UserRegistorService;

/**
 * @author Admin
 *
 */
@Controller
@RequestMapping("/detailRegistration/**")
public class DetailedUserRegister {
	private static final Logger LOGGER = Logger.getLogger(DetailedUserRegister.class); 
	
	@Autowired
	UserRegistorService urs;

	Users userObj;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/finalComanyDialogReq", method = RequestMethod.POST)
	public void doSaveConpanyDialogDetail(@RequestBody NewUserRegistorDTO userDTO, Principal principal) {
		Users userObj = urs.doGetValueForEditRegistration(principal.getName());
		urs.saveEditRegistrationForm(userDTO, userObj);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/countryView", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<CountryMasterDTO> countryListView() {
		List<CountryMaster> countryObj = urs.doGetCountryList();
		// System.out.println("in the methis of get the valueeeeeeeeeeeeeeeeeee");
		List<CountryMasterDTO> lii = CountryMasterDTO.mapCountryList(countryObj);
		return lii;

	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/companyDialogDetail", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public FirstLoginRequiredDetailDTO valueForCompanyDialogFlagAndEmail(Principal principal) {
		List<?> comanyFlagAndEmailID = urs.doGetFlagToShowCompanyDialogOnFirstLogin(principal.getName());
		Object[] row = (Object[]) comanyFlagAndEmailID.get(0);
		System.out.println("row 3 value is    "+ (OrganizationDetail)row[3]);
		OrganizationDetail org = (OrganizationDetail)row[3];
		System.out.println("row 3 value is    "+ org.getOrgID());
		FirstLoginRequiredDetailDTO comyDetail = new FirstLoginRequiredDetailDTO(row[0].toString(), (boolean) row[1], (boolean) row[2],
				(OrganizationDetail)row[3]);
		return comyDetail;

	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/bankDetail", method = RequestMethod.POST)
	public void doSaveBankDetail(@RequestBody BankDetailsDTO bankDTO, Principal principal) {
		System.out.print("in class of a controllerrrrrr bank detaillllllllllssssss        ");
		urs.saveBankAccountDetail(bankDTO, principal.getName());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/editRegistration", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public NewUserRegistorDTO fetchValueForEditRegistration(Principal principal) {
		userObj = urs.doGetValueForEditRegistration(principal.getName());
		NewUserRegistorDTO userDTO = new NewUserRegistorDTO(userObj);
		return userDTO;

	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updateRegistrationDetail", method = RequestMethod.PUT)
	public void updateEditRegistrationRequest(@RequestBody NewUserRegistorDTO userDTO) {
		System.out.println("new file size is       "+userDTO.getNewAddedDocList().size());
		urs.saveEditRegistrationForm(userDTO, userObj);
	}
	

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/viewBankDetail", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<BankDetailsDTO> fetchValueForViewBankDetail(Principal prince) {
		System.out.println("get the valueeeeeeeeee for  view bank detailllllllllllllll");
		List<NewBankDetails> bankList = urs.getBankDetailForView(prince.getName());
		List<BankDetailsDTO> bankDTO = BankDetailsDTO.mapBankDetailFromList(bankList);
		return bankDTO;

	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/udpateBankDetail", method = RequestMethod.PUT)
	public void updateBankDetailRequest(@RequestBody BankDetailsDTO bankDTO, Principal principal) {
		System.out.println("going to update the value updateBankDetailRequest   "+ bankDTO.getBankId());
		urs.doUpdateBankDetail(bankDTO, principal.getName());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/viewAuditData", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<AuditTrailLogDTO> viewAuditTrail(
			@RequestParam("refID") long refId,
			@RequestParam("refType") String refType) {
		List<AuditLogTrail> auditObj = urs.doGetAuditValueForView(refId,refType);
		if (auditObj != null) {
			List<AuditTrailLogDTO> lii = AuditTrailLogDTO.mapAuditList(auditObj);
			return lii;
		}
		return null;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/inactiveSelectedAcct", method = RequestMethod.PUT)
	public void inactiveSelectedAcct(@RequestBody BankDetailsDTO bankDTO, Principal pri) {
		System.out.println("in the controlllerrrrrr   rrrr   ");
		urs.disableSelectedAccount(bankDTO, pri.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/uploadedFileDataView", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<DocInfoDetailDTO> getValueToShowUploadedFile(			
			@RequestParam("orgtionID") int refID,
			@RequestParam("referenceType") String refType) {
		 System.out.println("file to viewwwwwwwww     "+refID+"      "+refType);
		 List<DocInfoDetail> docObj = urs.doGetValueForUploadedDoc(refID, refType);
		if (docObj != null) {
			List<DocInfoDetailDTO> docList = DocInfoDetailDTO.mapDocInfoList(docObj);
			return docList;
		}
		return null;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/deleteDocInfo", method = RequestMethod.PUT)
	public void deleteSelectedDocInfo(@RequestParam("docID") int docId, Principal prinn) {
		System.out.println("in the controlllerrrrrr   rrrr   "+docId);
		urs.deleteSelectedBankDocInfo(docId, prinn.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/deleteDocFromColumn", method = RequestMethod.PUT)
	public void updateDocInfoFromColumnn(
			@RequestParam("rowId") int rowId,
			@RequestParam("columnName") String columnName, Principal prin) {
		System.out.println("going to update the value of edit registrationnnnnnnn    gggg       "+ columnName);
		urs.deleteFileAndUpdateDocValue(rowId, columnName, prin.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/deleteDocInfoFromOrg", method = RequestMethod.PUT)
	public void updateDocInfoFromOrgColumnn(
			@RequestParam("rowId") int rowId,
			@RequestParam("columnName") String columnName, Principal prin) {
		System.out.println("going to update the value of edit registrationnnnnnnn    gggg       "+ columnName);
		urs.deleteFileAndUpdateDocValueOfOrg(rowId, columnName, prin.getName());
	}
	
	/**
    *
    * error handler for backend errors - a 400 status code will be sent back, and the body
    * of the message contains the exception text.
    *
    * @param exc - the exception caught
    */
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
    	System.out.println("in exceptionnnnnnnnnnnnn     "+exc.getMessage()+"       rr     "+exc);
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }
	

}
