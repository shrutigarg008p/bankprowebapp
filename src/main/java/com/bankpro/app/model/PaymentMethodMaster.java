package com.bankpro.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "pv_payment_method_master")

@NamedQueries({
	@NamedQuery(name = PaymentMethodMaster.SELECT_PAYMENT_METHOD_LIST, query = "from PaymentMethodMaster ")
})

public class PaymentMethodMaster {
	
	public static final String SELECT_PAYMENT_METHOD_LIST = "PaymentMethodMaster.SELECT_PAYMENT_METHOD_LIST";
	
	@Id
	@GeneratedValue
	@Column(name = "pay_method_Id")
	private int paymentMethodId;
	
	@Column(name = "pay_method_name")
	private String payMethodName;
	
	@Column(name = "pay_method_desc")
	private String payMethodDesc;
	
	
	

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
	 * @return the payMethodName
	 */
	public String getPayMethodName() {
		return payMethodName;
	}

	/**
	 * @param payMethodName the payMethodName to set
	 */
	public void setPayMethodName(String payMethodName) {
		this.payMethodName = payMethodName;
	}

	/**
	 * @return the payMethodDesc
	 */
	public String getPayMethodDesc() {
		return payMethodDesc;
	}

	/**
	 * @param payMethodDesc the payMethodDesc to set
	 */
	public void setPayMethodDesc(String payMethodDesc) {
		this.payMethodDesc = payMethodDesc;
	}

}
