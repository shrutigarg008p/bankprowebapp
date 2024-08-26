/**
 * 
 */
package com.bankpro.app.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.bankpro.app.model.Users;

/**
 * @author Lakharwal
 *
 */
public class NewUserRegistorDTO {

	private String email;
	private String password;
	private String confirmPass;
	private String firstName;
	private String lastName;
	private String contactNo;
	private String employeeNo;
	private String noVerification;
	private String addressFile;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private List<?> roleList = new ArrayList<>();
	private String salt;
	private String photo;
	private int createdBy;
	private Date createdDate;
	private int updatedBy;
	private Date updatedDate;
	private Date lastLogin;
	private String status;
	private String organization;
	private String legalEntityName;
	private int cin;
	private String panNo;
	private String pinCode;
	private String url;
	private String inboxEmailID;
	private String industry;
	private String entityType;
	private int employCount;
	private String addressProofDoc;
	private String panCardDoc;
	private boolean submitDialogueRequestFlag;
	private int countryID;
	private String citiName;
	private int stateNameID;
	private int orgID;
	private List<?> newAddedDocList = new ArrayList<>();
	private boolean loginTermsAndConditon;

	public NewUserRegistorDTO() {

	}

	public NewUserRegistorDTO(Users userObj) {
		this.legalEntityName = userObj.getUserOrganization().getOrgLegalEntityName();
		this.organization = userObj.getUserOrganization().getOrgName();
		this.cin = userObj.getUserOrganization().getOrgCINNumber();
		this.panNo = userObj.getUserOrganization().getOrgPanNumber();
		this.addressLine1 = userObj.getUserOrganization().getOrgAddressLine1();
		this.addressLine2 = userObj.getUserOrganization().getOrgAddressLine2();
		this.addressLine3 = userObj.getUserOrganization().getOrgAddressLine3();
		this.pinCode = userObj.getUserOrganization().getOrgPinCode();
		this.url = userObj.getUserOrganization().getOrgURL();
		this.contactNo = userObj.getUserOrganization().getOrgPhoneNumber();
		this.email = userObj.getUserEmail();
		this.inboxEmailID = userObj.getUserOrganization().getOrgInboxEmail();
		this.employCount = userObj.getUserOrganization().getOrgEmployeCount();
		this.industry = userObj.getUserOrganization().getOrgIndustry();
		this.panCardDoc = userObj.getUserOrganization().getOrgPanCardDoc();
		this.addressProofDoc = userObj.getUserOrganization().getOrgAddressProofDoc();
		this.entityType = userObj.getUserOrganization().getOrgEntityType();
		this.countryID = userObj.getUserOrganization().getOrgCountryId();
		this.orgID = userObj.getUserOrganization().getOrgID();
		this.citiName = userObj.getUserOrganization().getOrgCitiName();
		this.stateNameID = userObj.getUserOrganization().getOrgStateNameId();
	}

	
	
	/**
	 * @return the stateNameID
	 */
	public int getStateNameID() {
		return stateNameID;
	}

	/**
	 * @param stateNameID the stateNameID to set
	 */
	public void setStateNameID(int stateNameID) {
		this.stateNameID = stateNameID;
	}

	/**
	 * @return the citiName
	 */
	public String getCitiName() {
		return citiName;
	}

	/**
	 * @param citiName the citiName to set
	 */
	public void setCitiName(String citiName) {
		this.citiName = citiName;
	}

	/**
	 * @return the newAddedDocList
	 */
	public List<?> getNewAddedDocList() {
		return newAddedDocList;
	}

	/**
	 * @param newAddedDocList
	 *            the newAddedDocList to set
	 */
	public void setNewAddedDocList(List<?> newAddedDocList) {
		this.newAddedDocList = newAddedDocList;
	}

	/**
	 * @return the orgID
	 */
	public int getOrgID() {
		return orgID;
	}

	/**
	 * @param orgID
	 *            the orgID to set
	 */
	public void setOrgID(int orgID) {
		this.orgID = orgID;
	}

	/**
	 * @return the countryID
	 */
	public int getCountryID() {
		return countryID;
	}

	/**
	 * @param countryID
	 *            the countryID to set
	 */
	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}

	/**
	 * @return the submitDialogueRequestFlag
	 */
	public boolean isSubmitDialogueRequestFlag() {
		return submitDialogueRequestFlag;
	}

	/**
	 * @param submitDialogueRequestFlag
	 *            the submitDialogueRequestFlag to set
	 */
	public void setSubmitDialogueRequestFlag(boolean submitDialogueRequestFlag) {
		this.submitDialogueRequestFlag = submitDialogueRequestFlag;
	}

	/**
	 * @return the panCardDoc
	 */
	public String getPanCardDoc() {
		return panCardDoc;
	}

	/**
	 * @param panCardDoc
	 *            the panCardDoc to set
	 */
	public void setPanCardDoc(String panCardDoc) {
		this.panCardDoc = panCardDoc;
	}

	/**
	 * @return the addressProofDoc
	 */
	public String getAddressProofDoc() {
		return addressProofDoc;
	}

	/**
	 * @param addressProofDoc
	 *            the addressProofDoc to set
	 */
	public void setAddressProofDoc(String addressProofDoc) {
		this.addressProofDoc = addressProofDoc;
	}

	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * @param addressLine2
	 *            the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * @return the addressLine3
	 */
	public String getAddressLine3() {
		return addressLine3;
	}

	/**
	 * @param addressLine3
	 *            the addressLine3 to set
	 */
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	/**
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * @param pinCode
	 *            the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the inboxEmailID
	 */
	public String getInboxEmailID() {
		return inboxEmailID;
	}

	/**
	 * @param inboxEmailID
	 *            the inboxEmailID to set
	 */
	public void setInboxEmailID(String inboxEmailID) {
		this.inboxEmailID = inboxEmailID;
	}

	/**
	 * @return the industry
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * @param industry
	 *            the industry to set
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/**
	 * @return the entityType
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * @param entityType
	 *            the entityType to set
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	/**
	 * @return the employCount
	 */
	public int getEmployCount() {
		return employCount;
	}

	/**
	 * @param employCount
	 *            the employCount to set
	 */
	public void setEmployCount(int employCount) {
		this.employCount = employCount;
	}

	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * @param addressLine1
	 *            the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the legalEntityName
	 */
	public String getLegalEntityName() {
		return legalEntityName;
	}

	/**
	 * @param legalEntityName
	 *            the legalEntityName to set
	 */
	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}

	/**
	 * @return the cin
	 */
	public int getCin() {
		return cin;
	}

	/**
	 * @param cin
	 *            the cin to set
	 */
	public void setCin(int cin) {
		this.cin = cin;
	}

	/**
	 * @return the panNo
	 */
	public String getPanNo() {
		return panNo;
	}

	/**
	 * @param panNo
	 *            the panNo to set
	 */
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	/**
	 * @return the roleList
	 */
	public List<?> getRoleList() {
		return roleList;
	}

	/**
	 * @param roleList
	 *            the roleList to set
	 */
	public void setRoleList(List<?> roleList) {
		this.roleList = roleList;
	}

	/**
	 * @return the employeeNo
	 */
	public String getEmployeeNo() {
		return employeeNo;
	}

	/**
	 * @param employeeNo
	 *            the employeeNo to set
	 */
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	/**
	 * @return the addressFile
	 */
	public String getAddressFile() {
		return addressFile;
	}

	/**
	 * @param addressFile
	 *            the addressFile to set
	 */
	public void setAddressFile(String addressFile) {
		this.addressFile = addressFile;
	}

	/**
	 * @return the confirmPass
	 */
	public String getConfirmPass() {
		return confirmPass;
	}

	/**
	 * @param confirmPass
	 *            the confirmPass to set
	 */
	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the contectNo
	 */
	public String getContactNo() {
		return contactNo;
	}

	/**
	 * @param contectNo
	 *            the contectNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	/**
	 * @return the noVerification
	 */
	public String getNoVerification() {
		return noVerification;
	}

	/**
	 * @param noVerification
	 *            the noVerification to set
	 */
	public void setNoVerification(String noVerification) {
		this.noVerification = noVerification;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt
	 *            the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * @return the createdBy
	 */
	public int getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedBy
	 */
	public int getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate
	 *            the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin
	 *            the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * @return the loginTermsAndConditon
	 */
	public boolean isLoginTermsAndConditon() {
		return loginTermsAndConditon;
	}

	/**
	 * @param loginTermsAndConditon
	 *            the loginTermsAndConditon to set
	 */
	public void setLoginTermsAndConditon(boolean loginTermsAndConditon) {
		this.loginTermsAndConditon = loginTermsAndConditon;
	}

}
