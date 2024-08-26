package com.bankpro.app.dto;

import java.util.ArrayList;
import java.util.List;

public class PaymentApproverUserDTO {

	private long userId;
	private String userName;
	
	public PaymentApproverUserDTO(long userId,String firstName, String lastName) {
		this.userId = userId;
		this.userName = firstName+" " + lastName;
	}
	
	public static List<PaymentApproverUserDTO> mapFromPaymentApproverEntity(List userArray) {
		List<PaymentApproverUserDTO> paymentApp = new ArrayList();
		for (Object obj : userArray) {
			Object[] rowObj = (Object[]) obj;
			paymentApp.add(new PaymentApproverUserDTO(Long.valueOf(rowObj[0].toString()), rowObj[1].toString(), rowObj[2].toString()));
		}
		return paymentApp;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
