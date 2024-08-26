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

import org.hibernate.annotations.Cascade;

import com.bankpro.app.dto.BankDetailsDTO;

/**
 * @author Surendra
 *
 */

@Entity
@Table(name = "pv_employee")
@NamedQueries({
	@NamedQuery(name = Employee.FETCH_ALL_EMPLOYEE, query = "from Employee where orgId = :orgId" ),
	@NamedQuery(name = Employee.GET_EMP_BY_ID, query = "from Employee where empId = :empId")
	//,@NamedQuery(name = Employee.SEARCH_BY_NAME, query = "from Employee where userDetail.firstName like :firstName")
})
public class Employee {
	
	public static final String FETCH_ALL_EMPLOYEE = "Employee.FETCH_ALL_EMPLOYEE";
	public static final String GET_EMP_BY_ID = "Employee.GET_EMP_BY_ID";
	public static final String SEARCH_BY_NAME = "Employee.SEARCH_BY_NAME";
	
	
	@Id
	@GeneratedValue
	@Column(name = "emp_id")
	private int empId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private Users userDetail;
	
	@Column(name = "created_by")
	private int createdBy;
	
	@Column(name = "created_date")
	private Date createdDt;
	
	@Column(name = "updated_by")
	private int updatedBy;
	
	@Column(name = "updated_date")
	private Date updatedDt;
	
	@Column(name = "pan_number")
	private String panNumber;
	
	@Column(name = "payment_method")
	private String paymentMethod;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bank_id")
	private NewBankDetails bankDetail;
	
	@Column(name = "org_id")
	private int orgId;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "mm_id")
	private String mmId;
	
	@Column(name = "emp_number")
	private int empNumber;

	/**
	 * @return the empId
	 */
	public int getEmpId() {
		return empId;
	}

	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(int empId) {
		this.empId = empId;
	}

	/**
	 * @return the createBy
	 */
	public int getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createDt
	 */
	public Date getCreatedDt() {
		return createdDt;
	}

	/**
	 * @param createDt the createDt to set
	 */
	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * @return the updatedBy
	 */
	public int getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDt
	 */
	public Date getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * @param updatedDt the updatedDt to set
	 */
	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	/**
	 * @return the panNumber
	 */
	public String getPanNumber() {
		return panNumber;
	}

	/**
	 * @param panNumber the panNumber to set
	 */
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
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

	/**
	 * @return the userDetail
	 */
	public Users getUserDetail() {
		return userDetail;
	}

	/**
	 * @param userDetail the userDetail to set
	 */
	public void setUserDetail(Users userDetail) {
		this.userDetail = userDetail;
	}

	/**
	 * @return the bankDetail
	 */
	public NewBankDetails getBankDetail() {
		return bankDetail;
	}

	/**
	 * @param bankDetail the bankDetail to set
	 */
	public void setBankDetail(NewBankDetails bankDetail) {
		this.bankDetail = bankDetail;
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
	 * @return the mmId
	 */
	public String getMmId() {
		return mmId;
	}

	/**
	 * @param mmId the mmId to set
	 */
	public void setMmId(String mmId) {
		this.mmId = mmId;
	}

	/**
	 * @return the empNumber
	 */
	public int getEmpNumber() {
		return empNumber;
	}

	/**
	 * @param empNumber the empNumber to set
	 */
	public void setEmpNumber(int empNumber) {
		this.empNumber = empNumber;
	} 
	
}
