package com.bankpro.app.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "pv_payment_approver")
@NamedQueries({
		@NamedQuery(name = PaymentApprover.FETCH_PENDING_APPROVAL_PAYMENT_USERIDWISE, query = "from PaymentApprover where appStatus = 0 and paymentApprovalAccess = 1 and appUserID = :appUserID"),
		@NamedQuery(name = PaymentApprover.Find_PAYMENT_Approver_Based_On_PaymentID, query = "from PaymentApprover where payment.paymentId = :payment and  appStatus = 0 order by appID asc"),
		@NamedQuery(name = PaymentApprover.Find_PAYMENT_Approver_For_Declined_On_PayID, query = "from PaymentApprover where payment.paymentId = :payment")})
		
public class PaymentApprover {
	public static final String FETCH_PENDING_APPROVAL_PAYMENT_USERIDWISE = "PaymentApprover.FETCH_PENDING_APPROVAL_PAYMENT_USERIDWISE";
	public static final String Find_PAYMENT_Approver_Based_On_PaymentID = "PaymentApprover.Find_PAYMENT_Approver_Based_On_PaymentID";
	public static final String Find_PAYMENT_Approver_For_Declined_On_PayID = "PaymentApprover.Find_PAYMENT_Approver_For_Declined_On_PayID";

	@Id
	@GeneratedValue
	@Column(name = "app_id")
	private long appID;

	@Column(name = "app_userId")
	private long appUserID;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "app_paymentId")
	private Payment payment;

	@Column(name = "app_status")
	private int appStatus;

	@Column(name = "app_comment")
	private String appComment;

	@Column(name = "app_paymentAccess")
	private int paymentApprovalAccess;

	@Column(name = "app_createdBy")
	private int appCreatedBy;

	@Column(name = "app_CreatedDate")
	private Date appCreatedDate;

	@Column(name = "app_updatedBy")
	private int appUpdatedBy;

	@Column(name = "app_updatedDate")
	private Date appUpdatedDate;

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
	 * @return the payment
	 */
	public Payment getPayment() {
		return payment;
	}

	/**
	 * @param payment
	 *            the payment to set
	 */
	public void setPayment(Payment payment) {
		this.payment = payment;
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

	/**
	 * @return the paymentApprovalAccess
	 */
	public int getPaymentApprovalAccess() {
		return paymentApprovalAccess;
	}

	/**
	 * @param paymentApprovalAccess
	 *            the paymentApprovalAccess to set
	 */
	public void setPaymentApprovalAccess(int paymentApprovalAccess) {
		this.paymentApprovalAccess = paymentApprovalAccess;
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

}