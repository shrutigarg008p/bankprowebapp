package com.bankpro.app.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.Employee;
import com.bankpro.app.model.VendorTypeMaster;

/**
 * @author Surendra
 *
 */

public class VendorTypeDTO {

	private int vendorTypeId;
	private String vendorType;
	private String vendorTypeName;
	
	
	
	public VendorTypeDTO () {
		
	}
	
	public VendorTypeDTO (VendorTypeMaster vendorTypeMaster) {
		
		this.vendorTypeId = vendorTypeMaster.getVtmId();
		this.vendorType = vendorTypeMaster.getVtmType();
		this.vendorTypeName = vendorTypeMaster.getVtmName();
		
	}
	
	public static VendorTypeDTO mapVendorType(VendorTypeMaster vendorTypeMaster) {
		return new VendorTypeDTO(vendorTypeMaster);
	}
	
	public static List<VendorTypeDTO> mapFromVendorTypeEntities(List<VendorTypeMaster> vendorType) {
		return vendorType.stream().map((vendorTypeMaster) -> mapVendorType(vendorTypeMaster)).collect(Collectors.toList());
	}
	
	
	
	/**
	 * @return the vendorTypeId
	 */
	public int getVendorTypeId() {
		return vendorTypeId;
	}
	/**
	 * @param vendorTypeId the vendorTypeId to set
	 */
	public void setVendorTypeId(int vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}
	/**
	 * @return the vendorType
	 */
	public String getVendorType() {
		return vendorType;
	}
	/**
	 * @param vendorType the vendorType to set
	 */
	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}
	/**
	 * @return the vendorTypeName
	 */
	public String getVendorTypeName() {
		return vendorTypeName;
	}
	/**
	 * @param vendorTypeName the vendorTypeName to set
	 */
	public void setVendorTypeName(String vendorTypeName) {
		this.vendorTypeName = vendorTypeName;
	}
	
}
