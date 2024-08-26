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
@Table(name = "pv_bank_details")
@NamedQueries({
		@NamedQuery(name = NewBankDetails.bankDetail, query = "from NewBankDetails where bankOrgId = :bankOrgId"),
		@NamedQuery(name = NewBankDetails.find_WithBankID, query = "from NewBankDetails where bankId = :bankId"),
		@NamedQuery(name = NewBankDetails.update_BankStatus, query = "update NewBankDetails set bankActive = :bankActive  where bankId = :bankId"),
		@NamedQuery(name = NewBankDetails.find_accountNo_Based_On_ORG, query = "select bankId, bankAccountNo from NewBankDetails where bankOrgId = :bankOrgId")})
public class NewBankDetails {
	public static final String bankDetail = "NewBankDetails.detail";
	public static final String find_WithBankID = "NewBankDetails.bankID";
	public static final String update_BankStatus = "NewBankDetails.bankActive";
	public static final String find_accountNo_Based_On_ORG = "NewBankDetails.find_accountNo_Based_On_ORG";

	@Id
	@GeneratedValue
	@Column(name = "bank_id")
	private Long bankId;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "country_id")
	private int bankCountryId;

	@Column(name = "account_holder_name")
	private String bankAccountHolderName;

	@Column(name = "verification_status")
	private boolean bankVerificationStatus;

	@Column(name = "account_number")
	private String bankAccountNo;

	@Column(name = "bank_active")
	private boolean bankActive;

	@Column(name = "branch_address")
	private String bankBranchAddress;

	@Column(name = "ifsc_code")
	private String bankIFSCCode;

	@Column(name = "micr_number")
	private String bankMICRNo;

	@Column(name = "iban_number")
	private String bankIBANNo;

	@Column(name = "electronic_collection_amount")
	private double bankElecroCollectionAmt;

	@Column(name = "amount_limit")
	private double bankAmountLimit;

	@Column(name = "available_ap")
	private boolean bankAvailableAP;

	@Column(name = "default_ap")
	private boolean bankDefaultAP;

	@Column(name = "available_ar")
	private boolean bankAvailableAR;

	@Column(name = "agree_payment_term_service")
	private boolean bankAgreePaymentTermService;

	@Column(name = "transactional_password")
	private String bankTransactionPass;

	@Column(name = "otp_verification")
	private boolean bankOTPVerification;

	@Column(name = "debit_mandate_doc")
	private String bankDebitMandateDoc;

	@Column(name = "debit_mandate_verify_status")
	private boolean bankMandateVerifyStatus;

	@Column(name = "board_resolution_doc")
	private String bankBoardResolutionDoc;

	@Column(name = "board_resolutionverify_status")
	private boolean bankBoardResolutionStatus;

	@Column(name = "pan_card_doc")
	private String bankPanCardDoc;

	@Column(name = "pan_card_verify_status")
	private boolean bankPanCardVerifyStatus;

	@Column(name = "address_proof_doc")
	private String bankAddressProofDoc;

	@Column(name = "address_proof_verify_status")
	private String bankAddressProofVerifyStatus;

	@Column(name = "cancelled_cheque_doc")
	private String bankCancellChequeDoc;

	@Column(name = "cancelled_cheque_status")
	private boolean bankCancellChequeStatus;

	@Column(name = "created_by")
	private int bankCreatedBy;

	@Column(name = "created_date")
	private Date bankCreatedDate;

	@Column(name = "updated_by")
	private int bankUpdatedBy;

	@Column(name = "updated_date")
	private Date bankUpdateDate;

	@Column(name = "bank_org_id")
	private int bankOrgId;

	/**
	 * @return the bankOrgId
	 */
	public int getBankOrgId() {
		return bankOrgId;
	}

	/**
	 * @param bankOrgId
	 *            the bankOrgId to set
	 */
	public void setBankOrgId(int bankOrgId) {
		this.bankOrgId = bankOrgId;
	}

	/**
	 * @return the bankCountryId
	 */
	public int getBankCountryId() {
		return bankCountryId;
	}

	/**
	 * @param bankCountryId
	 *            the bankCountryId to set
	 */
	public void setBankCountryId(int bankCountryId) {
		this.bankCountryId = bankCountryId;
	}

	/**
	 * @return the bankId
	 */
	public Long getBankId() {
		return bankId;
	}

	/**
	 * @param bankId
	 *            the bankId to set
	 */
	public void setBankId(Long bankId) {
		this.bankId = bankId;
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
	 * @return the bankAccountHolderName
	 */
	public String getBankAccountHolderName() {
		return bankAccountHolderName;
	}

	/**
	 * @param bankAccountHolderName
	 *            the bankAccountHolderName to set
	 */
	public void setBankAccountHolderName(String bankAccountHolderName) {
		this.bankAccountHolderName = bankAccountHolderName;
	}

	/**
	 * @return the bankVerificationStatus
	 */
	public boolean isBankVerificationStatus() {
		return bankVerificationStatus;
	}

	/**
	 * @param bankVerificationStatus
	 *            the bankVerificationStatus to set
	 */
	public void setBankVerificationStatus(boolean bankVerificationStatus) {
		this.bankVerificationStatus = bankVerificationStatus;
	}

	/**
	 * @return the bankAccountNo
	 */
	public String getBankAccountNo() {
		return bankAccountNo;
	}

	/**
	 * @param bankAccountNo
	 *            the bankAccountNo to set
	 */
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	/**
	 * @return the bankActive
	 */
	public boolean isBankActive() {
		return bankActive;
	}

	/**
	 * @param bankActive
	 *            the bankActive to set
	 */
	public void setBankActive(boolean bankActive) {
		this.bankActive = bankActive;
	}

	/**
	 * @return the bankBranchAddress
	 */
	public String getBankBranchAddress() {
		return bankBranchAddress;
	}

	/**
	 * @param bankBranchAddress
	 *            the bankBranchAddress to set
	 */
	public void setBankBranchAddress(String bankBranchAddress) {
		this.bankBranchAddress = bankBranchAddress;
	}

	/**
	 * @return the bankIFSCCode
	 */
	public String getBankIFSCCode() {
		return bankIFSCCode;
	}

	/**
	 * @param bankIFSCCode
	 *            the bankIFSCCode to set
	 */
	public void setBankIFSCCode(String bankIFSCCode) {
		this.bankIFSCCode = bankIFSCCode;
	}

	/**
	 * @return the bankMICRNo
	 */
	public String getBankMICRNo() {
		return bankMICRNo;
	}

	/**
	 * @param bankMICRNo
	 *            the bankMICRNo to set
	 */
	public void setBankMICRNo(String bankMICRNo) {
		this.bankMICRNo = bankMICRNo;
	}

	/**
	 * @return the bankIBANNo
	 */
	public String getBankIBANNo() {
		return bankIBANNo;
	}

	/**
	 * @param bankIBANNo
	 *            the bankIBANNo to set
	 */
	public void setBankIBANNo(String bankIBANNo) {
		this.bankIBANNo = bankIBANNo;
	}

	/**
	 * @return the bankElecroCollectionAmt
	 */
	public double getBankElecroCollectionAmt() {
		return bankElecroCollectionAmt;
	}

	/**
	 * @param bankElecroCollectionAmt
	 *            the bankElecroCollectionAmt to set
	 */
	public void setBankElecroCollectionAmt(double bankElecroCollectionAmt) {
		this.bankElecroCollectionAmt = bankElecroCollectionAmt;
	}

	/**
	 * @return the bankAmountLimit
	 */
	public double getBankAmountLimit() {
		return bankAmountLimit;
	}

	/**
	 * @param bankAmountLimit
	 *            the bankAmountLimit to set
	 */
	public void setBankAmountLimit(double bankAmountLimit) {
		this.bankAmountLimit = bankAmountLimit;
	}

	/**
	 * @return the bankAvailableAP
	 */
	public boolean isBankAvailableAP() {
		return bankAvailableAP;
	}

	/**
	 * @param bankAvailableAP
	 *            the bankAvailableAP to set
	 */
	public void setBankAvailableAP(boolean bankAvailableAP) {
		this.bankAvailableAP = bankAvailableAP;
	}

	/**
	 * @return the bankDefaultAP
	 */
	public boolean isBankDefaultAP() {
		return bankDefaultAP;
	}

	/**
	 * @param bankDefaultAP
	 *            the bankDefaultAP to set
	 */
	public void setBankDefaultAP(boolean bankDefaultAP) {
		this.bankDefaultAP = bankDefaultAP;
	}

	/**
	 * @return the bankAvailableAR
	 */
	public boolean isBankAvailableAR() {
		return bankAvailableAR;
	}

	/**
	 * @param bankAvailableAR
	 *            the bankAvailableAR to set
	 */
	public void setBankAvailableAR(boolean bankAvailableAR) {
		this.bankAvailableAR = bankAvailableAR;
	}

	/**
	 * @return the bankAgreePaymentTermService
	 */
	public boolean isBankAgreePaymentTermService() {
		return bankAgreePaymentTermService;
	}

	/**
	 * @param bankAgreePaymentTermService
	 *            the bankAgreePaymentTermService to set
	 */
	public void setBankAgreePaymentTermService(
			boolean bankAgreePaymentTermService) {
		this.bankAgreePaymentTermService = bankAgreePaymentTermService;
	}

	/**
	 * @return the bankTransactionPass
	 */
	public String getBankTransactionPass() {
		return bankTransactionPass;
	}

	/**
	 * @param bankTransactionPass
	 *            the bankTransactionPass to set
	 */
	public void setBankTransactionPass(String bankTransactionPass) {
		this.bankTransactionPass = bankTransactionPass;
	}

	/**
	 * @return the bankOTPVerification
	 */
	public boolean isBankOTPVerification() {
		return bankOTPVerification;
	}

	/**
	 * @param bankOTPVerification
	 *            the bankOTPVerification to set
	 */
	public void setBankOTPVerification(boolean bankOTPVerification) {
		this.bankOTPVerification = bankOTPVerification;
	}

	/**
	 * @return the bankDebitMandateDoc
	 */
	public String getBankDebitMandateDoc() {
		return bankDebitMandateDoc;
	}

	/**
	 * @param bankDebitMandateDoc
	 *            the bankDebitMandateDoc to set
	 */
	public void setBankDebitMandateDoc(String bankDebitMandateDoc) {
		this.bankDebitMandateDoc = bankDebitMandateDoc;
	}

	/**
	 * @return the bankMandateVerifyStatus
	 */
	public boolean isBankMandateVerifyStatus() {
		return bankMandateVerifyStatus;
	}

	/**
	 * @param bankMandateVerifyStatus
	 *            the bankMandateVerifyStatus to set
	 */
	public void setBankMandateVerifyStatus(boolean bankMandateVerifyStatus) {
		this.bankMandateVerifyStatus = bankMandateVerifyStatus;
	}

	/**
	 * @return the bankBoardResolutionDoc
	 */
	public String getBankBoardResolutionDoc() {
		return bankBoardResolutionDoc;
	}

	/**
	 * @param bankBoardResolutionDoc
	 *            the bankBoardResolutionDoc to set
	 */
	public void setBankBoardResolutionDoc(String bankBoardResolutionDoc) {
		this.bankBoardResolutionDoc = bankBoardResolutionDoc;
	}

	/**
	 * @return the bankBoardResolutionStatus
	 */
	public boolean isBankBoardResolutionStatus() {
		return bankBoardResolutionStatus;
	}

	/**
	 * @param bankBoardResolutionStatus
	 *            the bankBoardResolutionStatus to set
	 */
	public void setBankBoardResolutionStatus(boolean bankBoardResolutionStatus) {
		this.bankBoardResolutionStatus = bankBoardResolutionStatus;
	}

	/**
	 * @return the bankPanCardDoc
	 */
	public String getBankPanCardDoc() {
		return bankPanCardDoc;
	}

	/**
	 * @param bankPanCardDoc
	 *            the bankPanCardDoc to set
	 */
	public void setBankPanCardDoc(String bankPanCardDoc) {
		this.bankPanCardDoc = bankPanCardDoc;
	}

	/**
	 * @return the bankPanCardVerifyStatus
	 */
	public boolean isBankPanCardVerifyStatus() {
		return bankPanCardVerifyStatus;
	}

	/**
	 * @param bankPanCardVerifyStatus
	 *            the bankPanCardVerifyStatus to set
	 */
	public void setBankPanCardVerifyStatus(boolean bankPanCardVerifyStatus) {
		this.bankPanCardVerifyStatus = bankPanCardVerifyStatus;
	}

	/**
	 * @return the bankAddressProofDoc
	 */
	public String getBankAddressProofDoc() {
		return bankAddressProofDoc;
	}

	/**
	 * @param bankAddressProofDoc
	 *            the bankAddressProofDoc to set
	 */
	public void setBankAddressProofDoc(String bankAddressProofDoc) {
		this.bankAddressProofDoc = bankAddressProofDoc;
	}

	/**
	 * @return the bankAddressProofVerifyStatus
	 */
	public String getBankAddressProofVerifyStatus() {
		return bankAddressProofVerifyStatus;
	}

	/**
	 * @param bankAddressProofVerifyStatus
	 *            the bankAddressProofVerifyStatus to set
	 */
	public void setBankAddressProofVerifyStatus(
			String bankAddressProofVerifyStatus) {
		this.bankAddressProofVerifyStatus = bankAddressProofVerifyStatus;
	}

	/**
	 * @return the bankCancellChequeDoc
	 */
	public String getBankCancellChequeDoc() {
		return bankCancellChequeDoc;
	}

	/**
	 * @param bankCancellChequeDoc
	 *            the bankCancellChequeDoc to set
	 */
	public void setBankCancellChequeDoc(String bankCancellChequeDoc) {
		this.bankCancellChequeDoc = bankCancellChequeDoc;
	}

	/**
	 * @return the bankCancellChequeStatus
	 */
	public boolean isBankCancellChequeStatus() {
		return bankCancellChequeStatus;
	}

	/**
	 * @param bankCancellChequeStatus
	 *            the bankCancellChequeStatus to set
	 */
	public void setBankCancellChequeStatus(boolean bankCancellChequeStatus) {
		this.bankCancellChequeStatus = bankCancellChequeStatus;
	}

	/**
	 * @return the bankCreatedBy
	 */
	public int getBankCreatedBy() {
		return bankCreatedBy;
	}

	/**
	 * @param bankCreatedBy
	 *            the bankCreatedBy to set
	 */
	public void setBankCreatedBy(int bankCreatedBy) {
		this.bankCreatedBy = bankCreatedBy;
	}

	/**
	 * @return the bankCreatedDate
	 */
	public Date getBankCreatedDate() {
		return bankCreatedDate;
	}

	/**
	 * @param bankCreatedDate
	 *            the bankCreatedDate to set
	 */
	public void setBankCreatedDate(Date bankCreatedDate) {
		this.bankCreatedDate = bankCreatedDate;
	}

	/**
	 * @return the bankUpdatedBy
	 */
	public int getBankUpdatedBy() {
		return bankUpdatedBy;
	}

	/**
	 * @param bankUpdatedBy
	 *            the bankUpdatedBy to set
	 */
	public void setBankUpdatedBy(int bankUpdatedBy) {
		this.bankUpdatedBy = bankUpdatedBy;
	}

	/**
	 * @return the bankUpdateDate
	 */
	public Date getBankUpdateDate() {
		return bankUpdateDate;
	}

	/**
	 * @param bankUpdateDate
	 *            the bankUpdateDate to set
	 */
	public void setBankUpdateDate(Date bankUpdateDate) {
		this.bankUpdateDate = bankUpdateDate;
	}

}
