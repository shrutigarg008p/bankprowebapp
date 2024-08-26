/**
 * 
 */
package com.bankpro.app.dto;

import com.bankpro.app.model.OrganizationDetail;

/**
 * @author Admin
 *
 */
public class FirstLoginRequiredDetailDTO {
	private String emailID;
	private boolean companyDialogFlag;
	private boolean bankDialogueFlag;
	private int orgID;
	private String orgName;

	public FirstLoginRequiredDetailDTO(String email, boolean companyDialogFlg,
			boolean bankDialogue, OrganizationDetail orgObj) {
		this.emailID = email;
		this.companyDialogFlag = companyDialogFlg;
		this.bankDialogueFlag = bankDialogue;
		this.orgID = orgObj.getOrgID();
		this.orgName = orgObj.getOrgName();

	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 *            the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the orgID
	 */
	public int getOrgID() {
		return orgID;
	}

	/**
	 * @param orgID
	 *            the orgID to set
	 */
	public void setOrgID(int orgID) {
		this.orgID = orgID;
	}

	/**
	 * @return the bankDialogueFlag
	 */
	public boolean isBankDialogueFlag() {
		return bankDialogueFlag;
	}

	/**
	 * @param bankDialogueFlag
	 *            the bankDialogueFlag to set
	 */
	public void setBankDialogueFlag(boolean bankDialogueFlag) {
		this.bankDialogueFlag = bankDialogueFlag;
	}

	/**
	 * @return the emailID
	 */
	public String getEmailID() {
		return emailID;
	}

	/**
	 * @param emailID
	 *            the emailID to set
	 */
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	/**
	 * @return the companyDialogFlag
	 */
	public boolean isCompanyDialogFlag() {
		return companyDialogFlag;
	}

	/**
	 * @param companyDialogFlag
	 *            the companyDialogFlag to set
	 */
	public void setCompanyDialogFlag(boolean companyDialogFlag) {
		this.companyDialogFlag = companyDialogFlag;
	}

}
