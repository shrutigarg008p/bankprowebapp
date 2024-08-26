package com.bankpro.app.dto;

import java.util.ArrayList;
import java.util.List;


public class InvoiceApproverUserDTO {
	private long appUserID;
	private String appName;
	
	public InvoiceApproverUserDTO(long appId,String firstName, String lastName) {
		this.appName = firstName+" "+lastName;
		this.appUserID = appId;
	}

	public static List<InvoiceApproverUserDTO> mapFromApproverEntity(List vendorNameArray) {
		List<InvoiceApproverUserDTO> li = new ArrayList();
		for (Object obj : vendorNameArray) {
			Object[] rowObj = (Object[]) obj;
			li.add(new InvoiceApproverUserDTO(Long.valueOf(rowObj[0].toString()), rowObj[1].toString(), rowObj[2].toString()));
		}
		return li;
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
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

}
