package com.bankpro.app.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.NewBankDetails;

public class BankDetailsDTO {
	private Long bankId;
	private String bankName;
	private String branchName;
	private String accountNumber;
	private String accountName;
	private String accHolderName;
	private String branchAddress;
	private int countryID;
	private boolean bankActive;
	private boolean verifyStatus;
	private String ifscCode;
	private String micrNo;
	private String ibanNo;
	private double amountLimit;
	private double electronicCollection;
	private String virtualAcctIFSC;
	private boolean availableForAP;
	private boolean defaultForAP;
	private boolean availableForAR;
	private boolean trigerBankDialogueFlag;
	private String debitMandateDoc;
	private String boardResolutionDoc;
	private String panCardDoc;
	private String addressProofDoc;
	private String cancelCheque;
	private boolean termAcceptanceFlag;
	private String transactionPass;
	private List<?> newAddedDocListInBank = new ArrayList<>();

	public BankDetailsDTO() {

	}

	public BankDetailsDTO(NewBankDetails bankObj) {
		this.bankId = bankObj.getBankId();
		this.bankName = bankObj.getBankName();
		this.accountNumber = bankObj.getBankAccountNo();
		this.availableForAP = bankObj.isBankAvailableAP();
		this.availableForAR = bankObj.isBankAvailableAR();
		this.defaultForAP = bankObj.isBankDefaultAP();
		this.bankActive = bankObj.isBankActive();
		this.verifyStatus = bankObj.isBankVerificationStatus();
		this.accHolderName = bankObj.getBankAccountHolderName();
		this.branchAddress = bankObj.getBankBranchAddress();
		this.ifscCode = bankObj.getBankIFSCCode();
		this.micrNo = bankObj.getBankMICRNo();
		this.ibanNo = bankObj.getBankIBANNo();
		this.electronicCollection = bankObj.getBankElecroCollectionAmt();
		this.amountLimit = bankObj.getBankAmountLimit();
		this.panCardDoc = bankObj.getBankPanCardDoc();
		this.addressProofDoc = bankObj.getBankAddressProofDoc();
		this.boardResolutionDoc = bankObj.getBankBoardResolutionDoc();
		this.debitMandateDoc = bankObj.getBankDebitMandateDoc();
		this.cancelCheque = bankObj.getBankCancellChequeDoc();
		this.termAcceptanceFlag = bankObj.isBankAgreePaymentTermService();

	}

	public static BankDetailsDTO mapBankDetailEntity(NewBankDetails bankObj) {
		return new BankDetailsDTO(bankObj);
	}

	public static List<BankDetailsDTO> mapBankDetailFromList(
			List<NewBankDetails> bankList) {
		return bankList.stream()
				.map((bankListt) -> mapBankDetailEntity(bankListt))
				.collect(Collectors.toList());
	}
	
	public BankDetailsDTO(long bankID, String accNo) {
		this.bankId = bankID;
		this.accountName = accNo;
	}
	
	public static List<BankDetailsDTO> extractAccountNoForPaymentRelease(List<?> accNo) {
		List<BankDetailsDTO> bankDTO = new ArrayList<BankDetailsDTO>();
		for (Object accct : accNo) {
			Object[] accObj = (Object[])accct;
			bankDTO.add(new BankDetailsDTO(Long.valueOf(accObj[0].toString()), accObj[1].toString()));
		}
		return bankDTO;
	}
	
	

	
	
	/**
	 * @return the accountNumber
	 */

	/**
	 * @return the transactionPass
	 */
	public String getTransactionPass() {
		return transactionPass;
	}

	/**
	 * @param transactionPass the transactionPass to set
	 */
	public void setTransactionPass(String transactionPass) {
		this.transactionPass = transactionPass;
	}

	/**
	 * @return the termAcceptanceFlag
	 */
	public boolean isTermAcceptanceFlag() {
		return termAcceptanceFlag;
	}

	/**
	 * @param termAcceptanceFlag the termAcceptanceFlag to set
	 */
	public void setTermAcceptanceFlag(boolean termAcceptanceFlag) {
		this.termAcceptanceFlag = termAcceptanceFlag;
	}

	/**
	 * @return the newAddedDocListInBank
	 */
	public List<?> getNewAddedDocListInBank() {
		return newAddedDocListInBank;
	}

	/**
	 * @param newAddedDocListInBank the newAddedDocListInBank to set
	 */
	public void setNewAddedDocListInBank(List<?> newAddedDocListInBank) {
		this.newAddedDocListInBank = newAddedDocListInBank;
	}

	/**
	 * @return the debitMandateDoc
	 */
	public String getDebitMandateDoc() {
		return debitMandateDoc;
	}

	/**
	 * @param debitMandateDoc
	 *            the debitMandateDoc to set
	 */
	public void setDebitMandateDoc(String debitMandateDoc) {
		this.debitMandateDoc = debitMandateDoc;
	}

	/**
	 * @return the boardResolutionDoc
	 */
	public String getBoardResolutionDoc() {
		return boardResolutionDoc;
	}

	/**
	 * @param boardResolutionDoc
	 *            the boardResolutionDoc to set
	 */
	public void setBoardResolutionDoc(String boardResolutionDoc) {
		this.boardResolutionDoc = boardResolutionDoc;
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
	 * @return the trigerBankDialogueFlag
	 */
	public boolean isTrigerBankDialogueFlag() {
		return trigerBankDialogueFlag;
	}

	/**
	 * @param trigerBankDialogueFlag
	 *            the trigerBankDialogueFlag to set
	 */
	public void setTrigerBankDialogueFlag(boolean trigerBankDialogueFlag) {
		this.trigerBankDialogueFlag = trigerBankDialogueFlag;
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
	 * @return the electronicCollection
	 */
	public double getElectronicCollection() {
		return electronicCollection;
	}

	/**
	 * @param electronicCollection
	 *            the electronicCollection to set
	 */
	public void setElectronicCollection(double electronicCollection) {
		this.electronicCollection = electronicCollection;
	}

	/**
	 * @return the virtualAcctIFSC
	 */
	public String getVirtualAcctIFSC() {
		return virtualAcctIFSC;
	}

	/**
	 * @param virtualAcctIFSC
	 *            the virtualAcctIFSC to set
	 */
	public void setVirtualAcctIFSC(String virtualAcctIFSC) {
		this.virtualAcctIFSC = virtualAcctIFSC;
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
	 * @return the verifyStatus
	 */
	public boolean isVerifyStatus() {
		return verifyStatus;
	}

	/**
	 * @param verifyStatus
	 *            the verifyStatus to set
	 */
	public void setVerifyStatus(boolean verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	/**
	 * @return the accHolderName
	 */
	public String getAccHolderName() {
		return accHolderName;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @param accHolderName
	 *            the accHolderName to set
	 */
	public void setAccHolderName(String accHolderName) {
		this.accHolderName = accHolderName;
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
	 * @return the micrNo
	 */
	public String getMicrNo() {
		return micrNo;
	}

	/**
	 * @param micrNo
	 *            the micrNo to set
	 */
	public void setMicrNo(String micrNo) {
		this.micrNo = micrNo;
	}

	/**
	 * @return the ibanNo
	 */
	public String getIbanNo() {
		return ibanNo;
	}

	/**
	 * @param ibanNo
	 *            the ibanNo to set
	 */
	public void setIbanNo(String ibanNo) {
		this.ibanNo = ibanNo;
	}

	/**
	 * @return the amountLimit
	 */
	public double getAmountLimit() {
		return amountLimit;
	}

	/**
	 * @param amountLimit
	 *            the amountLimit to set
	 */
	public void setAmountLimit(double amountLimit) {
		this.amountLimit = amountLimit;
	}

	/**
	 * @return the availableForAP
	 */
	public boolean isAvailableForAP() {
		return availableForAP;
	}

	/**
	 * @param availableForAP
	 *            the availableForAP to set
	 */
	public void setAvailableForAP(boolean availableForAP) {
		this.availableForAP = availableForAP;
	}

	/**
	 * @return the defaultForAP
	 */
	public boolean isDefaultForAP() {
		return defaultForAP;
	}

	/**
	 * @param defaultForAP
	 *            the defaultForAP to set
	 */
	public void setDefaultForAP(boolean defaultForAP) {
		this.defaultForAP = defaultForAP;
	}

	/**
	 * @return the availableForAR
	 */
	public boolean isAvailableForAR() {
		return availableForAR;
	}

	/**
	 * @param availableForAR
	 *            the availableForAR to set
	 */
	public void setAvailableForAR(boolean availableForAR) {
		this.availableForAR = availableForAR;
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
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 *            the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

}
