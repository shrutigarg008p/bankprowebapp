package com.bankpro.app.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.PaymentApprover;
import com.fasterxml.jackson.annotation.JsonFormat;

public class PaymentApprovalLogDTO {
	private long paymentID;
	private long payApprovalName;
	private int payAppStatus;
	private String payAppcomment;
	
	@JsonFormat(pattern = "dd/MM/yyyy hh:mm a", timezone = "IST")
	private Date payAppCreatedDate;

	public PaymentApprovalLogDTO() {

	}

	public PaymentApprovalLogDTO(PaymentApprover payAppObj) {

		// this.paymentID = payAppObj.get;
		this.payApprovalName = payAppObj.getAppUserID();
		this.payAppStatus = payAppObj.getAppStatus();
		this.payAppcomment = payAppObj.getAppComment();
		this.payAppCreatedDate = payAppObj.getAppCreatedDate();

	}

	public static PaymentApprovalLogDTO mapPaymentApprover(PaymentApprover payApprover) {
		return new PaymentApprovalLogDTO(payApprover);
	}

	public static List<PaymentApprovalLogDTO> approverPaymentLogEntities(List<PaymentApprover> appPayLogList) {
		return appPayLogList.stream().map((appPayLogListq) -> mapPaymentApprover(appPayLogListq)).collect(Collectors.toList());
	}

	/**
	 * @return the paymentID
	 */
	public long getPaymentID() {
		return paymentID;
	}

	/**
	 * @param paymentID
	 *            the paymentID to set
	 */
	public void setPaymentID(long paymentID) {
		this.paymentID = paymentID;
	}

	

	/**
	 * @return the payApprovalName
	 */
	public long getPayApprovalName() {
		return payApprovalName;
	}

	/**
	 * @param payApprovalName the payApprovalName to set
	 */
	public void setPayApprovalName(long payApprovalName) {
		this.payApprovalName = payApprovalName;
	}

	/**
	 * @return the payAppStatus
	 */
	public int getPayAppStatus() {
		return payAppStatus;
	}

	/**
	 * @param payAppStatus
	 *            the payAppStatus to set
	 */
	public void setPayAppStatus(int payAppStatus) {
		this.payAppStatus = payAppStatus;
	}

	/**
	 * @return the payAppcomment
	 */
	public String getPayAppcomment() {
		return payAppcomment;
	}

	/**
	 * @param payAppcomment
	 *            the payAppcomment to set
	 */
	public void setPayAppcomment(String payAppcomment) {
		this.payAppcomment = payAppcomment;
	}

	/**
	 * @return the payAppCreatedDate
	 */
	public Date getPayAppCreatedDate() {
		return payAppCreatedDate;
	}

	/**
	 * @param payAppCreatedDate
	 *            the payAppCreatedDate to set
	 */
	public void setPayAppCreatedDate(Date payAppCreatedDate) {
		this.payAppCreatedDate = payAppCreatedDate;
	}

}
