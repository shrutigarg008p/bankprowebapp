package com.bankpro.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "pv_invoice_approver")
@NamedQueries({ 
	@NamedQuery(name = InvoiceApprover.Find_Invoice_Approver_Based_On_InvoiceID, query = "from InvoiceApprover where appInvoiceID = :appInvoiceID and  appStatus = 0 order by appID asc"),
	@NamedQuery(name = InvoiceApprover.FETCH_APPROVE_INVOICE_OBJ, query = "from InvoiceApprover where appInvoiceID = :invoiceId"),
	@NamedQuery(name = InvoiceApprover.FETCH_INVOICE_APPROVAL_HISTORY, query = "from InvoiceApprover where appUserID = :userId"),
	@NamedQuery(name = InvoiceApprover.udpate_For_Approval_Status_In_Cancel, query = "UPDATE InvoiceApprover set invoiceApprovalAccess= :invoiceApprovalAccess, appStatus = :appStatus,"
			+ " appUpdatedBy = :appUpdatedBy, appUpdatedDate = :appUpdatedDate, appComment= :appComment where appInvoiceID = :appInvoiceID")
})
public class InvoiceApprover {
	public static final String Find_Invoice_Approver_Based_On_InvoiceID = "InvoiceApprover.Find_Invoice_Approver_Based_On_InvoiceID";
	public static final String FETCH_APPROVE_INVOICE_OBJ = "InvoiceApprover.FETCH_APPROVE_INVOICE_OBJ";
	public static final String FETCH_INVOICE_APPROVAL_HISTORY = "InvoiceApprover.FETCH_INVOICE_APPROVAL_HISTORY";
	public static final String udpate_For_Approval_Status_In_Cancel = "InvoiceApprover.udpate_For_Approval_Status_In_Cancel";

	@Id
	@GeneratedValue
	@Column(name = "app_id")
	private long appID;

	@Column(name = "app_userId")
	private long appUserID;

	@Column(name = "app_invoiceId")
	private long appInvoiceID;

	@Column(name = "app_createdBy")
	private int appCreatedBy;

	@Column(name = "app_CreatedDate")
	private Date appCreatedDate;

	@Column(name = "app_updatedBy")
	private int appUpdatedBy;

	@Column(name = "app_updatedDate")
	private Date appUpdatedDate;

	@Column(name = "app_status")
	private int appStatus;

	@Column(name = "app_comment")
	private String appComment;

	@Column(name = "app_invoiceAccess")
	private int invoiceApprovalAccess;

	public InvoiceApprover(long approvalUser, long invoiceId, int sessionUser,
			int accessFlag) {
		this.appUserID = approvalUser;
		this.appInvoiceID = invoiceId;
		this.appCreatedBy = sessionUser;
		this.invoiceApprovalAccess = accessFlag;
	}

	public InvoiceApprover() {

	}

	/**
	 * @return the invoiceApprovalAccess
	 */
	public int getInvoiceApprovalAccess() {
		return invoiceApprovalAccess;
	}

	/**
	 * @param invoiceApprovalAccess
	 *            the invoiceApprovalAccess to set
	 */
	public void setInvoiceApprovalAccess(int invoiceApprovalAccess) {
		this.invoiceApprovalAccess = invoiceApprovalAccess;
	}

	/**
	 * @return the appID
	 */
	public long getAppID() {
		return appID;
	}

	/**
	 * @param appID
	 *            the appID to set
	 */
	public void setAppID(long appID) {
		this.appID = appID;
	}

	/**
	 * @return the appUserID
	 */
	public long getAppUserID() {
		return appUserID;
	}

	/**
	 * @param appUserID
	 *            the appUserID to set
	 */
	public void setAppUserID(long appUserID) {
		this.appUserID = appUserID;
	}

	/**
	 * @return the appInvoiceID
	 */
	public long getAppInvoiceID() {
		return appInvoiceID;
	}

	/**
	 * @param appInvoiceID
	 *            the appInvoiceID to set
	 */
	public void setAppInvoiceID(long appInvoiceID) {
		this.appInvoiceID = appInvoiceID;
	}

	/**
	 * @return the appCreatedBy
	 */
	public int getAppCreatedBy() {
		return appCreatedBy;
	}

	/**
	 * @param appCreatedBy
	 *            the appCreatedBy to set
	 */
	public void setAppCreatedBy(int appCreatedBy) {
		this.appCreatedBy = appCreatedBy;
	}

	/**
	 * @return the appCreatedDate
	 */
	public Date getAppCreatedDate() {
		return appCreatedDate;
	}

	/**
	 * @param appCreatedDate
	 *            the appCreatedDate to set
	 */
	public void setAppCreatedDate(Date appCreatedDate) {
		this.appCreatedDate = appCreatedDate;
	}

	/**
	 * @return the appUpdatedBy
	 */
	public int getAppUpdatedBy() {
		return appUpdatedBy;
	}

	/**
	 * @param appUpdatedBy
	 *            the appUpdatedBy to set
	 */
	public void setAppUpdatedBy(int appUpdatedBy) {
		this.appUpdatedBy = appUpdatedBy;
	}

	/**
	 * @return the appUpdatedDate
	 */
	public Date getAppUpdatedDate() {
		return appUpdatedDate;
	}

	/**
	 * @param appUpdatedDate
	 *            the appUpdatedDate to set
	 */
	public void setAppUpdatedDate(Date appUpdatedDate) {
		this.appUpdatedDate = appUpdatedDate;
	}

	/**
	 * @return the appStatus
	 */
	public int getAppStatus() {
		return appStatus;
	}

	/**
	 * @param appStatus
	 *            the appStatus to set
	 */
	public void setAppStatus(int appStatus) {
		this.appStatus = appStatus;
	}

	/**
	 * @return the appComment
	 */
	public String getAppComment() {
		return appComment;
	}

	/**
	 * @param appComment
	 *            the appComment to set
	 */
	public void setAppComment(String appComment) {
		this.appComment = appComment;
	}

}
