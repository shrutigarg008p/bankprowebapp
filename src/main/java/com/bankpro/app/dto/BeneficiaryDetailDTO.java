package com.bankpro.app.dto;

import java.util.ArrayList;
import java.util.List;

import com.bankpro.app.model.InvoiceDetails;
import com.bankpro.app.model.Users;

public class BeneficiaryDetailDTO {

	public String beneficiaryName;
	public long beneficryID;

	public BeneficiaryDetailDTO(long benId,String benUserName) {
		this.beneficiaryName = benUserName;
		this.beneficryID = benId;
	}

	public static List<BeneficiaryDetailDTO> mapFromvendorBeneficiryName(List vendorNameArray) {
		List<BeneficiaryDetailDTO> li = new ArrayList();
		for (Object obj : vendorNameArray) {
			Object[] rowObj = (Object[]) obj;
			li.add(new BeneficiaryDetailDTO(Long.valueOf(rowObj[0].toString()),rowObj[1].toString()));
		}
		return li;
	}

	/**
	 * @return the beneficiaryName
	 */
	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	/**
	 * @param beneficiaryName
	 *            the beneficiaryName to set
	 */
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	/**
	 * @return the beneficryID
	 */
	public long getBeneficryID() {
		return beneficryID;
	}

	/**
	 * @param beneficryID
	 *            the beneficryID to set
	 */
	public void setBeneficryID(long beneficryID) {
		this.beneficryID = beneficryID;
	}

}
