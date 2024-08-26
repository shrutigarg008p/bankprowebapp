package com.bankpro.app.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.PaymentMethodMaster;
import com.bankpro.app.model.TermMaster;

public class PaymentMethodMasterDTO {

	private int paymentMethodId;
	private String paymentMethodName;
	private String paymentMethodDesc;
	
	
	public PaymentMethodMasterDTO() {
		
	}
	
	public PaymentMethodMasterDTO(PaymentMethodMaster payMethodMaster) {
	
		this.paymentMethodId = payMethodMaster.getPaymentMethodId();
		this.paymentMethodName = payMethodMaster.getPayMethodName();
		this.paymentMethodDesc = payMethodMaster.getPayMethodDesc();
		
	}
	
	public static PaymentMethodMasterDTO mapPaymentMethod(PaymentMethodMaster payMethodMaster) {
		return new PaymentMethodMasterDTO(payMethodMaster);
	}
	
	public static List<PaymentMethodMasterDTO> mapFromPaymentMethodEntities(List<PaymentMethodMaster> paymentMaster) {
		return paymentMaster.stream().map((payMethodMaster) -> mapPaymentMethod(payMethodMaster)).collect(Collectors.toList());
	}
	
	
	
	/**
	 * @return the paymentMethodId
	 */
	public int getPaymentMethodId() {
		return paymentMethodId;
	}

	/**
	 * @param paymentMethodId the paymentMethodId to set
	 */
	public void setPaymentMethodId(int paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	/**
	 * @return the paymentMethodName
	 */
	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	/**
	 * @param paymentMethodName the paymentMethodName to set
	 */
	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	/**
	 * @return the paymentMethodDesc
	 */
	public String getPaymentMethodDesc() {
		return paymentMethodDesc;
	}

	/**
	 * @param paymentMethodDesc the paymentMethodDesc to set
	 */
	public void setPaymentMethodDesc(String paymentMethodDesc) {
		this.paymentMethodDesc = paymentMethodDesc;
	}
	
	
}
