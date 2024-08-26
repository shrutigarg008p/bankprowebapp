package com.bankpro.app.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bankpro.app.model.NewBankDetails;
import com.bankpro.app.model.Users;

public class ApproveInvoiceDTO {
	private long venID;
	private long invoiceID;
	private String name;
	private String accountNo;
	private String ifsc;
	private String primaryEmail;
	private String paymentMethod;
	private String phnNo;

	public ApproveInvoiceDTO(long venID, Users benUserName, NewBankDetails bankObj, String primaryEmail, String paymentMeth, long invoiceId) {
		this.venID = venID;
		this.invoiceID = invoiceId;
		this.name = benUserName.getUserFirstName() + " "+ benUserName.getUserLastName();
		this.accountNo = bankObj.getBankAccountNo();
		this.ifsc = bankObj.getBankIFSCCode();
		this.primaryEmail = (primaryEmail == "null" ? null : primaryEmail);
		this.paymentMethod = (paymentMeth == "null" ? null : paymentMeth);
		this.phnNo = benUserName.getUserContectNo();
	}

	public static List<ApproveInvoiceDTO> mapFromApproverList(Map<String, Object[]> vendorNameArray) {
		List<ApproveInvoiceDTO> li = new ArrayList();
		for (Map.Entry<String, Object[]> entry : vendorNameArray.entrySet()) {
			long invoiceId = Long.valueOf(entry.getKey());
			Object[] obj = entry.getValue();
			li.add(new ApproveInvoiceDTO(Long.valueOf(obj[0].toString()), (Users) obj[1], (NewBankDetails) obj[2], String
							.valueOf(obj[3]), String.valueOf(obj[4]), invoiceId));
		}
		return li;
	}
	
	

	/**
	 * @return the venID
	 */
	public long getVenID() {
		return venID;
	}

	/**
	 * @param venID the venID to set
	 */
	public void setVenID(long venID) {
		this.venID = venID;
	}

	/**
	 * @return the invoiceID
	 */
	public long getInvoiceID() {
		return invoiceID;
	}

	/**
	 * @param invoiceID
	 *            the invoiceID to set
	 */
	public void setInvoiceID(long invoiceID) {
		this.invoiceID = invoiceID;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo
	 *            the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the ifsc
	 */
	public String getIfsc() {
		return ifsc;
	}

	/**
	 * @param ifsc
	 *            the ifsc to set
	 */
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	/**
	 * @return the primaryEmail
	 */
	public String getPrimaryEmail() {
		return primaryEmail;
	}

	/**
	 * @param primaryEmail
	 *            the primaryEmail to set
	 */
	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod
	 *            the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the phnNo
	 */
	public String getPhnNo() {
		return phnNo;
	}

	/**
	 * @param phnNo
	 *            the phnNo to set
	 */
	public void setPhnNo(String phnNo) {
		this.phnNo = phnNo;
	}

}