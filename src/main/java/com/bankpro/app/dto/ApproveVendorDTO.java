package com.bankpro.app.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bankpro.app.model.ApproveVendor;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ApproveVendorDTO {
	
	private long vendorId;
	private String comment;
	private String status;
	private int createdBy;
	private String createdByName;
	
	@JsonFormat(pattern = "dd/MM/YYYY")
	private Date createdDate;
	
	
	
	public ApproveVendorDTO() {
		
	}
	
	public ApproveVendorDTO(ApproveVendor approveVendor, Map<Integer, String> mapForUserNameWithID) {
		this.comment = approveVendor.getComment();
		this.createdDate = approveVendor.getCreatedDate();
		this.status = approveVendor.getStatus();
		this.createdBy = approveVendor.getCreatedBy();
		this.createdByName = mapForUserNameWithID.get((int)approveVendor.getUserId());
	}
	
	public static ApproveVendorDTO mapApproveVendor(ApproveVendor approveVendor, Map<Integer, String> mapForUserNameWithID) {
		return new ApproveVendorDTO(approveVendor, mapForUserNameWithID);
	}
	
	public static List<ApproveVendorDTO> mapFromApproverVendEntities(List<ApproveVendor> apprVend, Map<Integer, String> mapForUserNameWithID) {
		return apprVend.stream().map((approveVendor) -> mapApproveVendor(approveVendor, mapForUserNameWithID)).collect(Collectors.toList());
	}
	
	
	
		
	/**
	 * @return the createdByName
	 */
	public String getCreatedByName() {
		return createdByName;
	}

	/**
	 * @param createdByName the createdByName to set
	 */
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the createdBy
	 */
	public int getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the vendorId
	 */
	public long getVendorId() {
		return vendorId;
	}
	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	

}
