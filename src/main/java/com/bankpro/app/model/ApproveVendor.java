package com.bankpro.app.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Surendra
 *
 */

@Entity
@Table(name = "pv_vendor_approver")

@NamedQueries({
		@NamedQuery(name = ApproveVendor.FETCH_APPROVE_VENDOR_OBJ, query = "from ApproveVendor where vendorDetail.id = :vendorId "),
		@NamedQuery(name = ApproveVendor.FETCH_VENDOR_APPROVAL_HISTORY, query = "from ApproveVendor where userId = :userId ")
		})

public class ApproveVendor {

	public static final String FETCH_APPROVE_VENDOR_OBJ = "ApproveVendor.FETCH_APPROVE_VENDOR_OBJ";
	public static final String FETCH_VENDOR_APPROVAL_HISTORY = "ApproveVendor.FETCH_VENDOR_APPROVAL_HISTORY";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "user_id")
	private long userId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vendor_id")
	private Vendor vendorDetail;
	//private long vendorId;

	@Column(name = "created_by")
	private int createdBy;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "updated_by")
	private int updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "status")
	private String status;

	@Column(name = "comment")
	private String comment;

	public ApproveVendor() {

	}

	public ApproveVendor(long userId, Vendor vendorDetail, String status) {
		this.userId = userId;
		this.vendorDetail = vendorDetail;
		this.status = status;
	}

	public ApproveVendor(long userId, Vendor vendorDetail, String status, String comment) {
		this.userId = userId;
		this.vendorDetail = vendorDetail;
		this.status = status;
		this.comment = comment;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the vendorId
	 */
	public Vendor getVendorDetail() {
		return vendorDetail;
	}

	/**
	 * @param vendorId
	 *            the vendorId to set
	 */
	public void setVendorDetail(Vendor vendorDetail) {
		this.vendorDetail = vendorDetail;
	}

	/**
	 * @return the createdBy
	 */
	public int getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
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
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedBy
	 */
	public int getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate
	 *            the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

}
