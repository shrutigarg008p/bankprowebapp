/**
 * 
 */
package com.bankpro.app.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.InvoiceDetails;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Lakharwal
 *
 */
public class InvoiceDTO {
	private long invoiceID;
	private String beneficiaryName;
	private String beneficiaryID;
	private String invoiceNumber;
	private String invoiceType;

	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "IST")
	private Date invoiceDate;
	private double amount;  //basic amount
	private String description;
	private String poNumber;

	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "IST")
	private Date dueDate;
	private double approveAmount; // not imlemented
	private double netInvoiceAmount; //invoice amount
	private String paymentTerm;
	private float taxPercent;
	private double taxAmount;
	private float tdsDeductionPrecnt;
	private double tdsDeductionAmt;
	private double otherDeductionAmt;
	private String approverNote;
	private long vendorID;
	private List<?> newAddedDocListForInvoice = new ArrayList<>();
	private int invoiceStatus;
	private int invApprovalStatus;
	private double invApprovedAmt;
	private double invPaidAmt;
	private List<?> selectedIncoiveApproverList = new ArrayList<>();

	public InvoiceDTO() {

	}

	public InvoiceDTO(InvoiceDetails invObj) {
		this.invoiceID = invObj.getInvoiceId();
		this.beneficiaryName = invObj.getInvoiceBeneficiaryName();
		this.beneficiaryID = String.valueOf(invObj.getInvoicevenId());
		this.dueDate = invObj.getInvoiceDueDate();
		this.invoiceNumber = invObj.getInvoiceNumber();
		this.amount = invObj.getInvoiceBasicAmount(); // bsic amount
		this.netInvoiceAmount = invObj.getInvoiceAmount();  //invoice amount
		this.poNumber = invObj.getInvoicePONumber();
		this.paymentTerm = invObj.getInvoicePaymentTerm();
		this.invoiceType = invObj.getInvoiceType();
		this.invoiceDate = invObj.getInvoiceDate();
		this.description = invObj.getInvoiceDescription();
		this.taxPercent = invObj.getInvoiceTaxPrcnt();
		this.taxAmount = invObj.getInvoiceTaxAmt();
		this.tdsDeductionPrecnt = invObj.getInvoiceTDSDeductionPrcnt();
		this.tdsDeductionAmt = invObj.getInvoiceTDSDeductionAmt();
		this.otherDeductionAmt = invObj.getInvoiceOtherDeduction();
		this.approverNote = invObj.getInvoiceApproverComment();
		this.vendorID = invObj.getInvoicevenId();
		this.invoiceStatus = invObj.getInvoiceStatus();
		this.invApprovalStatus = invObj.getInvoiceApprovalStatus();
		this.invApprovedAmt = invObj.getInvoiceApprovedAmt();
		this.invPaidAmt = invObj.getInvoicePaidAmount();

	}
	
	public static InvoiceDTO mapFromInvoiceEntity(InvoiceDetails invoicee) {
		return new InvoiceDTO(invoicee);
	}

	public static List<InvoiceDTO> mapFromInvoiceEntities(
			List<InvoiceDetails> invoice) {
		return invoice.stream()
				.map((invoicee) -> mapFromInvoiceEntity(invoicee))
				.collect(Collectors.toList());
	}
	
	public InvoiceDTO(Date dueDate, double invAmount) {
		this.dueDate = dueDate;
		this.netInvoiceAmount = invAmount; //invoice amount
		

	}
	
	public static InvoiceDTO mapToShowDueAndPaymentBasedOnINvoiceNO(Date dueDate, double invAmount) {
		return new InvoiceDTO(dueDate, invAmount);
	}
	
	
	

	/**
	 * @return the beneficiaryID
	 */
	public String getBeneficiaryID() {
		return beneficiaryID;
	}

	/**
	 * @param beneficiaryID the beneficiaryID to set
	 */
	public void setBeneficiaryID(String beneficiaryID) {
		this.beneficiaryID = beneficiaryID;
	}

	/**
	 * @return the selectedIncoiveApproverList
	 */
	public List<?> getSelectedIncoiveApproverList() {
		return selectedIncoiveApproverList;
	}

	/**
	 * @param selectedIncoiveApproverList
	 *            the selectedIncoiveApproverList to set
	 */
	public void setSelectedIncoiveApproverList(
			List<?> selectedIncoiveApproverList) {
		this.selectedIncoiveApproverList = selectedIncoiveApproverList;
	}

	/**
	 * @return the invoiceStatus
	 */
	public int getInvoiceStatus() {
		return invoiceStatus;
	}

	/**
	 * @param invoiceStatus
	 *            the invoiceStatus to set
	 */
	public void setInvoiceStatus(int invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	/**
	 * @return the invApprovalStatus
	 */
	public int getInvApprovalStatus() {
		return invApprovalStatus;
	}

	/**
	 * @param invApprovalStatus
	 *            the invApprovalStatus to set
	 */
	public void setInvApprovalStatus(int invApprovalStatus) {
		this.invApprovalStatus = invApprovalStatus;
	}

	/**
	 * @return the invApprovedAmt
	 */
	public double getInvApprovedAmt() {
		return invApprovedAmt;
	}

	/**
	 * @param invApprovedAmt
	 *            the invApprovedAmt to set
	 */
	public void setInvApprovedAmt(double invApprovedAmt) {
		this.invApprovedAmt = invApprovedAmt;
	}

	/**
	 * @return the invPaidAmt
	 */
	public double getInvPaidAmt() {
		return invPaidAmt;
	}

	/**
	 * @param invPaidAmt
	 *            the invPaidAmt to set
	 */
	public void setInvPaidAmt(double invPaidAmt) {
		this.invPaidAmt = invPaidAmt;
	}

	/**
	 * @return the vendorID
	 */
	public long getVendorID() {
		return vendorID;
	}

	/**
	 * @param vendorID
	 *            the vendorID to set
	 */
	public void setVendorID(long vendorID) {
		this.vendorID = vendorID;
	}

	/**
	 * @return the newAddedDocListForInvoice
	 */
	public List<?> getNewAddedDocListForInvoice() {
		return newAddedDocListForInvoice;
	}

	/**
	 * @param newAddedDocListForInvoice
	 *            the newAddedDocListForInvoice to set
	 */
	public void setNewAddedDocListForInvoice(List<?> newAddedDocListForInvoice) {
		this.newAddedDocListForInvoice = newAddedDocListForInvoice;
	}

	/**
	 * @return the invoiceID
	 */
	public long getInvoiceID() {
		return invoiceID;
	}

	/**
	 * @param invoiceID
	 *            the invoiceID to set
	 */
	public void setInvoiceID(long invoiceID) {
		this.invoiceID = invoiceID;
	}

	/**
	 * @return the beneficiaryName
	 */
	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	/**
	 * @param beneficiaryName
	 *            the beneficiaryName to set
	 */
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	/**
	 * @return the approverNote
	 */
	public String getApproverNote() {
		return approverNote;
	}

	/**
	 * @param approverNote
	 *            the approverNote to set
	 */
	public void setApproverNote(String approverNote) {
		this.approverNote = approverNote;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the poNumber
	 */
	public String getPoNumber() {
		return poNumber;
	}

	/**
	 * @param poNumber
	 *            the poNumber to set
	 */
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	/**
	 * @return the invoiceType
	 */
	public String getInvoiceType() {
		return invoiceType;
	}

	/**
	 * @param invoiceType
	 *            the invoiceType to set
	 */
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	/**
	 * @return the invoiceDate
	 */
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate
	 *            the invoiceDate to set
	 */
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the approveAmount
	 */
	public double getApproveAmount() {
		return approveAmount;
	}

	/**
	 * @param approveAmount
	 *            the approveAmount to set
	 */
	public void setApproveAmount(double approveAmount) {
		this.approveAmount = approveAmount;
	}

	/**
	 * @return the netInvoiceAmount
	 */
	public double getNetInvoiceAmount() {
		return netInvoiceAmount;
	}

	/**
	 * @param netInvoiceAmount
	 *            the netInvoiceAmount to set
	 */
	public void setNetInvoiceAmount(double netInvoiceAmount) {
		this.netInvoiceAmount = netInvoiceAmount;
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
	 * @return the taxPercent
	 */
	public float getTaxPercent() {
		return taxPercent;
	}

	/**
	 * @param taxPercent
	 *            the taxPercent to set
	 */
	public void setTaxPercent(float taxPercent) {
		this.taxPercent = taxPercent;
	}

	/**
	 * @return the taxAmount
	 */
	public double getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount
	 *            the taxAmount to set
	 */
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * @return the tdsDeductionPrecnt
	 */
	public float getTdsDeductionPrecnt() {
		return tdsDeductionPrecnt;
	}

	/**
	 * @param tdsDeductionPrecnt
	 *            the tdsDeductionPrecnt to set
	 */
	public void setTdsDeductionPrecnt(float tdsDeductionPrecnt) {
		this.tdsDeductionPrecnt = tdsDeductionPrecnt;
	}

	/**
	 * @return the tdsDeductionAmt
	 */
	public double getTdsDeductionAmt() {
		return tdsDeductionAmt;
	}

	/**
	 * @param tdsDeductionAmt
	 *            the tdsDeductionAmt to set
	 */
	public void setTdsDeductionAmt(double tdsDeductionAmt) {
		this.tdsDeductionAmt = tdsDeductionAmt;
	}

	/**
	 * @return the otherDeductionAmt
	 */
	public double getOtherDeductionAmt() {
		return otherDeductionAmt;
	}

	/**
	 * @param otherDeductionAmt
	 *            the otherDeductionAmt to set
	 */
	public void setOtherDeductionAmt(double otherDeductionAmt) {
		this.otherDeductionAmt = otherDeductionAmt;
	}

}
