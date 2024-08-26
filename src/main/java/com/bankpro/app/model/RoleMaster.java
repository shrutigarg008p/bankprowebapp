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
@Table(name = "pv_role_master")
@NamedQueries({
	@NamedQuery(name = RoleMaster.role_Master, query = "from RoleMaster where roleStatus = :roleStatus"),
	@NamedQuery(name = RoleMaster.value_Array_For_Role, query = "select roleId,  roleName from RoleMaster where roleStatus = :roleStatus"),
	@NamedQuery(name = RoleMaster.FETCH_ROLE_LIST, query = "from RoleMaster where orgId = :orgId AND roleStatus = 'Y' ")
})
public class RoleMaster {
	public static final String role_Master = "RoleMaster.role";
	public static final String value_Array_For_Role = "RoleMaster.value_Array_For_Role";
	public static final String FETCH_ROLE_LIST = "RoleMaster.FETCH_ROLE_LIST";
	
	@Id
	@GeneratedValue
	@Column(name = "rm_id")
	private int roleId;

	@Column(name = "rm_name")
	private String roleName;

	@Column(name = "rm_description")
	private String roleDesciption;

	@Column(name = "rm_status")
	private String roleStatus;

	@Column(name = "rm_createdBy")
	private int roleCreatedBy;

	@Column(name = "rm_createdDate")
	private Date roleCreatedDate = new Date();

	@Column(name = "rm_updatedBy")
	private int roleUpdatedBy;

	@Column(name = "rm_updatedDate")
	private Date roleUpdatedDate;
	
	@Column(name = "rm_orgId")
	private int orgId;


	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesciption() {
		return roleDesciption;
	}

	public void setRoleDesciption(String roleDesciption) {
		this.roleDesciption = roleDesciption;
	}

	public String getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}

	public int getRoleCreatedBy() {
		return roleCreatedBy;
	}

	public void setRoleCreatedBy(int roleCreatedBy) {
		this.roleCreatedBy = roleCreatedBy;
	}

	public Date getRoleCreatedDate() {
		return roleCreatedDate;
	}

	public void setRoleCreatedDate(Date roleCreatedDate) {
		this.roleCreatedDate = roleCreatedDate;
	}

	public int getRoleUpdatedBy() {
		return roleUpdatedBy;
	}

	public void setRoleUpdatedBy(int roleUpdatedBy) {
		this.roleUpdatedBy = roleUpdatedBy;
	}

	public Date getRoleUpdatedDate() {
		return roleUpdatedDate;
	}

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
	 * @param orgId the orgId to set
	 */
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

}
