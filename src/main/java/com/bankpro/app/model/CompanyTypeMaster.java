package com.bankpro.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "pv_company_type_master")

@NamedQueries({
	@NamedQuery(name = CompanyTypeMaster.FETCH_COMPANY_TYPE_LIST, query = "from CompanyTypeMaster")
})

public class CompanyTypeMaster {
	
	public static final String FETCH_COMPANY_TYPE_LIST = "CompanyTypeMaster.FETCH_COMPANY_TYPE_LIST";
	
	@Id
	@GeneratedValue
	@Column(name = "company_id")
	private int compId;
	@Column(name = "company_type")
	private String compType;
	@Column(name = "company_name")
	private String compName;

	/**
	 * @return the compId
	 */
	public int getCompId() {
		return compId;
	}

	/**
	 * @param compId
	 *            the compId to set
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
	 * @param compType
	 *            the compType to set
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
	 * @param compName
	 *            the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

}
