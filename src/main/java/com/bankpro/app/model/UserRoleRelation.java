/**
 * 
 */
package com.bankpro.app.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Admin
 *
 */
@Entity
@Table(name = "pv_user_role_relation")
public class UserRoleRelation {
	@Id
	@GeneratedValue
	@Column(name = "urr_userRoleId")
	private int urrId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private Users user;

	@Column(name = "urr_roleId")
	private int urr_roleID;

	@Column(name = "urr_createdBy")
	private int urrCreatedBy;

	@Column(name = "urr_createdDate")
	private Date urrCreatedDate;

	@Column(name = "urr_updatedBy")
	private int urrUpdatedBy;

	@Column(name = "urr_updatedDate")
	private Date urrUpdatedDate;


	/**
	 * @return the urr_roleID
	 */
	public int getUrr_roleID() {
		return urr_roleID;
	}

	/**
	 * @param urr_roleID
	 *            the urr_roleID to set
	 */
	public void setUrr_roleID(int urr_roleID) {
		this.urr_roleID = urr_roleID;
	}

	/**
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * @return the urrId
	 */
	public int getUrrId() {
		return urrId;
	}

	/**
	 * @param urrId
	 *            the urrId to set
	 */
	public void setUrrId(int urrId) {
		this.urrId = urrId;
	}

	/**
	 * @return the urrCreatedBy
	 */
	public int getUrrCreatedBy() {
		return urrCreatedBy;
	}

	/**
	 * @param urrCreatedBy
	 *            the urrCreatedBy to set
	 */
	public void setUrrCreatedBy(int urrCreatedBy) {
		this.urrCreatedBy = urrCreatedBy;
	}

	/**
	 * @return the urrCreatedDate
	 */
	public Date getUrrCreatedDate() {
		return urrCreatedDate;
	}

	/**
	 * @param urrCreatedDate
	 *            the urrCreatedDate to set
	 */
	public void setUrrCreatedDate(Date urrCreatedDate) {
		this.urrCreatedDate = urrCreatedDate;
	}

	/**
	 * @return the urrUpdatedBy
	 */
	public int getUrrUpdatedBy() {
		return urrUpdatedBy;
	}

	/**
	 * @param urrUpdatedBy
	 *            the urrUpdatedBy to set
	 */
	public void setUrrUpdatedBy(int urrUpdatedBy) {
		this.urrUpdatedBy = urrUpdatedBy;
	}

	/**
	 * @return the urrUpdatedDate
	 */
	public Date getUrrUpdatedDate() {
		return urrUpdatedDate;
	}

	/**
	 * @param urrUpdatedDate
	 *            the urrUpdatedDate to set
	 */
	public void setUrrUpdatedDate(Date urrUpdatedDate) {
		this.urrUpdatedDate = urrUpdatedDate;
	}

}
