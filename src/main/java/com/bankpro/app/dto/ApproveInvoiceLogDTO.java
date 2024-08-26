package com.bankpro.app.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bankpro.app.model.InvoiceApprover;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ApproveInvoiceLogDTO {

	private long appInvoiceID;
	private int appCreatedBy;
	private int appStatus;
	private String comment;
	private String appCreatedUserName;

	@JsonFormat(pattern = "dd/MM/yyyy hh:mm a", timezone = "IST")
	private Date appCreatedDate;

	public ApproveInvoiceLogDTO() {

	}

	public ApproveInvoiceLogDTO(InvoiceApprover invApprover, Map<Integer, String> mapForUserNameWithID) {

		this.appInvoiceID = invApprover.getAppInvoiceID();
		this.appCreatedBy = invApprover.getAppCreatedBy();
		this.appStatus = invApprover.getAppStatus();
		this.comment = invApprover.getAppComment();
		this.appCreatedDate = invApprover.getAppCreatedDate();
		this.appCreatedUserName = mapForUserNameWithID.get(invApprover.getAppCreatedBy());

	}

	public static ApproveInvoiceLogDTO mapInvoiceApprover(
			InvoiceApprover invoiceApprover,
			Map<Integer, String> mapForUserNameWithID) {
		return new ApproveInvoiceLogDTO(invoiceApprover, mapForUserNameWithID);
	}

	public static List<ApproveInvoiceLogDTO> mapFromApproverInvEntities(
			List<InvoiceApprover> apprInv,
			Map<Integer, String> mapForUserNameWithID) {
		return apprInv
				.stream().map((invoiceApprover) -> mapInvoiceApprover(invoiceApprover,
						mapForUserNameWithID)).collect(Collectors.toList());
	}

	/**
	 * @return the appCreatedUserName
	 */
	public String getAppCreatedUserName() {
		return appCreatedUserName;
	}

	/**
	 * @param appCreatedUserName
	 *            the appCreatedUserName to set
	 */
	public void setAppCreatedUserName(String appCreatedUserName) {
		this.appCreatedUserName = appCreatedUserName;
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
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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

}
