/**
 * 
 */
package com.bankpro.app.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Lakharwal
 *
 */
@Entity
@Table(name = "pv_invoice")
@NamedQueries({
		@NamedQuery(name = InvoiceDetails.FETCH_IVNVOICE_OBJ, query = "from InvoiceDetails where invoiceId = :invoiceId"),
		@NamedQuery(name = InvoiceDetails.Find_pending_Approved_Invoice, query = "select InvoicevenId, invoiceId FROM InvoiceDetails where invoiceId "
				+ "in(select appInvoiceID from InvoiceApprover where appUserID = :appUserID and appStatus =0 and invoiceApprovalAccess =1)"),
		@NamedQuery(name = InvoiceDetails.FIND_APPROVAL_INVOICE_HISTORY, query = "select InvoicevenId, invoiceId FROM InvoiceDetails where invoiceOrgId = :orgId AND invoiceId "
				+ "in(select appInvoiceID from InvoiceApprover where appUserID = :appUserID and appStatus in(1,2) and invoiceApprovalAccess = 2)"),
		@NamedQuery(name = InvoiceDetails.Invoice_Obj_Based_On_InvoiceNo, query = "from InvoiceDetails where invoiceNumber = :invoiceNumber and invoiceOrgId = :invoiceOrgId"),
		@NamedQuery(name = InvoiceDetails.Update_InvoiceStatus_For_Cancel, query = "UPDATE InvoiceDetails set invoiceStatus = :invoiceStatus, invoiceUpdatedBy = :invoiceUpdatedBy, invoiceUpdatedDate = :invoiceUpdatedDate  where invoiceId = :invoiceId"),
		@NamedQuery(name = InvoiceDetails.Find_FOR_DUPLICATE_INVOICE_WITH_SAME_BEN, query = "select invoiceId from InvoiceDetails where invoiceNumber = :invoiceNumber and InvoicevenId = :InvoicevenId"),
		@NamedQuery(name = InvoiceDetails.FETCH_INVOICE_OBJ_BASED_ON_INVOICE_NO, query = "from InvoiceDetails where invoiceNumber = :invoiceNumber and invoiceOrgId = :invoiceOrgId")})
public class InvoiceDetails {
	public static final String FETCH_IVNVOICE_OBJ = "InvoiceDetails.FETCH_IVNVOICE_OBJ";
	public static final String Find_pending_Approved_Invoice = "InvoiceDetails.Find_pending_Approved_Invoice";
	public static final String FIND_APPROVAL_INVOICE_HISTORY = "InvoiceDetails.FIND_APPROVAL_INVOICE_HISTORY";
	public static final String Invoice_Obj_Based_On_InvoiceNo = "InvoiceDetails.Invoice_Obj_Based_On_InvoiceNo";
	public static final String Update_InvoiceStatus_For_Cancel = "InvoiceDetails.Update_InvoiceStatus_For_Cancel";
	public static final String Find_FOR_DUPLICATE_INVOICE_WITH_SAME_BEN = "InvoiceDetails.Find_FOR_DUPLICATE_INVOICE";
	public static final String FETCH_INVOICE_OBJ_BASED_ON_INVOICE_NO = "InvoiceDetails.FETCH_INVOICE_OBJ";

	@Id
	@GeneratedValue
	@Column(name = "invoice_id")
	private long invoiceId;

	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "invoice_type")
	private String invoiceType;

	@Column(name = "beneficiary_name")
	private String invoiceBeneficiaryName;

	@Column(name = "invoice_date")
	private Date invoiceDate;

	@Column(name = "payment_term")
	private String invoicePaymentTerm;

	@Column(name = "due_date")
	private Date invoiceDueDate;

	@Column(name = "invoice_amount")
	private double invoiceAmount;

	@Column(name = "gl_date")
	private Date invoiceGLDate;

	@Column(name = "invoice_description")
	private String invoiceDescription;

	@Column(name = "po_number")
	private String invoicePONumber;

	@Column(name = "tax_percent")
	private float invoiceTaxPrcnt;

	@Column(name = "taxes_amount")
	private double invoiceTaxAmt;

	@Column(name = "tds_deduction_percent")
	private float invoiceTDSDeductionPrcnt;

	@Column(name = "tds_deduction_amount")
	private double invoiceTDSDeductionAmt;

	@Column(name = "any_other_deduction")
	private double invoiceOtherDeduction;

	@Column(name = "invoice_item")
	private int invoiceItem;

	@Column(name = "invoice_quantity")
	private int invoiceQuantity;

	@Column(name = "invoice_rate")
	private float invoiceRate;

	@Column(name = "invoice_account")
	private int invoiceAccount;

	@Column(name = "created_by")
	private int invoiceCreatedBy;

	@Column(name = "created_date")
	private Date invoiceCreatedDate;

	@Column(name = "updated_by")
	private int invoiceUpdatedBy;

	@Column(name = "updated_date")
	private Date invoiceUpdatedDate;

	@Column(name = "inv_OrgID")
	private int invoiceOrgId;

	@Column(name = "approver_Comment")
	private String invoiceApproverComment;

	@Column(name = "inv_vendorID")
	private long InvoicevenId;

	@Column(name = "inv_status")
	private int invoiceStatus;

	@Column(name = "inv_approvalStatus")
	private int invoiceApprovalStatus;

	@Column(name = "inv_approvedAmount")
	private double invoiceApprovedAmt;

	@Column(name = "inv_paidAmount")
	private double invoicePaidAmount;

	@Column(name = "inv_basicAmount")
	private double invoiceBasicAmount;

	/**
	 * @return the invoiceBasicAmount
	 */
	public double getInvoiceBasicAmount() {
		return invoiceBasicAmount;
	}

	/**
	 * @param invoiceBasicAmount
	 *            the invoiceBasicAmount to set
	 */
	public void setInvoiceBasicAmount(double invoiceBasicAmount) {
		this.invoiceBasicAmount = invoiceBasicAmount;
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
	 * @return the invoiceApprovalStatus
	 */
	public int getInvoiceApprovalStatus() {
		return invoiceApprovalStatus;
	}

	/**
	 * @param invoiceApprovalStatus
	 *            the invoiceApprovalStatus to set
	 */
	public void setInvoiceApprovalStatus(int invoiceApprovalStatus) {
		this.invoiceApprovalStatus = invoiceApprovalStatus;
	}

	/**
	 * @return the invoiceApprovedAmt
	 */
	public double getInvoiceApprovedAmt() {
		return invoiceApprovedAmt;
	}

	/**
	 * @param invoiceApprovedAmt
	 *            the invoiceApprovedAmt to set
	 */
	public void setInvoiceApprovedAmt(double invoiceApprovedAmt) {
		this.invoiceApprovedAmt = invoiceApprovedAmt;
	}

	/**
	 * @return the invoicePaidAmount
	 */
	public double getInvoicePaidAmount() {
		return invoicePaidAmount;
	}

	/**
	 * @param invoicePaidAmount
	 *            the invoicePaidAmount to set
	 */
	public void setInvoicePaidAmount(double invoicePaidAmount) {
		this.invoicePaidAmount = invoicePaidAmount;
	}

	/**
	 * @return the invoicevenId
	 */
	public long getInvoicevenId() {
		return InvoicevenId;
	}

	/**
	 * @param invoicevenId
	 *            the invoicevenId to set
	 */
	public void setInvoicevenId(long invoicevenId) {
		InvoicevenId = invoicevenId;
	}

	/**
	 * @return the invoiceApproverComment
	 */
	public String getInvoiceApproverComment() {
		return invoiceApproverComment;
	}

	/**
	 * @param invoiceApproverComment
	 *            the invoiceApproverComment to set
	 */
	public void setInvoiceApproverComment(String invoiceApproverComment) {
		this.invoiceApproverComment = invoiceApproverComment;
	}

	/**
	 * @return the invoiceOrgId
	 */
	public int getInvoiceOrgId() {
		return invoiceOrgId;
	}

	/**
	 * @param invoiceOrgId
	 *            the invoiceOrgId to set
	 */
	public void setInvoiceOrgId(int invoiceOrgId) {
		this.invoiceOrgId = invoiceOrgId;
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
	 * @return the invoiceBeneficiaryName
	 */
	public String getInvoiceBeneficiaryName() {
		return invoiceBeneficiaryName;
	}

	/**
	 * @param invoiceBeneficiaryName
	 *            the invoiceBeneficiaryName to set
	 */
	public void setInvoiceBeneficiaryName(String invoiceBeneficiaryName) {
		this.invoiceBeneficiaryName = invoiceBeneficiaryName;
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
	 * @return the invoicePaymentTerm
	 */
	public String getInvoicePaymentTerm() {
		return invoicePaymentTerm;
	}

	/**
	 * @param invoicePaymentTerm
	 *            the invoicePaymentTerm to set
	 */
	public void setInvoicePaymentTerm(String invoicePaymentTerm) {
		this.invoicePaymentTerm = invoicePaymentTerm;
	}

	/**
	 * @return the invoiceDueDate
	 */
	public Date getInvoiceDueDate() {
		return invoiceDueDate;
	}

	/**
	 * @param invoiceDueDate
	 *            the invoiceDueDate to set
	 */
	public void setInvoiceDueDate(Date invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
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
	 * @return the invoiceGLDate
	 */
	public Date getInvoiceGLDate() {
		return invoiceGLDate;
	}

	/**
	 * @param invoiceGLDate
	 *            the invoiceGLDate to set
	 */
	public void setInvoiceGLDate(Date invoiceGLDate) {
		this.invoiceGLDate = invoiceGLDate;
	}

	/**
	 * @return the invoiceDescription
	 */
	public String getInvoiceDescription() {
		return invoiceDescription;
	}

	/**
	 * @param invoiceDescription
	 *            the invoiceDescription to set
	 */
	public void setInvoiceDescription(String invoiceDescription) {
		this.invoiceDescription = invoiceDescription;
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
	 * @return the invoicePONumber
	 */
	public String getInvoicePONumber() {
		return invoicePONumber;
	}

	/**
	 * @param invoicePONumber
	 *            the invoicePONumber to set
	 */
	public void setInvoicePONumber(String invoicePONumber) {
		this.invoicePONumber = invoicePONumber;
	}

	/**
	 * @return the invoiceTaxPrcnt
	 */
	public float getInvoiceTaxPrcnt() {
		return invoiceTaxPrcnt;
	}

	/**
	 * @param invoiceTaxPrcnt
	 *            the invoiceTaxPrcnt to set
	 */
	public void setInvoiceTaxPrcnt(float invoiceTaxPrcnt) {
		this.invoiceTaxPrcnt = invoiceTaxPrcnt;
	}

	/**
	 * @return the invoiceTaxAmt
	 */
	public double getInvoiceTaxAmt() {
		return invoiceTaxAmt;
	}

	/**
	 * @param invoiceTaxAmt
	 *            the invoiceTaxAmt to set
	 */
	public void setInvoiceTaxAmt(double invoiceTaxAmt) {
		this.invoiceTaxAmt = invoiceTaxAmt;
	}

	/**
	 * @return the invoiceTDSDeductionPrcnt
	 */
	public float getInvoiceTDSDeductionPrcnt() {
		return invoiceTDSDeductionPrcnt;
	}

	/**
	 * @param invoiceTDSDeductionPrcnt
	 *            the invoiceTDSDeductionPrcnt to set
	 */
	public void setInvoiceTDSDeductionPrcnt(float invoiceTDSDeductionPrcnt) {
		this.invoiceTDSDeductionPrcnt = invoiceTDSDeductionPrcnt;
	}

	/**
	 * @return the invoiceTDSDeductionAmt
	 */
	public double getInvoiceTDSDeductionAmt() {
		return invoiceTDSDeductionAmt;
	}

	/**
	 * @param invoiceTDSDeductionAmt
	 *            the invoiceTDSDeductionAmt to set
	 */
	public void setInvoiceTDSDeductionAmt(double invoiceTDSDeductionAmt) {
		this.invoiceTDSDeductionAmt = invoiceTDSDeductionAmt;
	}

	/**
	 * @return the invoiceOtherDeduction
	 */
	public double getInvoiceOtherDeduction() {
		return invoiceOtherDeduction;
	}

	/**
	 * @param invoiceOtherDeduction
	 *            the invoiceOtherDeduction to set
	 */
	public void setInvoiceOtherDeduction(double invoiceOtherDeduction) {
		this.invoiceOtherDeduction = invoiceOtherDeduction;
	}

	/**
	 * @return the invoiceItem
	 */
	public int getInvoiceItem() {
		return invoiceItem;
	}

	/**
	 * @param invoiceItem
	 *            the invoiceItem to set
	 */
	public void setInvoiceItem(int invoiceItem) {
		this.invoiceItem = invoiceItem;
	}

	/**
	 * @return the invoiceQuantity
	 */
	public int getInvoiceQuantity() {
		return invoiceQuantity;
	}

	/**
	 * @param invoiceQuantity
	 *            the invoiceQuantity to set
	 */
	public void setInvoiceQuantity(int invoiceQuantity) {
		this.invoiceQuantity = invoiceQuantity;
	}

	/**
	 * @return the invoiceRate
	 */
	public float getInvoiceRate() {
		return invoiceRate;
	}

	/**
	 * @param invoiceRate
	 *            the invoiceRate to set
	 */
	public void setInvoiceRate(float invoiceRate) {
		this.invoiceRate = invoiceRate;
	}

	/**
	 * @return the invoiceAccount
	 */
	public int getInvoiceAccount() {
		return invoiceAccount;
	}

	/**
	 * @param invoiceAccount
	 *            the invoiceAccount to set
	 */
	public void setInvoiceAccount(int invoiceAccount) {
		this.invoiceAccount = invoiceAccount;
	}

	/**
	 * @return the invoiceCreatedBy
	 */
	public int getInvoiceCreatedBy() {
		return invoiceCreatedBy;
	}

	/**
	 * @param invoiceCreatedBy
	 *            the invoiceCreatedBy to set
	 */
	public void setInvoiceCreatedBy(int invoiceCreatedBy) {
		this.invoiceCreatedBy = invoiceCreatedBy;
	}

	/**
	 * @return the invoiceCreatedDate
	 */
	public Date getInvoiceCreatedDate() {
		return invoiceCreatedDate;
	}

	/**
	 * @param invoiceCreatedDate
	 *            the invoiceCreatedDate to set
	 */
	public void setInvoiceCreatedDate(Date invoiceCreatedDate) {
		this.invoiceCreatedDate = invoiceCreatedDate;
	}

	/**
	 * @return the invoiceUpdatedBy
	 */
	public int getInvoiceUpdatedBy() {
		return invoiceUpdatedBy;
	}

	/**
	 * @param invoiceUpdatedBy
	 *            the invoiceUpdatedBy to set
	 */
	public void setInvoiceUpdatedBy(int invoiceUpdatedBy) {
		this.invoiceUpdatedBy = invoiceUpdatedBy;
	}

	/**
	 * @return the invoiceUpdatedDate
	 */
	public Date getInvoiceUpdatedDate() {
		return invoiceUpdatedDate;
	}

	/**
	 * @param invoiceUpdatedDate
	 *            the invoiceUpdatedDate to set
	 */
	public void setInvoiceUpdatedDate(Date invoiceUpdatedDate) {
		this.invoiceUpdatedDate = invoiceUpdatedDate;
	}

}
