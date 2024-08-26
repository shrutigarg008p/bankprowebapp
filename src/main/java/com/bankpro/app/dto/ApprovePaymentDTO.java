package com.bankpro.app.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.InvoiceDetails;
import com.bankpro.app.model.Payment;
import com.bankpro.app.model.PaymentApprover;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ApprovePaymentDTO {
	private long paymentId;
	private long vendorId;
	private long payAppId;
	private String vendorName;
	private String invoiceNo;
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "IST")
	private Date dueDate;
	private double invoiceAmount;
	private double paymentAmount;
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "IST")
	private Date paymentProcessDate;
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "IST")
	private Date paymentDate;
	private String type;
	private double totalAmt;

	public ApprovePaymentDTO() {

	}

	public ApprovePaymentDTO(PaymentApprover payAppObj, InvoiceDetails invObj, Payment payObj) {
		this.paymentId = payObj.getPaymentId();
		this.vendorName = payObj.getVendorDetail().getUserId().getUserFirstName()+ " " + payObj.getVendorDetail().getUserId().getUserLastName();
		this.invoiceNo = invObj.getInvoiceNumber();
		this.dueDate = invObj.getInvoiceDueDate();
		this.invoiceAmount = invObj.getInvoiceAmount();
		this.paymentAmount = payObj.getPaymentAmount();
		this.paymentDate = payObj.getPaymentDate();
		this.paymentProcessDate = payObj.getPaymentProcessDate();
		this.type = "Vendor";
		//this.totalAmt = 53255.76;
		this.payAppId = payAppObj.getAppID();
		this.vendorId = payObj.getVendorDetail().getId();
	}

	public static ApprovePaymentDTO mapFromPaymentEntity(PaymentApprover payApp) {
		return new ApprovePaymentDTO(payApp, payApp.getPayment()
				.getInvoiceDetail(), payApp.getPayment());
	}

	public static List<ApprovePaymentDTO> mapPendingPaymentApprove(
			List<PaymentApprover> payApp) {
		return payApp.stream().map((payAppp) -> mapFromPaymentEntity(payAppp))
				.collect(Collectors.toList());
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
	 * @return the payAppId
	 */
	public long getPayAppId() {
		return payAppId;
	}

	/**
	 * @param payAppId
	 *            the payAppId to set
	 */
	public void setPayAppId(long payAppId) {
		this.payAppId = payAppId;
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

	/**
	 * @return the totalAmt
	 */
	public double getTotalAmt() {
		return totalAmt;
	}

	/**
	 * @param totalAmt
	 *            the totalAmt to set
	 */
	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}

}
