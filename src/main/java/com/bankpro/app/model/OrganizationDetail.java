/**
 * 
 */
package com.bankpro.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Admin
 *
 */
@Entity
@Table(name = "pv_organization")
@NamedQueries({
		@NamedQuery(name = OrganizationDetail.FIND_FOR_ORG_NAME, query = "select org.orgName from "
				+ "OrganizationDetail org where orgName = :orgName"),
		@NamedQuery(name = OrganizationDetail.FIND_ORG_DETAIL_BY_ID, query = "select org.orgID, org.orgName from OrganizationDetail org where orgID = :orgId "),
		@NamedQuery(name = OrganizationDetail.FIND_ORG_DETAIL_OBJ_BY_ID, query = "from OrganizationDetail where orgID = :orgId") })
public class OrganizationDetail {
	public static final String FIND_FOR_ORG_NAME = "OrganizationDetail.findForOrgName";
	public static final String FIND_ORG_DETAIL_BY_ID = "OrganizationDetail.FIND_ORG_DETAIL_BY_ID";
	public static final String FIND_ORG_DETAIL_OBJ_BY_ID = "OrganizationDetail.FIND_ORG_DETAIL_OBJ_BY_ID";

	@Id
	@GeneratedValue
	@Column(name = "org_id")
	private int orgID;

	@Column(name = "org_legalEntityName")
	private String orgLegalEntityName;

	@Column(name = "org_Name")
	private String orgName;

	@Column(name = "org_cinNumber")
	private int orgCINNumber;

	@Column(name = "org_panNumber")
	private String orgPanNumber;

	@Column(name = "org_address_line1")
	private String orgAddressLine1;

	@Column(name = "org_address_line2")
	private String orgAddressLine2;

	@Column(name = "org_address_line3")
	private String orgAddressLine3;

	@Column(name = "org_pinCode")
	private String orgPinCode;

	@Column(name = "org_citiName")
	private String orgCitiName;

	@Column(name = "org_stateNameID")
	private int orgStateNameId;

	@Column(name = "org_countryId")
	private int orgCountryId;

	@Column(name = "org_url")
	private String orgURL;

	@Column(name = "org_phoneNo")
	private String orgPhoneNumber;

	// @Column(name="org_legalEntityName")
	// private

	@Column(name = "org_employeeCount")
	private int orgEmployeCount;

	@Column(name = "org_entityType")
	private String orgEntityType;

	@Column(name = "org_inboxEmail")
	private String orgInboxEmail;

	@Column(name = "org_industry")
	private String orgIndustry;

	@Column(name = "org_panCardDoc")
	private String orgPanCardDoc;

	@Column(name = "org_addressProofDoc")
	private String orgAddressProofDoc;

	@Column(name = "org_serviceTaxDoc")
	private String orgServiceTaxDoc;

	@Column(name = "org_tanDoc")
	private String orgTanDoc;

	@Column(name = "org_vatRegistrationDoc")
	private String orgVatRegistrationDoc;

	@Column(name = "org_companyMouDoc")
	private String orgCompnanyMouDoc;

	@Column(name = "org_pfRegistration_Doc")
	private String orgPFRegistrationDoc;

	@Column(name = "org_exciseRegistrationDoc")
	private String orgExciseRegistrationDoc;

	@Column(name = "org_labourLicenseNumber")
	private String orgLabourLicenseNumber;

	@Column(name = "org_contactPersonName")
	private String orgContactPersonName;

	@Column(name = "org_contactPersonEmail")
	private String orgContactPersonEmail;

	@Column(name = "org_contactPersonPhoneNumber")
	private String orgContactPersonPhoneNo;

	@Column(name = "org_createdBy")
	private int orgCreatedBy;

	@Column(name = "org_createdDate")
	private Date orgCreatedDate = new Date();

	@Column(name = "org_updatedBy")
	private int orgUpdatedBy;

	@Column(name = "org_updatedDate")
	private Date orgUpdatedDate;

	/**
	 * @return the orgStateNameId
	 */
	public int getOrgStateNameId() {
		return orgStateNameId;
	}

	/**
	 * @param orgStateNameId
	 *            the orgStateNameId to set
	 */
	public void setOrgStateNameId(int orgStateNameId) {
		this.orgStateNameId = orgStateNameId;
	}

	/**
	 * @return the orgCitiName
	 */
	public String getOrgCitiName() {
		return orgCitiName;
	}

	/**
	 * @param orgCitiName
	 *            the orgCitiName to set
	 */
	public void setOrgCitiName(String orgCitiName) {
		this.orgCitiName = orgCitiName;
	}

	public OrganizationDetail(String orgName) {
		this.orgName = orgName;
	}

	public OrganizationDetail() {

	}

	/**
	 * @return the orgInboxEmail
	 */
	public String getOrgInboxEmail() {
		return orgInboxEmail;
	}

	/**
	 * @param orgInboxEmail
	 *            the orgInboxEmail to set
	 */
	public void setOrgInboxEmail(String orgInboxEmail) {
		this.orgInboxEmail = orgInboxEmail;
	}

	/**
	 * @return the orgIndustry
	 */
	public String getOrgIndustry() {
		return orgIndustry;
	}

	/**
	 * @param orgIndustry
	 *            the orgIndustry to set
	 */
	public void setOrgIndustry(String orgIndustry) {
		this.orgIndustry = orgIndustry;
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
	 * @return the orgLegalEntityName
	 */
	public String getOrgLegalEntityName() {
		return orgLegalEntityName;
	}

	/**
	 * @param orgLegalEntityName
	 *            the orgLegalEntityName to set
	 */
	public void setOrgLegalEntityName(String orgLegalEntityName) {
		this.orgLegalEntityName = orgLegalEntityName;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 *            the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the orgCINNumber
	 */
	public int getOrgCINNumber() {
		return orgCINNumber;
	}

	/**
	 * @param orgCINNumber
	 *            the orgCINNumber to set
	 */
	public void setOrgCINNumber(int orgCINNumber) {
		this.orgCINNumber = orgCINNumber;
	}

	/**
	 * @return the orgPanNumber
	 */
	public String getOrgPanNumber() {
		return orgPanNumber;
	}

	/**
	 * @param orgPanNumber
	 *            the orgPanNumber to set
	 */
	public void setOrgPanNumber(String orgPanNumber) {
		this.orgPanNumber = orgPanNumber;
	}

	/**
	 * @return the orgAddressLine1
	 */
	public String getOrgAddressLine1() {
		return orgAddressLine1;
	}

	/**
	 * @param orgAddressLine1
	 *            the orgAddressLine1 to set
	 */
	public void setOrgAddressLine1(String orgAddressLine1) {
		this.orgAddressLine1 = orgAddressLine1;
	}

	/**
	 * @return the orgAddressLine2
	 */
	public String getOrgAddressLine2() {
		return orgAddressLine2;
	}

	/**
	 * @param orgAddressLine2
	 *            the orgAddressLine2 to set
	 */
	public void setOrgAddressLine2(String orgAddressLine2) {
		this.orgAddressLine2 = orgAddressLine2;
	}

	/**
	 * @return the orgAddressLine3
	 */
	public String getOrgAddressLine3() {
		return orgAddressLine3;
	}

	/**
	 * @param orgAddressLine3
	 *            the orgAddressLine3 to set
	 */
	public void setOrgAddressLine3(String orgAddressLine3) {
		this.orgAddressLine3 = orgAddressLine3;
	}

	// /**
	// * @return the orgCitiId
	// */
	// public CitiMaster getOrgCitiId() {
	// return orgCitiId;
	// }
	//
	// /**
	// * @param orgCitiId
	// * the orgCitiId to set
	// */
	// public void setOrgCitiId(CitiMaster orgCitiId) {
	// this.orgCitiId = orgCitiId;
	// }
	//
	// /**
	// * @return the orgStateId
	// */
	// public StateMaster getOrgStateId() {
	// return orgStateId;
	// }
	//
	// /**
	// * @param orgStateId
	// * the orgStateId to set
	// */
	// public void setOrgStateId(StateMaster orgStateId) {
	// this.orgStateId = orgStateId;
	// }
	//
	// /**
	// * @return the orgCountryId
	// */
	// public CountryMaster getOrgCountryId() {
	// return orgCountryId;
	// }
	//
	// /**
	// * @param orgCountryId
	// * the orgCountryId to set
	// */
	// public void setOrgCountryId(CountryMaster orgCountryId) {
	// this.orgCountryId = orgCountryId;
	// }

	/**
	 * @return the orgPinCode
	 */
	public String getOrgPinCode() {
		return orgPinCode;
	}

	/**
	 * @return the orgCountryId
	 */
	public int getOrgCountryId() {
		return orgCountryId;
	}

	/**
	 * @param orgCountryId
	 *            the orgCountryId to set
	 */
	public void setOrgCountryId(int orgCountryId) {
		this.orgCountryId = orgCountryId;
	}

	/**
	 * @param orgPinCode
	 *            the orgPinCode to set
	 */
	public void setOrgPinCode(String orgPinCode) {
		this.orgPinCode = orgPinCode;
	}

	/**
	 * @return the orgURL
	 */
	public String getOrgURL() {
		return orgURL;
	}

	/**
	 * @param orgURL
	 *            the orgURL to set
	 */
	public void setOrgURL(String orgURL) {
		this.orgURL = orgURL;
	}

	/**
	 * @return the orgPhoneNumber
	 */
	public String getOrgPhoneNumber() {
		return orgPhoneNumber;
	}

	/**
	 * @param orgPhoneNumber
	 *            the orgPhoneNumber to set
	 */
	public void setOrgPhoneNumber(String orgPhoneNumber) {
		this.orgPhoneNumber = orgPhoneNumber;
	}

	/**
	 * @return the orgEmployeCount
	 */
	public int getOrgEmployeCount() {
		return orgEmployeCount;
	}

	/**
	 * @param orgEmployeCount
	 *            the orgEmployeCount to set
	 */
	public void setOrgEmployeCount(int orgEmployeCount) {
		this.orgEmployeCount = orgEmployeCount;
	}

	/**
	 * @return the orgEntityType
	 */
	public String getOrgEntityType() {
		return orgEntityType;
	}

	/**
	 * @param orgEntityType
	 *            the orgEntityType to set
	 */
	public void setOrgEntityType(String orgEntityType) {
		this.orgEntityType = orgEntityType;
	}

	/**
	 * @return the orgPanCardDoc
	 */
	public String getOrgPanCardDoc() {
		return orgPanCardDoc;
	}

	/**
	 * @param orgPanCardDoc
	 *            the orgPanCardDoc to set
	 */
	public void setOrgPanCardDoc(String orgPanCardDoc) {
		this.orgPanCardDoc = orgPanCardDoc;
	}

	/**
	 * @return the orgAddressProofDoc
	 */
	public String getOrgAddressProofDoc() {
		return orgAddressProofDoc;
	}

	/**
	 * @param orgAddressProofDoc
	 *            the orgAddressProofDoc to set
	 */
	public void setOrgAddressProofDoc(String orgAddressProofDoc) {
		this.orgAddressProofDoc = orgAddressProofDoc;
	}

	/**
	 * @return the orgServiceTaxDoc
	 */
	public String getOrgServiceTaxDoc() {
		return orgServiceTaxDoc;
	}

	/**
	 * @param orgServiceTaxDoc
	 *            the orgServiceTaxDoc to set
	 */
	public void setOrgServiceTaxDoc(String orgServiceTaxDoc) {
		this.orgServiceTaxDoc = orgServiceTaxDoc;
	}

	/**
	 * @return the orgTanDoc
	 */
	public String getOrgTanDoc() {
		return orgTanDoc;
	}

	/**
	 * @param orgTanDoc
	 *            the orgTanDoc to set
	 */
	public void setOrgTanDoc(String orgTanDoc) {
		this.orgTanDoc = orgTanDoc;
	}

	/**
	 * @return the orgVatRegistrationDoc
	 */
	public String getOrgVatRegistrationDoc() {
		return orgVatRegistrationDoc;
	}

	/**
	 * @param orgVatRegistrationDoc
	 *            the orgVatRegistrationDoc to set
	 */
	public void setOrgVatRegistrationDoc(String orgVatRegistrationDoc) {
		this.orgVatRegistrationDoc = orgVatRegistrationDoc;
	}

	/**
	 * @return the orgCompnanyMouDoc
	 */
	public String getOrgCompnanyMouDoc() {
		return orgCompnanyMouDoc;
	}

	/**
	 * @param orgCompnanyMouDoc
	 *            the orgCompnanyMouDoc to set
	 */
	public void setOrgCompnanyMouDoc(String orgCompnanyMouDoc) {
		this.orgCompnanyMouDoc = orgCompnanyMouDoc;
	}

	/**
	 * @return the orgPFRegistrationDoc
	 */
	public String getOrgPFRegistrationDoc() {
		return orgPFRegistrationDoc;
	}

	/**
	 * @param orgPFRegistrationDoc
	 *            the orgPFRegistrationDoc to set
	 */
	public void setOrgPFRegistrationDoc(String orgPFRegistrationDoc) {
		this.orgPFRegistrationDoc = orgPFRegistrationDoc;
	}

	/**
	 * @return the orgExciseRegistrationDoc
	 */
	public String getOrgExciseRegistrationDoc() {
		return orgExciseRegistrationDoc;
	}

	/**
	 * @param orgExciseRegistrationDoc
	 *            the orgExciseRegistrationDoc to set
	 */
	public void setOrgExciseRegistrationDoc(String orgExciseRegistrationDoc) {
		this.orgExciseRegistrationDoc = orgExciseRegistrationDoc;
	}

	/**
	 * @return the orgLabourLicenseNumber
	 */
	public String getOrgLabourLicenseNumber() {
		return orgLabourLicenseNumber;
	}

	/**
	 * @param orgLabourLicenseNumber
	 *            the orgLabourLicenseNumber to set
	 */
	public void setOrgLabourLicenseNumber(String orgLabourLicenseNumber) {
		this.orgLabourLicenseNumber = orgLabourLicenseNumber;
	}

	/**
	 * @return the orgContactPersonName
	 */
	public String getOrgContactPersonName() {
		return orgContactPersonName;
	}

	/**
	 * @param orgContactPersonName
	 *            the orgContactPersonName to set
	 */
	public void setOrgContactPersonName(String orgContactPersonName) {
		this.orgContactPersonName = orgContactPersonName;
	}

	/**
	 * @return the orgContactPersonEmail
	 */
	public String getOrgContactPersonEmail() {
		return orgContactPersonEmail;
	}

	/**
	 * @param orgContactPersonEmail
	 *            the orgContactPersonEmail to set
	 */
	public void setOrgContactPersonEmail(String orgContactPersonEmail) {
		this.orgContactPersonEmail = orgContactPersonEmail;
	}

	/**
	 * @return the orgContactPersonPhoneNo
	 */
	public String getOrgContactPersonPhoneNo() {
		return orgContactPersonPhoneNo;
	}

	/**
	 * @param orgContactPersonPhoneNo
	 *            the orgContactPersonPhoneNo to set
	 */
	public void setOrgContactPersonPhoneNo(String orgContactPersonPhoneNo) {
		this.orgContactPersonPhoneNo = orgContactPersonPhoneNo;
	}

	/**
	 * @return the orgCreatedBy
	 */
	public int getOrgCreatedBy() {
		return orgCreatedBy;
	}

	/**
	 * @param orgCreatedBy
	 *            the orgCreatedBy to set
	 */
	public void setOrgCreatedBy(int orgCreatedBy) {
		this.orgCreatedBy = orgCreatedBy;
	}

	/**
	 * @return the orgCreatedDate
	 */
	public Date getOrgCreatedDate() {
		return orgCreatedDate;
	}

	/**
	 * @param orgCreatedDate
	 *            the orgCreatedDate to set
	 */
	public void setOrgCreatedDate(Date orgCreatedDate) {
		this.orgCreatedDate = orgCreatedDate;
	}

	/**
	 * @return the orgUpdatedBy
	 */
	public int getOrgUpdatedBy() {
		return orgUpdatedBy;
	}

	/**
	 * @param orgUpdatedBy
	 *            the orgUpdatedBy to set
	 */
	public void setOrgUpdatedBy(int orgUpdatedBy) {
		this.orgUpdatedBy = orgUpdatedBy;
	}

	/**
	 * @return the orgUpdatedDate
	 */
	public Date getOrgUpdatedDate() {
		return orgUpdatedDate;
	}

	/**
	 * @param orgUpdatedDate
	 *            the orgUpdatedDate to set
	 */
	public void setOrgUpdatedDate(Date orgUpdatedDate) {
		this.orgUpdatedDate = orgUpdatedDate;
	}

}
