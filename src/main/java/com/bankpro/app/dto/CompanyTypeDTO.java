package com.bankpro.app.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.CompanyTypeMaster;
import com.bankpro.app.model.VendorTypeMaster;

public class CompanyTypeDTO {

	private int compId;
	private String compType;
	private String compName;
	
	
	public CompanyTypeDTO() {
		
	}
	
	public CompanyTypeDTO(CompanyTypeMaster compMaster) {
		
		this.compId = compMaster.getCompId();
		this.compType = compMaster.getCompType();
		this.compName = compMaster.getCompName();
	}
	
	public static CompanyTypeDTO mapCompanyType(CompanyTypeMaster companyTypeMaster) {
		return new CompanyTypeDTO(companyTypeMaster);
	}
	
	public static List<CompanyTypeDTO> mapFromCompanyTypeEntities(List<CompanyTypeMaster> companyType) {
		return companyType.stream().map((CompanyTypeMaster) -> mapCompanyType(CompanyTypeMaster)).collect(Collectors.toList());
	}
	
	
	/**
	 * @return the compId
	 */
	public int getCompId() {
		return compId;
	}
	/**
	 * @param compId the compId to set
	 */
	public void setCompId(int compId) {
		this.compId = compId;
	}
	/**
	 * @return the compType
	 */
	public String getCompType() {
		return compType;
	}
	/**
	 * @param compType the compType to set
	 */
	public void setCompType(String compType) {
		this.compType = compType;
	}
	/**
	 * @return the compName
	 */
	public String getCompName() {
		return compName;
	}
	/**
	 * @param compName the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	
}
