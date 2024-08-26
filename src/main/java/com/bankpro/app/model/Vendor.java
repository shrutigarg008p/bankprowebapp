/**
 * 
 */
package com.bankpro.app.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Admin
 *
 */
@Entity
@Table(name = "pv_vendors")
@NamedQueries({
		@NamedQuery(name = Vendor.FETCH_VENDOR_OBJ, query = "from Vendor where vendorOrgID = :vendorOrgID and venBenCategorizeValue = :benCategorizeValue"),
		@NamedQuery(name = Vendor.VENDOR_OBJ_BASED_ON_VENDID, query = "from Vendor where id = :id"),
		@NamedQuery(name = Vendor.FETCH_VENDOR_OBJ_FOR_APPROVAL, query = "from Vendor where vendorOrgID = :orgId AND verified = 0 AND permissionFlag = 0 "),
		@NamedQuery(name = Vendor.Update_Permission_FLAG, query = "update Vendor set permissionFlag = :permissionFlag where id = :id"),
		@NamedQuery(name = Vendor.SEARCH_BY_NAME_ForEmployee, query = "from Vendor where vendorOrgID = :vendorOrgID and venBenCategorizeValue = :benCategorizeValue and supplierName like  :supplierName" )
		})
public class Vendor {
	public static final String FETCH_VENDOR_OBJ = "Vendor.FETCH_VENDOR_OBJ";
	public static final String VENDOR_OBJ_BASED_ON_VENDID = "Vendor.VENDOR_OBJ_BASED_ON_VENDID";
	public static final String FETCH_VENDOR_OBJ_FOR_APPROVAL = "Vendor.FETCH_VENDOR_OBJ_FOR_APPROVAL";
	public static final String Update_Permission_FLAG = "Vendor.Update_Permission_FLAG";
	public static final String SEARCH_BY_NAME_ForEmployee = "Employee.SEARCH_BY_NAME";
	
	@Id
	@GeneratedValue
	@Column(name = "vendor_id")
	private long id;

	@Column(name = "supplier_name")
	private String supplierName;

	@Column(name = "supplier_number")
	private String supplierNumber;

	@Column(name = "payment_name")
	private String paymentName;

	@Column(name = "pan_number")
	private String pan_number;

	@Column(name = "inactive_date")
	private Date inactiveDate;

	@Column(name = "address_line1")
	private String addressLine1;

	@Column(name = "address_line2")
	private String addressLine2;

	@Column(name = "address_line3")
	private String addressLine3;

	@Column(name = "pin_code")
	private String pinCode;

	@Column(name = "vendor_type")
	private String vendorType;

	@Column(name = "company_type")
	private String companyType;

	@Column(name = "city_name")
	private String citiName;
	
	@Column(name = "state_id")
	private String stateId;	
	
	@Column(name = "country_name")
	 private String countryName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bank_id")
	private NewBankDetails newBankID;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private Users userId;

	@Column(name = "address_name")
	private String addressName;

	@Column(name = "primary_email_id")
	private String primaryEmailId;

	@Column(name = "phone_number")
	private String phoneNo;

	@Column(name = "fax_number")
	private String faxNo;

	@Column(name = "payment_method")
	private String paymentMethod;

	@Column(name = "payment_terms")
	private String paymentTerms;

	@Column(name = "verified")
	private boolean verified;

	@Column(name = "currency")
	private String currency;

	@Column(name = "mmid")
	private String MID;

	@Column(name = "permission_flag")
	private boolean permissionFlag;

	@Column(name = "pan_card_doc")
	private String panCardDoc;

	@Column(name = "address_Proof_doc")
	private String addressProofDoc;

	@Column(name = "cancel_cheque_doc")
	private String cencelChequeDoc;

	@Column(name = "companies_doc")
	private String companiesDoc;

	@Column(name = "created_by")
	private int createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "updated_by")
	private int updatedBy;

	@Column(name = "updated_date")
	private Date updateDate;

	@Column(name = "vend_OrgID")
	private int vendorOrgID;
	
	@Column(name = "ven_employeeNo")
	private String empNumber;
	
	@Column(name = "ven_BeneficiaryCategorizeFlag")
	private int venBenCategorizeValue;

	
	

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
	 * @return the empNumber
	 */
	public String getEmpNumber() {
		return empNumber;
	}

	/**
	 * @param empNumber the empNumber to set
	 */
	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}

	/**
	 * @return the venBenCategorizeValue
	 */
	public int getVenBenCategorizeValue() {
		return venBenCategorizeValue;
	}

	/**
	 * @param venBenCategorizeValue the venBenCategorizeValue to set
	 */
	public void setVenBenCategorizeValue(int venBenCategorizeValue) {
		this.venBenCategorizeValue = venBenCategorizeValue;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the vendorOrgID
	 */
	public int getVendorOrgID() {
		return vendorOrgID;
	}

	/**
	 * @param vendorOrgID
	 *            the vendorOrgID to set
	 */
	public void setVendorOrgID(int vendorOrgID) {
		this.vendorOrgID = vendorOrgID;
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

	// /**
	// * @return the vendorType
	// */
	// public VendorTypeMaster getVendorType() {
	// return vendorType;
	// }
	//
	// /**
	// * @param vendorType
	// * the vendorType to set
	// */
	// public void setVendorType(VendorTypeMaster vendorType) {
	// this.vendorType = vendorType;
	// }
	//
	// /**
	// * @return the companyType
	// */
	// public CompanyTypeMaster getCompanyType() {
	// return companyType;
	// }
	//
	// /**
	// * @param companyType
	// * the companyType to set
	// */
	// public void setCompanyType(CompanyTypeMaster companyType) {
	// this.companyType = companyType;
	// }
	//
	// /**
	// * @return the citiId
	// */
	// public CitiMaster getCitiId() {
	// return citiId;
	// }
	//
	// /**
	// * @param citiId
	// * the citiId to set
	// */
	// public void setCitiId(CitiMaster citiId) {
	// this.citiId = citiId;
	// }
	//
	// /**
	// * @return the stateId
	// */
	// public StateMaster getStateId() {
	// return stateId;
	// }
	//
	// /**
	// * @param stateId
	// * the stateId to set
	// */
	// public void setStateId(StateMaster stateId) {
	// this.stateId = stateId;
	// }
	//
	// /**
	// * @return the countryId
	// */
	// public CountryMaster getCountryId() {
	// return countryId;
	// }
	//
	// /**
	// * @param countryId
	// * the countryId to set
	// */
	// public void setCountryId(CountryMaster countryId) {
	// this.countryId = countryId;
	// }

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public Users getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Users userId) {
		this.userId = userId;
	}

	/**
	 * @return the supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * @param supplierName
	 *            the supplierName to set
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * @return the supplierNumber
	 */
	public String getSupplierNumber() {
		return supplierNumber;
	}

	/**
	 * @param supplierNumber
	 *            the supplierNumber to set
	 */
	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
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
	 * @return the pan_number
	 */
	public String getPan_number() {
		return pan_number;
	}

	/**
	 * @param pan_number
	 *            the pan_number to set
	 */
	public void setPan_number(String pan_number) {
		this.pan_number = pan_number;
	}

	/**
	 * @return the inactiveDate
	 */
	public Date getInactiveDate() {
		return inactiveDate;
	}

	/**
	 * @param inactiveDate
	 *            the inactiveDate to set
	 */
	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
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
	 * @return the primaryEmailId
	 */
	public String getPrimaryEmailId() {
		return primaryEmailId;
	}

	/**
	 * @param primaryEmailId
	 *            the primaryEmailId to set
	 */
	public void setPrimaryEmailId(String primaryEmailId) {
		this.primaryEmailId = primaryEmailId;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo
	 *            the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @return the faxNo
	 */
	public String getFaxNo() {
		return faxNo;
	}

	/**
	 * @param faxNo
	 *            the faxNo to set
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
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
	 * @return the paymentTerms
	 */
	public String getPaymentTerms() {
		return paymentTerms;
	}

	/**
	 * @param paymentTerms
	 *            the paymentTerms to set
	 */
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	/**
	 * @return the newBankID
	 */
	public NewBankDetails getNewBankID() {
		return newBankID;
	}

	/**
	 * @param newBankID
	 *            the newBankID to set
	 */
	public void setNewBankID(NewBankDetails newBankID) {
		this.newBankID = newBankID;
	}

	/**
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @param verified
	 *            the verified to set
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
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
	public String getMID() {
		return MID;
	}

	/**
	 * @param mID
	 *            the mID to set
	 */
	public void setMID(String mID) {
		MID = mID;
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
	 * @return the cencelChequeDoc
	 */
	public String getCencelChequeDoc() {
		return cencelChequeDoc;
	}

	/**
	 * @param cencelChequeDoc
	 *            the cencelChequeDoc to set
	 */
	public void setCencelChequeDoc(String cencelChequeDoc) {
		this.cencelChequeDoc = cencelChequeDoc;
	}

	/**
	 * @return the companiesDoc
	 */
	public String getCompaniesDoc() {
		return companiesDoc;
	}

	/**
	 * @param companiesDoc
	 *            the companiesDoc to set
	 */
	public void setCompaniesDoc(String companiesDoc) {
		this.companiesDoc = companiesDoc;
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
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
