/**
 * 
 */
package com.bankpro.app.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Admin
 *
 */
@Entity
@Table(name = "pv_users")
@NamedQueries({
		@NamedQuery(name = Users.FIND_FOR_LOGIN, query = "select us.userEmail, us.userPassword from Users us where us.userEmail = :userEmail"),
		@NamedQuery(name = Users.FIND_BY_USERNAME, query = "select u.userEmail from Users u where userEmail = :userEmail"),
		@NamedQuery(name = Users.FLAG_COMPANY_DIALOG_VIEW, query = "select u.userEmail,u.userDetailOfSubmitFlag, "
				+ "u.userBankDialogueFlag, u.userOrganization from Users u where u.userEmail = :userEmail"),
		@NamedQuery(name = Users.GET_USER_OBJECT, query = "from Users where userEmail = :userEmail"),
		@NamedQuery(name = Users.SEARCH_BY_NAME, query = "from Users where userFirstName like :firstName "),
		@NamedQuery(name = Users.Get_ApproverName_For_Invoiceame_For_Invoice, query = "select distinct us.userId, us.userFirstName, us.userLastName from Users us, UserRoleRelation as role where us.userId = role.user.userId "
		+ " and us.userOrganization.orgID = :userOrganization and role.urr_roleID = 4"),
		@NamedQuery(name = Users.FIND_USER_ID_BASED_ON_USERNAME, query = "select us.userId, us.userOrganization from Users as us where us.userEmail = :userEmail"),
		@NamedQuery(name = Users.GET_PAYMENT_APPROVER_LIST, query = "select distinct us.userId, us.userFirstName, us.userLastName from Users us, UserRoleRelation as role where us.userId = role.user.userId "
				+ " and us.userOrganization.orgID = :userOrganization and role.urr_roleID = 6"),
		@NamedQuery(name = Users.Fetch_UserNameWith_IDFor_ApprovalLOG, query = "select userId, userFirstName, userLastName from Users where userOrganization.orgID = :userOrganization"),
		})
public class Users {
	public static final String FIND_FOR_LOGIN = "users.findForLogin";
	public static final String FIND_BY_USERNAME = "users.findByUserName";
	public static final String FLAG_COMPANY_DIALOG_VIEW = "users.FLAG_COMPANY_DIALOG_VIEW";
	public static final String GET_USER_OBJECT = "users.GET_USER_OBJECT";
	public static final String SEARCH_BY_NAME = "users.SEARCH_BY_NAME";
	public static final String SEARCH_VENDOR_FOR_APPROVAL = "Users.SEARCH_VENDOR_FOR_APPROVAL";
	public static final String Get_ApproverName_For_Invoiceame_For_Invoice = "Users.Get_ApproverName_For_Invoice";
	public static final String FIND_USER_ID_BASED_ON_USERNAME = "Users.FIND_USER_ID_BASED_ON_USERNAME";
	public static final String GET_PAYMENT_APPROVER_LIST = "Users.GET_PAYMENT_APPROVER_LIST";
	public static final String Fetch_UserNameWith_IDFor_ApprovalLOG = "Users.Fetch_UserNameWith_IDFor_ApprovalLOG";
	
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private int userId;

	@Column(name = "user_email")
	private String userEmail;

	@Column(name = "user_password")
	private String userPassword;

	@Column(name = "user_firstName")
	private String userFirstName;

	@Column(name = "user_lastName")
	private String userLastName;

	@Column(name = "user_contactNumber")
	private String userContectNo;

	@Column(name = "user_numberVarification")
	private String userNoVerification;

	@Column(name = "user_employeNo")
	private String userEmployeeNo;

	@Column(name = "user_address")
	private String userAddress;

	@Column(name = "user_salt")
	private String userSalt;

	@Column(name = "user_photo")
	private String userPhoto;

	@Column(name = "user_createdBy")
	private int userCreatedBy;

	@Column(name = "user_createdDate")
	private Date userCreatedDate = new Date();

	@Column(name = "user_updatedBy")
	private int userUpdatedBy;

	@Column(name = "user_updatedDate")
	private Date userUpdatedDate;

	@Column(name = "user_lastLogin")
	private Date userLastLogin;

	@Column(name = "user_status")
	private String userStatus;

	@Column(name = "user_detailSubmitFlag")
	private boolean userDetailOfSubmitFlag = true;

	@Column(name = "user_bankDialogFlag")
	private boolean userBankDialogueFlag = true;
	
	@Column(name = "user_loginTermCondition")
	private boolean userLoginTermAndCondition;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private List<UserRoleRelation> roleRelation;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "org_id")
	private OrganizationDetail userOrganization;

	// @OneToOne
	// @JoinColumn(name = "vendor_id")
	// private Vendor vendorHead;

	public Users() {

	}

	public Users(String firstName, String lastName, String phoneNo,
			String emailId, OrganizationDetail organization, String status) {
		this.userFirstName = firstName;
		this.userLastName = lastName;
		this.userContectNo = phoneNo;
		this.userOrganization = organization;
		this.userEmail = emailId;
		this.userStatus = status;
		this.userPassword = "$2a$10$ULF4HQsTTk6ECyJG8tkyXetRXI8GO07ls5VFCLMzvbXiddGwJf5.a";

	}
	
	

	/**
	 * @return the userLoginTermAndCondition
	 */
	public boolean isUserLoginTermAndCondition() {
		return userLoginTermAndCondition;
	}

	/**
	 * @param userLoginTermAndCondition the userLoginTermAndCondition to set
	 */
	public void setUserLoginTermAndCondition(boolean userLoginTermAndCondition) {
		this.userLoginTermAndCondition = userLoginTermAndCondition;
	}

	/**
	 * @return the userBankDialogueFlag
	 */
	public boolean isUserBankDialogueFlag() {
		return userBankDialogueFlag;
	}

	/**
	 * @param userBankDialogueFlag
	 *            the userBankDialogueFlag to set
	 */
	public void setUserBankDialogueFlag(boolean userBankDialogueFlag) {
		this.userBankDialogueFlag = userBankDialogueFlag;
	}

	/**
	 * @return the userDetailOfSubmitFlag
	 */
	public boolean isUserDetailOfSubmitFlag() {
		return userDetailOfSubmitFlag;
	}

	/**
	 * @param userDetailOfSubmitFlag
	 *            the userDetailOfSubmitFlag to set
	 */
	public void setUserDetailOfSubmitFlag(boolean userDetailOfSubmitFlag) {
		this.userDetailOfSubmitFlag = userDetailOfSubmitFlag;
	}

	/**
	 * @return the roleRelation
	 */
	public List<UserRoleRelation> getRoleRelation() {
		return roleRelation;
	}

	/**
	 * @param roleRelation
	 *            the roleRelation to set
	 */
	public void setRoleRelation(List<UserRoleRelation> roleRelation) {
		this.roleRelation = roleRelation;
	}

	/**
	 * @return the userAddress
	 */
	public String getUserAddress() {
		return userAddress;
	}

	/**
	 * @param userAddress
	 *            the userAddress to set
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	/**
	 * @return the userEmployeeNo
	 */
	public String getUserEmployeeNo() {
		return userEmployeeNo;
	}

	/**
	 * @param userEmployeeNo
	 *            the userEmployeeNo to set
	 */
	public void setUserEmployeeNo(String userEmployeeNo) {
		this.userEmployeeNo = userEmployeeNo;
	}

	/**
	 * @return the userOrganization
	 */
	public OrganizationDetail getUserOrganization() {
		return userOrganization;
	}

	/**
	 * @param userOrganization
	 *            the userOrganization to set
	 */
	public void setUserOrganization(OrganizationDetail userOrganization) {
		this.userOrganization = userOrganization;
	}

	public Users(String Pass) {
		this.userPassword = Pass;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
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

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword
	 *            the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the userFirstName
	 */
	public String getUserFirstName() {
		return userFirstName;
	}

	/**
	 * @param userFirstName
	 *            the userFirstName to set
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	/**
	 * @return the userLastName
	 */
	public String getUserLastName() {
		return userLastName;
	}

	/**
	 * @param userLastName
	 *            the userLastName to set
	 */
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	/**
	 * @return the userContectNo
	 */
	public String getUserContectNo() {
		return userContectNo;
	}

	/**
	 * @param userContectNo
	 *            the userContectNo to set
	 */
	public void setUserContectNo(String userContectNo) {
		this.userContectNo = userContectNo;
	}

	/**
	 * @return the userNoVerification
	 */
	public String getUserNoVerification() {
		return userNoVerification;
	}

	/**
	 * @param userNoVerification
	 *            the userNoVerification to set
	 */
	public void setUserNoVerification(String userNoVerification) {
		this.userNoVerification = userNoVerification;
	}

	/**
	 * @return the userSalt
	 */
	public String getUserSalt() {
		return userSalt;
	}

	/**
	 * @param userSalt
	 *            the userSalt to set
	 */
	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt;
	}

	/**
	 * @return the userPhoto
	 */
	public String getUserPhoto() {
		return userPhoto;
	}

	/**
	 * @param userPhoto
	 *            the userPhoto to set
	 */
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	/**
	 * @return the userCreatedBy
	 */
	public int getUserCreatedBy() {
		return userCreatedBy;
	}

	/**
	 * @param userCreatedBy
	 *            the userCreatedBy to set
	 */
	public void setUserCreatedBy(int userCreatedBy) {
		this.userCreatedBy = userCreatedBy;
	}

	/**
	 * @return the userCreatedDate
	 */
	public Date getUserCreatedDate() {
		return userCreatedDate;
	}

	/**
	 * @param userCreatedDate
	 *            the userCreatedDate to set
	 */
	public void setUserCreatedDate(Date userCreatedDate) {
		this.userCreatedDate = userCreatedDate;
	}

	/**
	 * @return the userUpdatedBy
	 */
	public int getUserUpdatedBy() {
		return userUpdatedBy;
	}

	/**
	 * @param userUpdatedBy
	 *            the userUpdatedBy to set
	 */
	public void setUserUpdatedBy(int userUpdatedBy) {
		this.userUpdatedBy = userUpdatedBy;
	}

	/**
	 * @return the userUpdatedDate
	 */
	public Date getUserUpdatedDate() {
		return userUpdatedDate;
	}

	/**
	 * @param userUpdatedDate
	 *            the userUpdatedDate to set
	 */
	public void setUserUpdatedDate(Date userUpdatedDate) {
		this.userUpdatedDate = userUpdatedDate;
	}

	/**
	 * @return the userLastLogin
	 */
	public Date getUserLastLogin() {
		return userLastLogin;
	}

	/**
	 * @param userLastLogin
	 *            the userLastLogin to set
	 */
	public void setUserLastLogin(Date userLastLogin) {
		this.userLastLogin = userLastLogin;
	}

	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus
	 *            the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

}
