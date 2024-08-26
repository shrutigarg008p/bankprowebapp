package com.bankpro.app.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.RoleMaster;

public class RoleMasterDTO {
	private int roleID;
	private String roleName;

	private String roleDesciption;
	private int roleCreatedBy;
	private Date roleCreatedDate;
	private int roleUpdatedBy;
	private Date roleUpdatedDate;
	private int orgId;
	private String userName;
	private String userEmail;
	private String contactNo;

	private List<?> selectedPermissionList = new ArrayList<>();

	/*
	 * public RoleMasterDTO(int roleIDD, String roleNamee) { this.roleID =
	 * roleIDD; this.roleName = roleNamee;
	 * 
	 * }
	 */

	public RoleMasterDTO(String userPermission, String userName, String userEmail, String contactNo, long orgId) {
		this.roleDesciption = userPermission;
		this.userName = userName;
		this.userEmail = userEmail;
		this.contactNo = contactNo;
		this.orgId = (int) orgId;
	}

	public RoleMasterDTO(RoleMaster userRole) {
		this.roleID = userRole.getRoleId();
		this.roleName = userRole.getRoleName();

		this.roleDesciption = userRole.getRoleDesciption();
		this.roleCreatedBy = userRole.getRoleCreatedBy();
		this.roleCreatedDate = userRole.getRoleCreatedDate();
		this.roleUpdatedBy = userRole.getRoleUpdatedBy();
		this.roleUpdatedDate = userRole.getRoleUpdatedDate();
		this.orgId = userRole.getOrgId();

	}

	public static RoleMasterDTO mapFromMealEntity(RoleMaster userRole) {
		// return new RoleMasterDTO(userRole.getRoleId(),
		// userRole.getRoleName());
		return new RoleMasterDTO(userRole);
	}

	public RoleMasterDTO() {

	}

	public static List<RoleMasterDTO> mapFromRoleUserEntities(
			List<RoleMaster> roleUser) {
		return roleUser.stream()
				.map((roleUserr) -> mapFromMealEntity(roleUserr))
				.collect(Collectors.toList());
	}


	/**
	 * @return the contactNo
	 */
	public String getContactNo() {
		return contactNo;
	}

	/**
	 * @param contactNo
	 *            the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	/**
	 * @return the roleID
	 */
	public int getRoleID() {
		return roleID;
	}

	/**
	 * @param roleID
	 *            the roleID to set
	 */
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleDesciption
	 */
	public String getRoleDesciption() {
		return roleDesciption;
	}

	/**
	 * @param roleDesciption
	 *            the roleDesciption to set
	 */
	public void setRoleDesciption(String roleDesciption) {
		this.roleDesciption = roleDesciption;
	}

	/**
	 * @return the roleCreatedBy
	 */
	public int getRoleCreatedBy() {
		return roleCreatedBy;
	}

	/**
	 * @param roleCreatedBy
	 *            the roleCreatedBy to set
	 */
	public void setRoleCreatedBy(int roleCreatedBy) {
		this.roleCreatedBy = roleCreatedBy;
	}

	/**
	 * @return the roleCreatedDate
	 */
	public Date getRoleCreatedDate() {
		return roleCreatedDate;
	}

	/**
	 * @param roleCreatedDate
	 *            the roleCreatedDate to set
	 */
	public void setRoleCreatedDate(Date roleCreatedDate) {
		this.roleCreatedDate = roleCreatedDate;
	}

	/**
	 * @return the roleUpdatedBy
	 */
	public int getRoleUpdatedBy() {
		return roleUpdatedBy;
	}

	/**
	 * @param roleUpdatedBy
	 *            the roleUpdatedBy to set
	 */
	public void setRoleUpdatedBy(int roleUpdatedBy) {
		this.roleUpdatedBy = roleUpdatedBy;
	}

	/**
	 * @return the roleUpdatedDate
	 */
	public Date getRoleUpdatedDate() {
		return roleUpdatedDate;
	}

	/**
	 * @param roleUpdatedDate
	 *            the roleUpdatedDate to set
	 */
	public void setRoleUpdatedDate(Date roleUpdatedDate) {
		this.roleUpdatedDate = roleUpdatedDate;
	}

	/**
	 * @return the orgId
	 */
	public int getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the selectedPermissionList
	 */
	public List<?> getSelectedPermissionList() {
		return selectedPermissionList;
	}

	/**
	 * @param selectedPermissionList
	 *            the selectedPermissionList to set
	 */
	public void setSelectedPermissionList(List<?> selectedPermissionList) {
		this.selectedPermissionList = selectedPermissionList;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}



	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail
	 *            the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
