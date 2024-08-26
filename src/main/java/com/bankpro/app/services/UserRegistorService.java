package com.bankpro.app.services;

import static com.bankpro.app.services.ValidationUtils.assertNotBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.SystemPropertyUtils;

import com.bankpro.app.dao.UserRegistorRepository;
import com.bankpro.app.dto.BankDetailsDTO;
import com.bankpro.app.dto.NewUserRegistorDTO;
import com.bankpro.app.dto.UserDetailDTO;
import com.bankpro.app.model.AuditLogTrail;
import com.bankpro.app.model.CountryMaster;
import com.bankpro.app.model.DocInfoDetail;
import com.bankpro.app.model.NewBankDetails;
import com.bankpro.app.model.OrganizationDetail;
import com.bankpro.app.model.RoleMaster;
import com.bankpro.app.model.UserRoleRelation;
import com.bankpro.app.model.Users;

@Service
public class UserRegistorService {
	@Autowired
	UserRegistorRepository urr;
	

	static final String url = "https://paymentvista.com:8443/resources/public/resetpassword.html";
	static final String FROM = "info@paymentvista.com"; // Replace with your "From" address. This address must be verified.
	static String BODY =  "Dear User,\n\n you have successfully registered paymentvista. Please click on below link to set your password.\n\n";
	static final String SUBJECT = "Welcome to paymentvista";
	// Supply your SMTP credentials below. Note that your SMTP credentials are different from your AWS credentials.
	static final String SMTP_USERNAME = ""; // Replace with your SMTP username.
	static final String SMTP_PASSWORD = ""; // Replace with your SMTP password.

	// Amazon SES SMTP host name. This example uses the US West (Oregon) region.
	static final String HOST = "email-smtp.eu-west-1.amazonaws.com";

	// The port you will connect to on the Amazon SES SMTP endpoint. We are choosing port 25 because we will use STARTTLS to encrypt the connection.
	static final int PORT = 587;

	@Transactional
	public void createUser(String fname, String lname, String phNo,	String email, String org) {

		assertNotBlank(fname, "Username cannot be empty.");
	    assertNotBlank(email, "Email cannot be empty.");
		//assertMatches(email, EMAIL_REGEX, "Invalid email.");
		// assertNotBlank(password, "Password cannot be empty.");
//		 assertMatches(password, PASSWORD_REGEX,
//		 "Password must have at least 6 characters, with 1 numeric and 1 uppercase character.");

		if (!urr.isUsernameAvailable(email)) {
			System.out.println("the user name founddddddddd");
			throw new IllegalArgumentException("The Given Email ID is already register.");
		}
		
		if(!urr.isOrganizationNameAvailable(org)) {
			System.out.println("the organizationnnn name founddddddddd");
			throw new IllegalArgumentException("The Given Organization Name is already register.");
			
		}
		System.out.println("going to save the user although exception is captureeee");
		OrganizationDetail orgObj = new OrganizationDetail(org);
		Users user = new Users(fname, lname, phNo, email, orgObj, "InActive");

		Users usr = urr.save(user);
		
		System.out.println("created user with user id : " + usr.getUserId() + " and organization id : " + usr.getUserOrganization().getOrgID());
		// When user register create a master role with new created organization
		RoleMaster roleMaster = new RoleMaster();
		roleMaster.setRoleName("Company Admin");
		roleMaster.setRoleDesciption("Admin");
		roleMaster.setRoleStatus("Y");
		roleMaster.setRoleCreatedBy(usr.getUserId());
		roleMaster.setRoleCreatedDate(new Date());
		roleMaster.setOrgId(usr.getUserOrganization().getOrgID());
		RoleMaster role = urr.saveUserRole(roleMaster);
		
		// When user register create a role relation with new created master role of that organization
		UserRoleRelation roleRelation = new UserRoleRelation();
		roleRelation.setUser(usr);
		roleRelation.setUrr_roleID(role.getRoleId());
		roleRelation.setUrrCreatedBy(usr.getUserId());
		roleRelation.setUrrCreatedDate(new Date());
		urr.saveRoleRelation(roleRelation);
		
	}

	@Transactional
	public void resetPassword(String newPass,String email, boolean loginTermFlag) {
		List<Users> userObj = (List<Users>) urr.getUserObejectBasedOnUserName(email.trim());
		Users user = userObj.get(0);
		user.setUserPassword(newPass);
		user.setUserStatus("Active");
		user.setUserLoginTermAndCondition(loginTermFlag);
		System.out.println("in save method for password");
		urr.save(user);
	}

	public void sendEmail(String toEmail, String fName) throws AddressException,MessagingException {
		// Create a Properties object to contain connection configuration information.
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtp.port", PORT);
		// Set properties indicating that we want to use STARTTLS to encrypt the connection.
		// The SMTP session will begin on an unencrypted connection, and then the client
		// will issue a STARTTLS command to upgrade to an encrypted connection.
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.starttls.required", "true");
		props.put("mail.debug", "true");
		// Create a Session object to represent a mail session with the specified properties.
		Session session = Session.getDefaultInstance(props);	// Create a message with the specified information.
//		session.setDebug(true);
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(FROM));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		msg.setSubject(SUBJECT);
		BODY="<div style='padding:15px 10px 15px 10px;border-bottom:2px solid #d05709;background:#6ea6c9'>"
				+"	<table width='100%' cellspacing='0' cellpadding='0'>"
				+"		<tbody>"
				+"			<tr>"
				+"				<td style='vertical-align:middle'>"
				+"					<img style='max-height:30px' src='http://paymentvista.com/images/paymentvista-logo.png' translate='blank' style='height: 30px;'>"
				+"				</td>"
				+"			</tr>"
				+"		</tbody>"
				+"	</table>"
				+"</div>"
				+"<div style='font-family:arial;padding:15px;background-color:#f9f9f9;min-height:150px;line-height:20px;font-size:13px'>"

				+"	Hi "+fName+",<br><br>"
				+"	Thank you for signing up for your PaymentVista.com account.<br>"
				+"	Please click the button below to finish the account activation process:<br>"
				+"	<br>"
				+"	<a style='font-size:14px;font-family:Lato,Helvetica,Arial,sans-serif;color:#fff;text-decoration:none;border-radius:3px;background-color:#07669f;border-top:8px solid #07669f;border-bottom:8px solid #07669f;border-right:8px solid #07669f;border-left:8px solid #07669f;display:inline-block;font-weight:700;text-transform:uppercase;vertical-align:middle' href='"+ url + "?email=" + toEmail	+"'>Activate your account</a>"
				+"	<br><br>"

				+"	<span style='color:#999'>If the button above doesn't work, copy and paste the following link into your web browser:</span>"

				+"	<a href='"+ url + "?email=" + toEmail	+"'>"+ url + "?email=" + toEmail	+"</a>"

				+"	<br>"
				+"	<br>"

				+"	Have questions or would like assistance?<br>"
				+"	Call us at 877-345-2455 or email<a href='mailto:sales@paymentvista.com' target='_blank'>sales@hq.paymentvosta.com</a>"
				+"	<br>"
				+"	<br>"
				+"	Thank you,"
				+"	<br>"
				+"	The PaymentVista.com Team<br>"
				+"	<br>"
				+"	<br>"
				+"	Be safe! Hover your cursor over links in any email before clicking, to verify the location is safe. paymentvista.com links always start with <a href='#' target='_blank'>https://app.PaymentVista.com</a> or <a href='#' target='_blank'>http://www.paymentvista.com</a>.<br>"
				+"	<br>"
				+"	Please do not respond to this email. This e-mail was sent from a notification-only e-mail address.<br>"

				+"</div>"
				+"<div style='text-align:center;color:#aaaaaa;font-size:12px;padding-top:8px;padding-bottom:7px;background:rgb(235,235,235);border-top:1px solid #d05709'>"
				+" 	<div style='font-family:arial;padding-top:2px;padding-bottom:2px'>&copy; 20016 paymentvista.com, Inc </div>"
				+"</div>";
		msg.setContent(BODY, "text/html");
		// Create a transport.
		Transport transport = session.getTransport();

		// Send the message.
		try {
			System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");

			// Connect to Amazon SES using the SMTP username and password you
			// specified above.
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);	// Send the email.
			transport.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Email sent!");
		} catch (Exception ex) {
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + ex.getMessage());
		} finally {
			// Close and terminate the connection.
			transport.close();
		}
	}

	public List<CountryMaster> doGetCountryList() {
		System.out.println("country list payedddddddddddddd service");
		List<CountryMaster> countryList = urr.getCountryList();
		System.out.println("list is  size is    " + countryList.size());
		return countryList;
	}

	@Transactional
	public void addNewUser(NewUserRegistorDTO userDTO, String userName) {
		System.out.println("name is    " + userDTO.getFirstName());
		
		if (!urr.isUsernameAvailable(userDTO.getEmail())) {
			System.out.println("the user name founddddddddd");
			throw new IllegalArgumentException("The Given Email ID is already registered.");
		}
		
		//OrganizationDetail orgObj = new OrganizationDetail(userDTO.getOrganization());
		OrganizationDetail orgObj = null;
		List<OrganizationDetail> orgObjList = urr.getOrganizationById(userDTO.getOrgID());
		if(orgObjList != null && orgObjList.size() > 0){
			System.out.println("User Org list found with size = " + orgObjList.size()); 
			orgObj = orgObjList.get(0);
		}
		

		List<?> roleId = userDTO.getRoleList();

		Users userObj = new Users();
		userObj.setUserFirstName(userDTO.getFirstName());
		userObj.setUserLastName(userDTO.getLastName());
		userObj.setUserEmail(userDTO.getEmail());
		userObj.setUserContectNo(userDTO.getContactNo());
		userObj.setUserEmployeeNo(userDTO.getEmployeeNo());
		userObj.setUserAddress(userDTO.getAddressLine1());
		userObj.setUserStatus("Active");
		userObj.setUserOrganization(orgObj);
		System.out.println("goinf to save the valueeeeeeeeeeeeeee     ");
		
		userObj.setRoleRelation(new ArrayList<UserRoleRelation>());
		for (Object iterable : roleId) {
			UserRoleRelation roleRelation = new UserRoleRelation();
			int roleID = Integer.parseInt(iterable.toString());
			System.out.println("isddddd     " + roleID);
			roleRelation.setUrr_roleID(roleID);
			roleRelation.setUrrCreatedDate(new Date());
			userObj.getRoleRelation().add(roleRelation);
		}

		System.out.println("going to save the user objecttt    "
				+ userObj.getRoleRelation().size());
		
		// Set user_detailSubmitFlag AND user_bankDialogFlag false by default
		userObj.setUserDetailOfSubmitFlag(false);
		userObj.setUserBankDialogueFlag(false);
		
		// urr.save(userObj);
		long userId = urr.saveUser(userObj);
		System.out.println("New user Id = " + userId);
		AuditLogTrail alt = new AuditLogTrail("userDetail", "Create", "User Create","New User", "New User", userName, userId);		 		 
	    urr.save(alt);

	}
	
	
	@Transactional
	public List<?> getOrganizationDetail(String userName) {
		System.out.println("user name of login is    " + userName);
		
		List<Users> users = (List<Users>) urr.getUserObejectBasedOnUserName(userName);
		Users usrObj = users.get(0);
		int orgID = usrObj.getUserOrganization().getOrgID();
		System.out.println("In UserRegistorService org id found  = " + orgID);
		
		List<?> orgList = urr.getOrganizationDetail(orgID);
		return orgList;
	}

	@Transactional
	public List<RoleMaster> fetchVlaueForUserRole() {
		List<RoleMaster> roleList = urr.roleMasterList();
		return roleList;

	}
	
	@Transactional
	public List<?> relatedUserRoleBasedValue() {
		List<?> roleList = urr.selectedRoleValue();
		return roleList;

	}

	@Transactional
	public void saveBankAccountDetail(BankDetailsDTO bankDTO, String userName) {
		NewBankDetails bankObj = new NewBankDetails();
	    bankObj = doCommonForBankDetail(bankDTO, bankObj);
		bankObj = commonForFileUploadInBank(bankDTO, bankObj);

		List<Users> users = (List<Users>) urr.getUserObejectBasedOnUserName(userName);
		Users usrObj = users.get(0);
		System.out.println("bank dialog flag is    "+ bankDTO.isTrigerBankDialogueFlag());
		if (bankDTO.isTrigerBankDialogueFlag() == false) {
			usrObj.setUserBankDialogueFlag(bankDTO.isTrigerBankDialogueFlag());
		}
		bankObj.setBankOrgId(usrObj.getUserOrganization().getOrgID());		
		Long bankID = urr.save(bankObj);
		if (!bankDTO.getNewAddedDocListInBank().isEmpty()) {
			doSaveFilesOfManuallyAdded(bankDTO, bankID);
		}
		urr.save(usrObj);		
		AuditLogTrail alt = new AuditLogTrail("BankDetail", "Creation", "New","Nill", "New", userName, bankID);		 		 
	    urr.save(alt);
	}
	
	public NewBankDetails doCommonForBankDetail(BankDetailsDTO bankDTO,	NewBankDetails bankObj) {
		bankObj.setBankOTPVerification(true);
		if (bankDTO.getTransactionPass() != null && bankDTO.getTransactionPass() != "") {
			bankObj.setBankTransactionPass(bankDTO.getTransactionPass());
		}
		bankObj.setBankCountryId(bankDTO.getCountryID());
		bankObj.setBankName(bankDTO.getBankName());
		bankObj.setBankAccountHolderName(bankDTO.getAccHolderName());
		bankObj.setBankAccountNo(bankDTO.getAccountNumber());
		bankObj.setBankBranchAddress(bankDTO.getBranchAddress());
		bankObj.setBankIFSCCode(bankDTO.getIfscCode());
		bankObj.setBankMICRNo(bankDTO.getMicrNo());
		bankObj.setBankIBANNo(bankDTO.getIbanNo());
		bankObj.setBankAmountLimit(bankDTO.getAmountLimit());
		bankObj.setBankAvailableAP(bankDTO.isAvailableForAP());
		bankObj.setBankDefaultAP(bankDTO.isDefaultForAP());
		bankObj.setBankAvailableAR(bankDTO.isDefaultForAP());
		bankObj.setBankAgreePaymentTermService(bankDTO.isTermAcceptanceFlag());
		return bankObj;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Users doGetValueForEditRegistration(String userName) {
		System.out.println("user name of login is    " + userName);
		List<Users> users = (List<Users>) urr.getUserObejectBasedOnUserName(userName);
		return users.get(0);

	}
	
	@Transactional
	public List<?> doGetFlagToShowCompanyDialogOnFirstLogin(String userName) {
		System.out.println("user name of login is    " + userName);
		List<?> users = (List<?>) urr.getFlagToShowCompanyDialog(userName);
		return users;
	}
	
	@Transactional
	public void saveEditRegistrationForm(NewUserRegistorDTO userDTO,Users userObj) {
		if(!urr.isOrganizationNameAvailableForUpdate(userDTO.getOrganization(), userDTO.getEmail())) {
			throw new IllegalArgumentException("The Given Organization Name is already register.");			
		}
		userObj.setUserDetailOfSubmitFlag(userDTO.isSubmitDialogueRequestFlag());
		userObj.getUserOrganization().setOrgLegalEntityName(userDTO.getLegalEntityName());
		userObj.getUserOrganization().setOrgName(userDTO.getOrganization());
		userObj.getUserOrganization().setOrgCINNumber(userDTO.getCin());
		userObj.getUserOrganization().setOrgPanNumber(userDTO.getPanNo());
		userObj.getUserOrganization().setOrgAddressLine1(userDTO.getAddressLine1());
		userObj.getUserOrganization().setOrgAddressLine2(userDTO.getAddressLine2());
		userObj.getUserOrganization().setOrgAddressLine3(userDTO.getAddressLine3());
		userObj.getUserOrganization().setOrgPinCode(userDTO.getPinCode());
		userObj.getUserOrganization().setOrgURL(userDTO.getUrl());
		userObj.getUserOrganization().setOrgPhoneNumber(userDTO.getContactNo());
		userObj.getUserOrganization().setOrgIndustry(userDTO.getIndustry());
		userObj.getUserOrganization().setOrgEmployeCount(userDTO.getEmployCount());
		userObj.getUserOrganization().setOrgEntityType(userDTO.getEntityType());
		userObj.getUserOrganization().setOrgCountryId(userDTO.getCountryID());
		userObj.getUserOrganization().setOrgUpdatedDate(new Date());
		userObj.getUserOrganization().setOrgUpdatedBy(userObj.getUserId());
		userObj.getUserOrganization().setOrgCitiName(userDTO.getCitiName());
		userObj.getUserOrganization().setOrgStateNameId(userDTO.getStateNameID());
		if (userDTO.getAddressProofDoc() != null && userDTO.getAddressProofDoc() != "") {
			userObj.getUserOrganization().setOrgAddressProofDoc(userDTO.getAddressProofDoc());
		}
		if (userDTO.getPanCardDoc() != null && userDTO.getPanCardDoc() != "") {
			userObj.getUserOrganization().setOrgPanCardDoc(userDTO.getPanCardDoc());
		}
		
	    urr.save(userObj);
		if (!userDTO.getNewAddedDocList().isEmpty()) {
			for (int i = 0; i < userDTO.getNewAddedDocList().size(); i++) {
				DocInfoDetail newObj = new DocInfoDetail();
				LinkedHashMap<?, ?> DocMap = (LinkedHashMap<?, ?>) userDTO.getNewAddedDocList().get(i);
				if(DocMap.get("manuallyGivenFileName") != null){
					newObj.setDocManuallyGivenFileName(DocMap.get("manuallyGivenFileName").toString());
				}				
				newObj.setDocFileName(DocMap.get("fileName").toString());
				newObj.setDocReferenceId(Integer.parseInt(DocMap.get("referenceId").toString()));
				newObj.setDocReferenceType(DocMap.get("refType").toString());
				urr.saveDocInfo(newObj);
			}
		}
	}
	
	@Transactional
	public void deleteFileAndUpdateDocValue(int rowID, String columnName, String userName){
		System.out.println("bank id   fff "+rowID);
		AuditLogTrail alt = new AuditLogTrail("BankDetail", "Update",
				"All", "All", "All", userName, Long.valueOf(rowID));
		urr.deleteDocInfoFromColumn(rowID, columnName);
		urr.save(alt);
		
	}
	
	@Transactional
	public void disableSelectedAccount(BankDetailsDTO bankDto, String userName){
		System.out.println("bank id   fff "+bankDto.getBankId());
		AuditLogTrail alt = new AuditLogTrail("BankDetail", "Update",
				"BankActive", "Active", "Inactive", userName ,bankDto.getBankId());
		urr.updateToSetAcctDesable(bankDto.getBankId());
		urr.save(alt);
		
	}
	
	@Transactional
	public List<NewBankDetails> getBankDetailForView(String userName) {
		List<Users> users = (List<Users>) urr.getUserObejectBasedOnUserName(userName);
		Users usrObj = users.get(0);
		int orgID = usrObj.getUserOrganization().getOrgID();
		List<NewBankDetails> bankList = urr.getBankDetailToView(orgID);
		return bankList;

	}

	@Transactional
	public void doUpdateBankDetail(BankDetailsDTO bankDTO, String userName) {
		AuditLogTrail alt = new AuditLogTrail("BankDetail", "Update",
				"All", "All", "All", userName, bankDTO.getBankId());
		NewBankDetails bankObj = getBankObjToUpdate(bankDTO.getBankId());
		if (bankObj != null) {
			NewBankDetails modifiedObj = doCommonForBankDetail(bankDTO, bankObj);
			NewBankDetails scndModifiedBankObj = commonForFileUploadInBank(bankDTO,modifiedObj);
			Long bankID = urr.save(scndModifiedBankObj);			
			if (!bankDTO.getNewAddedDocListInBank().isEmpty()) {
				doSaveFilesOfManuallyAdded(bankDTO, bankID);
			}
			urr.save(alt);
		}
	}
	
	@Transactional
	public void doSaveFilesOfManuallyAdded(BankDetailsDTO bankDTO, Long bankID) {
		for (int i = 0; i < bankDTO.getNewAddedDocListInBank().size(); i++) {
			DocInfoDetail newObj = new DocInfoDetail();
			LinkedHashMap<?, ?> DocMap = (LinkedHashMap<?, ?>) bankDTO.getNewAddedDocListInBank().get(i);
			newObj.setDocManuallyGivenFileName(DocMap.get("manuallyGivenFileName").toString());
			newObj.setDocFileName(DocMap.get("fileName").toString());
			newObj.setDocReferenceId(bankID.intValue());
			newObj.setDocReferenceType("BankDetail");
			urr.saveDocInfo(newObj);
		}
	
	}
	
	
	public NewBankDetails commonForFileUploadInBank(BankDetailsDTO bankDTO,NewBankDetails bankObj){
		if(bankDTO.getDebitMandateDoc() != null && bankDTO.getDebitMandateDoc() != ""){
			bankObj.setBankDebitMandateDoc(bankDTO.getDebitMandateDoc());
		}
		if(bankDTO.getBoardResolutionDoc() != null && bankDTO.getBoardResolutionDoc() != ""){
			bankObj.setBankBoardResolutionDoc(bankDTO.getBoardResolutionDoc());
		}
		if(bankDTO.getPanCardDoc() != null && bankDTO.getPanCardDoc() != ""){
			bankObj.setBankPanCardDoc(bankDTO.getPanCardDoc());
		}
		if(bankDTO.getAddressProofDoc() != null && bankDTO.getAddressProofDoc() != ""){
			bankObj.setBankAddressProofDoc(bankDTO.getAddressProofDoc());
		}
		if(bankDTO.getCancelCheque() != null && bankDTO.getCancelCheque() != ""){
			bankObj.setBankCancellChequeDoc(bankDTO.getCancelCheque());
		}	
		return bankObj;
	}

	@Transactional
	public NewBankDetails getBankObjToUpdate(Long bankID) {
		List<NewBankDetails> bankList = urr.getBankObjBasedOnBankID(bankID);
		System.out.println("size is list is     "+bankList.size());
		 NewBankDetails bankObj = bankList.size() == 1 ? bankList.get(0) : null;
		return bankObj;
	
	}
	
	@Transactional
	public List<AuditLogTrail> doGetAuditValueForView(long refID, String refType) {
		List<AuditLogTrail> auditObj = urr.getAuditTrailList(refID, refType);
		return auditObj;
	}
	
	@Transactional
	public List<Users> getAllUserList(String userName) {
		List<Users> usrList=urr.getUserObejectBasedOnUserName(userName);
		Users userObj = usrList.get(0);
		int orgId= userObj.getUserOrganization().getOrgID();
		System.out.println("orgId    ttttttttttttt           "+orgId);
		List<Users> userList = urr.getAllUserList(orgId);
		return userList;
	}
	
		
	@Transactional
	public void updateUser(UserDetailDTO userDTO, String userName) {
		List<Users> usrList=urr.getUserObejectBasedOnUserName(userDTO.getEmail());
		Users userObj =  usrList.get(0);
		userObj.setUserFirstName(userDTO.getFirstName());
		userObj.setUserLastName(userDTO.getLastName());
		userObj.setUserEmail(userDTO.getEmail());
		userObj.setUserContectNo(userDTO.getContactNo());
		//userObj.getUserOrganization().setOrgName(userDTO.getOrganization());
		userObj.setUserEmployeeNo(userDTO.getEmployeeNo());
		userObj.setUserAddress(userDTO.getAddressLine1());
		
		//urr.save(userObj);		
		long userId = urr.saveUser(userObj);
		
		System.out.println("New user Id = " + userId + " And userName = " + userName);
		
		AuditLogTrail alt = new AuditLogTrail("userDetail", "Update", "Active User","Update User", "Updated User", userName, userId);		 		 
	    urr.save(alt);
		
	}
	
	@Transactional
	public List<Users> searchUserList(String firstName, String showInactive) {
		List<Users> userList = urr.searchUserList(firstName, showInactive);
		
		return userList;
	}
	
	
	@Transactional
	public List<DocInfoDetail> doGetValueForUploadedDoc(int refID, String refType) {
		List<DocInfoDetail> docObj = urr.getDocDataForView(refID,refType);
		return docObj;
	}
	
	@Transactional
	public void deleteSelectedBankDocInfo(int docId, String userName) {
		AuditLogTrail alt = new AuditLogTrail("DocDetail", "Delete",
				"DocInfo", "Value", "Delete", userName ,Long.valueOf(docId));
		urr.deleteDocInfo(docId);
		urr.save(alt);

	}
	
	@Transactional
	public void deleteFileAndUpdateDocValueOfOrg(int rowID, String columnName, String userName){
		System.out.println("bank id   fff "+rowID);
		AuditLogTrail alt = new AuditLogTrail("OrgDetail", "Update",
				columnName, "All", "All", userName, Long.valueOf(rowID));
		urr.deleteDocInfoFromColumnOfOrg(rowID, columnName);
		urr.save(alt);
	}
	
	@Transactional
	public void disableUser(long userId, String userName) {
		System.out.println("UserRegisterService in disableUser userId = " + userId);	
		urr.disableUser(userId);
		System.out.println("In User Registration Service method : to disable User Disable UserId = " + userId + " and disabled by user name = " + userName);
		AuditLogTrail alt = new AuditLogTrail("userDetail", "Disable", "Disable User","Active User", "User Disabled", userName, userId);		 		 
	    urr.save(alt);
		
	}
	
	@Transactional
	public long forgotPassword(String userName) {
		System.out.println("UserRegisterService in forgot Password userName = " + userName);	
		long usrCount = urr.forgotPassword(userName);
		return usrCount;
	}
}
