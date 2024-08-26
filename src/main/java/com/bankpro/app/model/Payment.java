package com.bankpro.app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Surendra
 *
 */

@Entity
@Table(name = "pv_payments")
@NamedQueries({
		@NamedQuery(name = Payment.FETCH_ALL_PAYMENT, query = "from Payment where payOrgId = :orgId "),
		@NamedQuery(name = Payment.FETCH_Payment_Obj_BasedOnPaymentID, query = "from Payment where paymentId = :paymentId "),
		@NamedQuery(name = Payment.FETCH_Payment_Release_Data, query = "from Payment where payOrgId = :orgId and approvalStatus =:approvalStatus"),
		   })
public class Payment {
	public static final String FETCH_ALL_PAYMENT = "Payment.FETCH_ALL_PAYMENT";
	public static final String FETCH_Payment_Obj_BasedOnPaymentID = "Payment.FETCH_Payment_Obj_BasedOnPaymentID";
	public static final String FETCH_Payment_Release_Data = "Payment.FETCH_Payment_Release_Data";

	@Id
	@GeneratedValue
	@Column(name = "payment_id")
	private long paymentId;
	
	/*@Column(name = "pay_vendorId")
	private long payVendorId;*/
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pay_vendorId")
	private Vendor vendorDetail;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pay_invoiceId")
	private InvoiceDetails invoiceDetail; 
		
	@Column(name = "payment_amount")
	private double paymentAmount;
		
	@Column(name = "process_date")
	private Date paymentProcessDate;
	
	@Column(name = "payment_date")
	private Date paymentDate;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "pay_orgId")
	private int payOrgId;
	
	@Column(name = "created_by")
	private int createdBy;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_by")
	private int updatedBy;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "approval_status")
	private int approvalStatus;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "app_paymentId")
	private List<PaymentApprover> payApprover = new ArrayList<PaymentApprover>();
	

	/**
	 * @return the paymentId
	 */
	public long getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * @return the payVendorId
	 *//*
	public long getPayVendorId() {
		return payVendorId;
	}

	*//**
	 * @param payVendorId the payVendorId to set
	 *//*
	public void setPayVendorId(long payVendorId) {
		this.payVendorId = payVendorId;
	}*/

	/**
	 * @return the invoiceDetail
	 */
	public InvoiceDetails getInvoiceDetail() {
		return invoiceDetail;
	}

	/**
	 * @param invoiceDetail the invoiceDetail to set
	 */
	public void setInvoiceDetail(InvoiceDetails invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}

	/**
	 * @return the paymentAmount
	 */
	public double getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param paymentAmount the paymentAmount to set
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
	 * @param paymentProcessDate the paymentProcessDate to set
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
	 * @param paymentDate the paymentDate to set
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
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the payOrgId
	 */
	public int getPayOrgId() {
		return payOrgId;
	}

	/**
	 * @param payOrgId the payOrgId to set
	 */
	public void setPayOrgId(int payOrgId) {
		this.payOrgId = payOrgId;
	}

	/**
	 * @return the createdBy
	 */
	public int getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
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
	 * @param createdDate the createdDate to set
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
	 * @param updatedBy the updatedBy to set
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
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the approvalStatus
	 */
	public int getApprovalStatus() {
		return approvalStatus;
	}

	/**
	 * @param approvalStatus the approvalStatus to set
	 */
	public void setApprovalStatus(int approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	/**
	 * @return the payApprover
	 */
	public List<PaymentApprover> getPayApprover() {
		return payApprover;
	}

	/**
	 * @param payApprover the payApprover to set
	 */
	public void setPayApprover(List<PaymentApprover> payApprover) {
		this.payApprover = payApprover;
	}

	/**
	 * @return the vendorDetail
	 */
	public Vendor getVendorDetail() {
		return vendorDetail;
	}

	/**
	 * @param vendorDetail the vendorDetail to set
	 */
	public void setVendorDetail(Vendor vendorDetail) {
		this.vendorDetail = vendorDetail;
	}

		
}