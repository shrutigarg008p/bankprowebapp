package com.bankpro.app.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.Employee;
import com.bankpro.app.model.NewBankDetails;

/**
 * @author Surendra
 *
 */

public class EmployeeDTO {
	
	private int empId;
	private String firstName;
	private String lastName;
	private String email;
	private String paymentMethod;
	private String panNumber;
	private String accountNo;
	private String contactNumber;
	private String ifscCode;
	private String bankName;
	private String branch;
	
	private int createdBy;
	private Date createdDt;
	private int updatedBy;
	private Date updateddt;
	
	private int orgId;
	private String mmId;
	private String empNumber;
	
	
	public EmployeeDTO(){
		
	}
	
	
	public EmployeeDTO(Employee emp) {
		System.out.println("Emp ID = " + emp.getEmpId());
		
		this.empId = emp.getEmpId();
		
		this.firstName = emp.getUserDetail().getUserFirstName();
		this.lastName = emp.getUserDetail().getUserLastName();
		this.email = emp.getUserDetail().getUserEmail();
		this.contactNumber = emp.getUserDetail().getUserContectNo();
		
		this.paymentMethod = emp.getPaymentMethod();
		this.panNumber = emp.getPanNumber();
		this.orgId = emp.getOrgId();
		
		this.accountNo = emp.getBankDetail().getBankAccountNo();
		this.ifscCode = emp.getBankDetail().getBankIFSCCode();
		this.bankName = emp.getBankDetail().getBankName();
		this.branch = emp.getBankDetail().getBankBranchAddress();
		this.orgId = emp.getOrgId();
		
		this.createdBy = emp.getCreatedBy();
		this.createdDt = emp.getCreatedDt();
		this.updatedBy = emp.getUpdatedBy();
		this.updateddt = emp.getUpdatedDt();
		this.mmId = emp.getMmId();
		//this.empNumber = emp.getEmpNumber();
		
		this.empNumber = emp.getUserDetail().getUserEmployeeNo();
		
	}
	
	public static EmployeeDTO mapEmployeeDetail(Employee emp) {
		return new EmployeeDTO(emp);
	}
	
	public static List<EmployeeDTO> mapFromEmployeeEntities(List<Employee> employees) {
		return employees.stream().map((employee) -> mapEmployeeDetail(employee)).collect(Collectors.toList());
	}


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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}


	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}


	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}


	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}


	/**
	 * @return the ifscCode
	 */
	public String getIfscCode() {
		return ifscCode;
	}


	/**
	 * @param ifscCode the ifscCode to set
	 */
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}


	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}


	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}


	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
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
	 * @return the createdDt
	 */
	public Date getCreatedDt() {
		return createdDt;
	}


	/**
	 * @param createdDt the createdDt to set
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
	 * @return the updateddt
	 */
	public Date getUpdateddt() {
		return updateddt;
	}


	/**
	 * @param updateddt the updateddt to set
	 */
	public void setUpdateddt(Date updateddt) {
		this.updateddt = updateddt;
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
	public String getEmpNumber() {
		return empNumber;
	}


	/**
	 * @param empNumber the empNumber to set
	 */
	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}
	
	
	
	

}
