package com.bankpro.app.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.CompanyTypeMaster;
import com.bankpro.app.model.OrganizationDetail;

public class OrganizationDetailDTO {

	private long orgId;
	private String orgName;
	
	
	public OrganizationDetailDTO() {
		
	}
	
	public OrganizationDetailDTO(long id, String name) {
		this.orgId = id;
		this.orgName = name;
		
	}
	
	
	
	public static OrganizationDetailDTO mapFromOrgDetailEntities(long id, String name) {
		return new OrganizationDetailDTO(id, name);
	}
	
	
	/**
	 * @return the orgId
	 */
	public long getOrgId() {
		return orgId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
