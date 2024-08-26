/**
 * 
 */
package com.bankpro.app.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.AuditLogTrail;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Admin
 *
 */
public class AuditTrailLogDTO {
	private String operationType;
	private String actionType;
	private String fieldType;
	private String oldValue;
	private String newValue;
	
	@JsonFormat(pattern = "dd/MM/yyyy hh:mm a", timezone = "IST")
	private Date creationDateTime;
	private String createdBy;

	public AuditTrailLogDTO(AuditLogTrail auditObj) {
		this.operationType = auditObj.getAuditOperationType();
		this.actionType = auditObj.getAuditActionType();
		this.fieldType = auditObj.getAuditFieldType();
		this.oldValue = auditObj.getAuditOldValue();
		this.newValue = auditObj.getAuditNewValue();
		this.creationDateTime = auditObj.getAuditCreatedDate();
		this.createdBy = auditObj.getAuditCreatedBy();

	}

	public static AuditTrailLogDTO mapAuditEntity(AuditLogTrail audit) {
		return new AuditTrailLogDTO(audit);
	}

	public static List<AuditTrailLogDTO> mapAuditList(
			List<AuditLogTrail> mapAuditList) {
		return mapAuditList.stream()
				.map((mapAuditListt) -> mapAuditEntity(mapAuditListt))
				.collect(Collectors.toList());
	}

	/**
	 * @return the operationType
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType
	 *            the operationType to set
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return the actionType
	 */
	public String getActionType() {
		return actionType;
	}

	/**
	 * @param actionType
	 *            the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	/**
	 * @return the fieldType
	 */
	public String getFieldType() {
		return fieldType;
	}

	/**
	 * @param fieldType
	 *            the fieldType to set
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * @return the oldValue
	 */
	public String getOldValue() {
		return oldValue;
	}

	/**
	 * @param oldValue
	 *            the oldValue to set
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	/**
	 * @return the newValue
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * @param newValue
	 *            the newValue to set
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	/**
	 * @return the creationDateTime
	 */
	public Date getCreationDateTime() {
		return creationDateTime;
	}

	/**
	 * @param creationDateTime
	 *            the creationDateTime to set
	 */
	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
