/**
 * 
 */
package com.bankpro.app.model;

import java.util.Date;

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
@Table(name = "pv_audit_log")
@NamedQueries({ @NamedQuery(name = AuditLogTrail.auditObject, query = "from AuditLogTrail where auditOperationType = :auditOperationType and auditCombineReferenceId = :auditCombineReferenceId")})
public class AuditLogTrail {
	public static final String auditObject = "auditLogTrail.auditObject";
		
	@Id
	@GeneratedValue
	@Column(name = "audit_Id")
	private int auditId;

	@Column(name = "audit_operationType")
	private String auditOperationType;

	@Column(name = "audit_ActionType")
	private String auditActionType;

	@Column(name = "audit_FieldType")
	private String auditFieldType;

	@Column(name = "audit_OldValue")
	private String auditOldValue;

	@Column(name = "audit_NewValue")
	private String auditNewValue;

	@Column(name = "audit_CreatedBy")
	private String auditCreatedBy;

	@Column(name = "audit_CreatedDate")
	private Date auditCreatedDate = new Date();

	@Column(name = "audit_CombineReferenceId")
	private long auditCombineReferenceId;

	public AuditLogTrail() {

	}

	public AuditLogTrail(String opertionType, String actionType,
			String fieldType, String OldValue, String NewValue,
			String createdBy, long refereceId) {
		this.auditOperationType = opertionType;
		this.auditActionType = actionType;
		this.auditFieldType = fieldType;
		this.auditOldValue = OldValue;
		this.auditNewValue = NewValue;
		this.auditCreatedBy = createdBy;
		this.auditCombineReferenceId = refereceId;

	}

	/**
	 * @return the auditCombineReferenceId
	 */
	public Long getAuditCombineReferenceId() {
		return auditCombineReferenceId;
	}

	/**
	 * @param auditCombineReferenceId
	 *            the auditCombineReferenceId to set
	 */
	public void setAuditCombineReferenceId(Long auditCombineReferenceId) {
		this.auditCombineReferenceId = auditCombineReferenceId;
	}

	/**
	 * @return the auditId
	 */
	public int getAuditId() {
		return auditId;
	}

	/**
	 * @param auditId
	 *            the auditId to set
	 */
	public void setAuditId(int auditId) {
		this.auditId = auditId;
	}

	/**
	 * @return the auditOperationType
	 */
	public String getAuditOperationType() {
		return auditOperationType;
	}

	/**
	 * @param auditOperationType
	 *            the auditOperationType to set
	 */
	public void setAuditOperationType(String auditOperationType) {
		this.auditOperationType = auditOperationType;
	}

	/**
	 * @return the auditActionType
	 */
	public String getAuditActionType() {
		return auditActionType;
	}

	/**
	 * @param auditActionType
	 *            the auditActionType to set
	 */
	public void setAuditActionType(String auditActionType) {
		this.auditActionType = auditActionType;
	}

	/**
	 * @return the auditFieldType
	 */
	public String getAuditFieldType() {
		return auditFieldType;
	}

	/**
	 * @param auditFieldType
	 *            the auditFieldType to set
	 */
	public void setAuditFieldType(String auditFieldType) {
		this.auditFieldType = auditFieldType;
	}

	/**
	 * @return the auditOldValue
	 */
	public String getAuditOldValue() {
		return auditOldValue;
	}

	/**
	 * @param auditOldValue
	 *            the auditOldValue to set
	 */
	public void setAuditOldValue(String auditOldValue) {
		this.auditOldValue = auditOldValue;
	}

	/**
	 * @return the auditNewValue
	 */
	public String getAuditNewValue() {
		return auditNewValue;
	}

	/**
	 * @param auditNewValue
	 *            the auditNewValue to set
	 */
	public void setAuditNewValue(String auditNewValue) {
		this.auditNewValue = auditNewValue;
	}

	/**
	 * @return the auditCreatedBy
	 */
	public String getAuditCreatedBy() {
		return auditCreatedBy;
	}

	/**
	 * @param auditCreatedBy
	 *            the auditCreatedBy to set
	 */
	public void setAuditCreatedBy(String auditCreatedBy) {
		this.auditCreatedBy = auditCreatedBy;
	}

	/**
	 * @return the auditCreatedDate
	 */
	public Date getAuditCreatedDate() {
		return auditCreatedDate;
	}

	/**
	 * @param auditCreatedDate
	 *            the auditCreatedDate to set
	 */
	public void setAuditCreatedDate(Date auditCreatedDate) {
		this.auditCreatedDate = auditCreatedDate;
	}

}
