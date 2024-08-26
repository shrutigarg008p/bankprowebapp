package com.bankpro.app.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.Vendor;

public class VendorInfoDTO {
	private long vendID;
	private String suplierName;
	private String suplierNo;
	private String paymentName;
	private String panNumber;
	private String vendorType;
	private String companyType;
	private String paymentMethod;
	private String paymentTerm;
	private String bankName;
	private String branchAddress;
	private String accountNo;
	private String accName;
	private String currency;
	private String mID;
	private String phnNo;
	private String addressName;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String firstName;
	private String lastName;
	private String primEmailId;
	private String email;
	private String pinCode;
	private String faxNumber;
	private boolean verifiedStatus;
	private String citiName;
	private String ifscCode;
	private boolean permissionFlag;
	private String panCardDoc;
	private String addressProof;
	private String cancelCheque;
	private String venEmployeeNo;
	private String countryID;
	private String stateId;
	private List<?> newAddedDocListInVendor = new ArrayList<>();

	public VendorInfoDTO() {
	}

	public VendorInfoDTO(Vendor vendObj) {
		this.vendID = vendObj.getId();
		this.firstName = vendObj.getUserId().getUserFirstName();
		this.lastName = vendObj.getUserId().getUserLastName();
		this.accountNo = vendObj.getNewBankID().getBankAccountNo();
		this.citiName = vendObj.getCitiName();
		this.primEmailId = vendObj.getPrimaryEmailId();
		this.verifiedStatus = vendObj.isVerified();
		this.phnNo = vendObj.getPhoneNo();
		this.email = vendObj.getUserId().getUserEmail();
		this.suplierName = vendObj.getSupplierName();
		this.suplierNo = vendObj.getSupplierNumber();
		this.paymentName = vendObj.getPaymentName();
		this.panNumber = vendObj.getPan_number();
		this.vendorType = vendObj.getVendorType();
		this.companyType = vendObj.getCompanyType();
		this.paymentMethod = vendObj.getPaymentMethod();
		this.paymentTerm = vendObj.getPaymentTerms();
		this.bankName = vendObj.getNewBankID().getBankName();
		this.branchAddress = vendObj.getNewBankID().getBankBranchAddress();
		this.accName = vendObj.getNewBankID().getBankAccountHolderName();
		this.currency = vendObj.getCurrency();
		this.ifscCode = vendObj.getNewBankID().getBankIFSCCode();
		this.mID = vendObj.getMID();
		this.addressLine1 = vendObj.getAddressLine1();
		this.addressLine2 = vendObj.getAddressLine2();
		this.addressLine3 = vendObj.getAddressLine3();
		this.pinCode = vendObj.getPinCode();
		this.faxNumber = vendObj.getFaxNo();
		this.permissionFlag = vendObj.isPermissionFlag();
		this.addressProof = vendObj.getAddressProofDoc();
		this.panCardDoc = vendObj.getPanCardDoc();
		this.cancelCheque = vendObj.getCencelChequeDoc();
		this.addressName = vendObj.getAddressName();
		this.venEmployeeNo = vendObj.getEmpNumber();
	}

	public static VendorInfoDTO mapVendorEntity(Vendor venObj) {
		return new VendorInfoDTO(venObj);
	}

	public static List<VendorInfoDTO> mapVendorList(List<Vendor> vendObj) {
		return vendObj.stream().map((vendObjy) -> mapVendorEntity(vendObjy))
				.collect(Collectors.toList());
	}

	
	
	/**
	 * @return the countryID
	 */
	public String getCountryID() {
		return countryID;
	}

	/**
	 * @param countryID the countryID to set
	 */
	public void setCountryID(String countryID) {
		this.countryID = countryID;
	}

	/**
	 * @return the stateId
	 */
	public String getStateId() {
		return stateId;
	}

	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the venEmployeeNo
	 */
	public String getVenEmployeeNo() {
		return venEmployeeNo;
	}

	/**
	 * @param venEmployeeNo
	 *            the venEmployeeNo to set
	 */
	public void setVenEmployeeNo(String venEmployeeNo) {
		this.venEmployeeNo = venEmployeeNo;
	}

	/**
	 * @return the newAddedDocListInVendor
	 */
	public List<?> getNewAddedDocListInVendor() {
		return newAddedDocListInVendor;
	}

	/**
	 * @param newAddedDocListInVendor
	 *            the newAddedDocListInVendor to set
	 */
	public void setNewAddedDocListInVendor(List<?> newAddedDocListInVendor) {
		this.newAddedDocListInVendor = newAddedDocListInVendor;
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
	 * @return the addressProof
	 */
	public String getAddressProof() {
		return addressProof;
	}

	/**
	 * @param addressProof
	 *            the addressProof to set
	 */
	public void setAddressProof(String addressProof) {
		this.addressProof = addressProof;
	}

	/**
	 * @return the cancelCheque
	 */
	public String getCancelCheque() {
		return cancelCheque;
	}

	/**
	 * @param cancelCheque
	 *            the cancelCheque to set
	 */
	public void setCancelCheque(String cancelCheque) {
		this.cancelCheque = cancelCheque;
	}

	/**
	 * @return the vendID
	 */
	public long getVendID() {
		return vendID;
	}

	/**
	 * @param vendID
	 *            the vendID to set
	 */
	public void setVendID(long vendID) {
		this.vendID = vendID;
	}

	/**
	 * @return the permissionFlag
	 */
	public boolean isPermissionFlag() {
		return permissionFlag;
	}

	/**
	 * @param permissionFlag
	 *            the permissionFlag to set
	 */
	public void setPermissionFlag(boolean permissionFlag) {
		this.permissionFlag = permissionFlag;
	}

	/**
	 * @return the ifscCode
	 */
	public String getIfscCode() {
		return ifscCode;
	}

	/**
	 * @param ifscCode
	 *            the ifscCode to set
	 */
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	/**
	 * @return the citiName
	 */
	public String getCitiName() {
		return citiName;
	}

	/**
	 * @param citiName
	 *            the citiName to set
	 */
	public void setCitiName(String citiName) {
		this.citiName = citiName;
	}

	/**
	 * @return the verifiedStatus
	 */
	public boolean isVerifiedStatus() {
		return verifiedStatus;
	}

	/**
	 * @param verifiedStatus
	 *            the verifiedStatus to set
	 */
	public void setVerifiedStatus(boolean verifiedStatus) {
		this.verifiedStatus = verifiedStatus;
	}

	/**
	 * @return the phnNo
	 */
	public String getPhnNo() {
		return phnNo;
	}

	/**
	 * @param phnNo
	 *            the phnNo to set
	 */
	public void setPhnNo(String phnNo) {
		this.phnNo = phnNo;
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
	 * @return the primEmailId
	 */
	public String getPrimEmailId() {
		return primEmailId;
	}

	/**
	 * @param primEmailId
	 *            the primEmailId to set
	 */
	public void setPrimEmailId(String primEmailId) {
		this.primEmailId = primEmailId;
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
	 * @return the faxNumber
	 */
	public String getFaxNumber() {
		return faxNumber;
	}

	/**
	 * @param faxNumber
	 *            the faxNumber to set
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * @return the suplierName
	 */
	public String getSuplierName() {
		return suplierName;
	}

	/**
	 * @param suplierName
	 *            the suplierName to set
	 */
	public void setSuplierName(String suplierName) {
		this.suplierName = suplierName;
	}

	/**
	 * @return the suplierNo
	 */
	public String getSuplierNo() {
		return suplierNo;
	}

	/**
	 * @param suplierNo
	 *            the suplierNo to set
	 */
	public void setSuplierNo(String suplierNo) {
		this.suplierNo = suplierNo;
	}

	/**
	 * @return the paymentName
	 */
	public String getPaymentName() {
		return paymentName;
	}

	/**
	 * @param paymentName
	 *            the paymentName to set
	 */
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	/**
	 * @return the panNumber
	 */
	public String getPanNumber() {
		return panNumber;
	}

	/**
	 * @param panNumber
	 *            the panNumber to set
	 */
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	/**
	 * @return the vendorType
	 */
	public String getVendorType() {
		return vendorType;
	}

	/**
	 * @param vendorType
	 *            the vendorType to set
	 */
	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}

	/**
	 * @return the companyType
	 */
	public String getCompanyType() {
		return companyType;
	}

	/**
	 * @param companyType
	 *            the companyType to set
	 */
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod
	 *            the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the paymentTerm
	 */
	public String getPaymentTerm() {
		return paymentTerm;
	}

	/**
	 * @param paymentTerm
	 *            the paymentTerm to set
	 */
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the branchAddress
	 */
	public String getBranchAddress() {
		return branchAddress;
	}

	/**
	 * @param branchAddress
	 *            the branchAddress to set
	 */
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo
	 *            the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return the accName
	 */
	public String getAccName() {
		return accName;
	}

	/**
	 * @param accName
	 *            the accName to set
	 */
	public void setAccName(String accName) {
		this.accName = accName;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the mID
	 */
	public String getmID() {
		return mID;
	}

	/**
	 * @param mID
	 *            the mID to set
	 */
	public void setmID(String mID) {
		this.mID = mID;
	}

	/**
	 * @return the addressName
	 */
	public String getAddressName() {
		return addressName;
	}

	/**
	 * @param addressName
	 *            the addressName to set
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
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

}
