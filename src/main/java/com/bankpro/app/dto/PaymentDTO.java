package com.bankpro.app.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.InvoiceDetails;
import com.bankpro.app.model.NewBankDetails;
import com.bankpro.app.model.Payment;
import com.bankpro.app.model.Vendor;
import com.fasterxml.jackson.annotation.JsonFormat;

public class PaymentDTO {

	private long paymentId;
	private long vendorId;
	private long invoiceId;

	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "IST")
	private Date paymentProcessDate;

	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "IST")
	private Date paymentDate;
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "IST")
	private Date dueDate;

	private String remarks;
	private double invoiceAmount;
	private double paymentAmount;
	private List<?> selectedPaymentApproverList = new ArrayList<>();
	private String invoiceNo;
	private String type;
	private String accNo;
	private String ifscCode;
	private String bankName;
	private String vendorName;
	private int approvalStatus;
	private List<?> newAddedDocListForPayment = new ArrayList<>();

	public PaymentDTO() {

	}

	public PaymentDTO(Payment payment) {
		this.paymentId = payment.getPaymentId();
		Vendor ven = payment.getVendorDetail();
		this.vendorId = ven.getId();
		this.vendorName = ven.getSupplierName();
		this.type = ven.getVendorType();
		this.paymentProcessDate = payment.getPaymentProcessDate();
		this.paymentDate = payment.getPaymentDate();
		this.remarks = payment.getRemarks();
		this.paymentAmount = payment.getPaymentAmount();
		this.approvalStatus = payment.getApprovalStatus();
		this.accNo = ven.getNewBankID().getBankAccountNo();
		this.bankName = ven.getNewBankID().getBankName();
		this.ifscCode = ven.getNewBankID().getBankIFSCCode();

		InvoiceDetails invDetail = payment.getInvoiceDetail();
		this.invoiceNo = invDetail.getInvoiceNumber();
		this.invoiceId = invDetail.getInvoiceId();
		this.invoiceAmount = invDetail.getInvoiceAmount();
		this.dueDate = invDetail.getInvoiceDueDate();

	}

	public static PaymentDTO mapPayment(Payment pay) {
		return new PaymentDTO(pay);
	}

	public static List<PaymentDTO> mapFromPaymentEntities(List<Payment> payments) {
		return payments.stream().map((pay) -> mapPayment(pay))
				.collect(Collectors.toList());
	}

	/**
	 * @return the accNo
	 */
	public String getAccNo() {
		return accNo;
	}

	/**
	 * @param accNo
	 *            the accNo to set
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
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
	 * @return the newAddedDocListForPayment
	 */
	public List<?> getNewAddedDocListForPayment() {
		return newAddedDocListForPayment;
	}

	/**
	 * @param newAddedDocListForPayment
	 *            the newAddedDocListForPayment to set
	 */
	public void setNewAddedDocListForPayment(List<?> newAddedDocListForPayment) {
		this.newAddedDocListForPayment = newAddedDocListForPayment;
	}

	/**
	 * @return the paymentId
	 */
	public long getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId
	 *            the paymentId to set
	 */
	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * @return the invoiceId
	 */
	public long getInvoiceId() {
		return invoiceId;
	}

	/**
	 * @param invoiceId
	 *            the invoiceId to set
	 */
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
	 * @return the paymentProcessDate
	 */
	public Date getPaymentProcessDate() {
		return paymentProcessDate;
	}

	/**
	 * @param paymentProcessDate
	 *            the paymentProcessDate to set
	 */
	public void setPaymentProcessDate(Date paymentProcessDate) {
		this.paymentProcessDate = paymentProcessDate;
	}

	/**
	 * @return the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate
	 *            the paymentDate to set
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the invoiceAmount
	 */
	public double getInvoiceAmount() {
		return invoiceAmount;
	}

	/**
	 * @param invoiceAmount
	 *            the invoiceAmount to set
	 */
	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	/**
	 * @return the paymentAmount
	 */
	public double getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param paymentAmount
	 *            the paymentAmount to set
	 */
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * @return the selectedPaymentApproverList
	 */
	public List<?> getSelectedPaymentApproverList() {
		return selectedPaymentApproverList;
	}

	/**
	 * @param selectedPaymentApproverList
	 *            the selectedPaymentApproverList to set
	 */
	public void setSelectedPaymentApproverList(
			List<?> selectedPaymentApproverList) {
		this.selectedPaymentApproverList = selectedPaymentApproverList;
	}

	/**
	 * @return the vendorId
	 */
	public long getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId
	 *            the vendorId to set
	 */
	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @param vendorName
	 *            the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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
	 * @return the approvalStatus
	 */
	public int getApprovalStatus() {
		return approvalStatus;
	}

	/**
	 * @param approvalStatus
	 *            the approvalStatus to set
	 */
	public void setApprovalStatus(int approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	/**
	 * @return the invoiceNo
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * @param invoiceNo
	 *            the invoiceNo to set
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
