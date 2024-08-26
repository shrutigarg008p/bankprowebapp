package com.bankpro.app.dto;

import java.util.ArrayList;
import java.util.List;

import com.bankpro.app.model.Users;

public class BatchErrorFileUploadDTO {
	private int serialNo;
	private String columnName;
	private String rowNo;
	private String fieldValue;
	private String errorMsg;
	
	public BatchErrorFileUploadDTO(){
		
	}

	public BatchErrorFileUploadDTO(int counter, String columnName, String rowNo, String fieldValue, String errorMsg) {
		this.serialNo = counter;
		this.columnName = columnName;
		this.rowNo = rowNo;
		this.fieldValue = fieldValue;
		this.errorMsg = errorMsg;
	}

	public static List<BatchErrorFileUploadDTO> mapFromBatchUploadWithError(List<String[]> vendorNameArray) {
		List<BatchErrorFileUploadDTO> li = new ArrayList();
		int counter = 0;
		for (String[] obj : vendorNameArray) {
			counter++;
			li.add(new BatchErrorFileUploadDTO(counter, obj[0], obj[1], obj[2], obj[3]));
		}
		return li;
	}

	/**
	 * @return the serialNo
	 */
	public int getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            the serialNo to set
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName
	 *            the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the rowNo
	 */
	public String getRowNo() {
		return rowNo;
	}

	/**
	 * @param rowNo
	 *            the rowNo to set
	 */
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}

	/**
	 * @return the fieldValue
	 */
	public String getFieldValue() {
		return fieldValue;
	}

	/**
	 * @param fieldValue
	 *            the fieldValue to set
	 */
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
