/**
 * 
 */
package com.bankpro.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Admin
 *
 */
@Entity
@Table(name = "pv_vendor_type_master")

@NamedQueries({
	@NamedQuery(name = VendorTypeMaster.FETCH_VENDOR_TYPE_LIST, query = "from VendorTypeMaster" )})

public class VendorTypeMaster {
	
	public static final String FETCH_VENDOR_TYPE_LIST = "VendorTypeMaster.FETCH_VENDOR_TYPE_LIST";
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int vtmId;
	@Column(name = "vendor_type")
	private String vtmType;
	@Column(name = "vendor_type_name")
	private String vtmName;

	/**
	 * @return the vtmId
	 */
	public int getVtmId() {
		return vtmId;
	}

	/**
	 * @param vtmId
	 *            the vtmId to set
	 */
	public void setVtmId(int vtmId) {
		this.vtmId = vtmId;
	}

	/**
	 * @return the vtmType
	 */
	public String getVtmType() {
		return vtmType;
	}

	/**
	 * @param vtmType
	 *            the vtmType to set
	 */
	public void setVtmType(String vtmType) {
		this.vtmType = vtmType;
	}

	/**
	 * @return the vtmName
	 */
	public String getVtmName() {
		return vtmName;
	}

	/**
	 * @param vtmName
	 *            the vtmName to set
	 */
	public void setVtmName(String vtmName) {
		this.vtmName = vtmName;
	}

}
