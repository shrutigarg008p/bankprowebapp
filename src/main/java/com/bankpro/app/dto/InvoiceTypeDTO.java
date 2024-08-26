package com.bankpro.app.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.InvoiceTypeMaster;
import com.bankpro.app.model.VendorTypeMaster;

/**
 * @author Surendra
 *
 */

public class InvoiceTypeDTO {
	
	private long invoiceTypeId;
	private String invoiceTypeName;
	
	
	public InvoiceTypeDTO() {
		
	}
	
	public InvoiceTypeDTO (InvoiceTypeMaster invTypeMaster) {
		
		this.invoiceTypeId = invTypeMaster.getInvoiceTypeId();
		this.invoiceTypeName = invTypeMaster.getInvoiceTypeName();
		
	}
	
	public static InvoiceTypeDTO mapInvoiceType(InvoiceTypeMaster invoiceTypeMaster) {
		return new InvoiceTypeDTO(invoiceTypeMaster);
	}
	
	public static List<InvoiceTypeDTO> mapFromInvoiceTypeEntities(List<InvoiceTypeMaster> invoiceType) {
		return invoiceType.stream().map((InvoiceTypeMaster) -> mapInvoiceType(InvoiceTypeMaster)).collect(Collectors.toList());
	}
	
	
	/**
	 * @return the invoiceTypeId
	 */
	public long getInvoiceTypeId() {
		return invoiceTypeId;
	}
	/**
	 * @param invoiceTypeId the invoiceTypeId to set
	 */
	public void setInvoiceTypeId(long invoiceTypeId) {
		this.invoiceTypeId = invoiceTypeId;
	}
	/**
	 * @return the invoiceTypeName
	 */
	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}
	/**
	 * @param invoiceTypeName the invoiceTypeName to set
	 */
	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

}
