package com.bankpro.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Surendra
 *
 */
@Entity
@Table (name = "pv_invoice_type_master")
@NamedQueries({
	@NamedQuery(name = InvoiceTypeMaster.FETCH_INVOICE_TYPE_LIST, query = "from InvoiceTypeMaster")
})

public class InvoiceTypeMaster {
	
	public static final String FETCH_INVOICE_TYPE_LIST = "InvoiceTypeMaster.FETCH_INVOICE_TYPE_LIST";

	@Id
	@GeneratedValue
	@Column(name = "itm_ID")
	private long invoiceTypeId;
	
	@Column(name = "itm_name")
	private String invoiceTypeName;
	
	

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
