package com.bankpro.app.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bankpro.app.model.UserRoleRelation;
import com.bankpro.app.model.Users;

public class UserDetailDTO {

	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String contactNo;
	private String organization;
	private List<Integer> roleList = new ArrayList<>();
	private List<String> providedRoleList = new ArrayList<String>();
	private String employeeNo;
	private String addressLine1;
	private String userStatus;

	public UserDetailDTO() {

	}

	public UserDetailDTO(Users user, Map<String, String> roleValue) {
		this.userId = user.getUserId();
		this.firstName = user.getUserFirstName();
		this.lastName = user.getUserLastName();
		this.email = user.getUserEmail();
		this.contactNo = user.getUserContectNo();
		List<UserRoleRelation> userRoleList = user.getRoleRelation();
		if (userRoleList != null) {
			for (UserRoleRelation userRoleRelation : userRoleList) {
				this.providedRoleList.add(roleValue.get(String
						.valueOf(userRoleRelation.getUrr_roleID())));
				this.roleList.add(userRoleRelation.getUrr_roleID());
				System.out.println(this.providedRoleList);
			}
		}
		try{
			this.organization = user.getUserOrganization().getOrgName();
		}catch(Exception e){
			
		}
		this.employeeNo = user.getUserEmployeeNo();
		this.addressLine1 = user.getUserAddress();
		this.userStatus = user.getUserStatus();
	}

	public static UserDetailDTO mapUserDetails(Users users,
			Map<String, String> roleValue) {
		System.out.println("fffffffffffffffffffff    "
				+ users.getRoleRelation().size());

		return new UserDetailDTO(users, roleValue);
	}

	public static List<UserDetailDTO> mapFromUserEntities(List<Users> user,
			Map<String, String> roleValue) {
		return user.stream().map((users) -> mapUserDetails(users, roleValue))
				.collect(Collectors.toList());
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
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
	 * @param lastName
	 *            the lastName to set
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
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * @return the employeeNo
	 */
	public String getEmployeeNo() {
		return employeeNo;
	}

	/**
	 * @param employeeNo
	 *            the employeeNo to set
	 */
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * @param addressLine1
	 *            the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the providedRoleList
	 */
	public List<String> getProvidedRoleList() {
		return providedRoleList;
	}

	/**
	 * @param providedRoleList
	 *            the providedRoleList to set
	 */
	public void setProvidedRoleList(List<String> providedRoleList) {
		this.providedRoleList = providedRoleList;
	}

	/**
	 * @return the roleList
	 */
	public List<Integer> getRoleList() {
		return roleList;
	}

	/**
	 * @param roleList the roleList to set
	 */
	public void setRoleList(List<Integer> roleList) {
		this.roleList = roleList;
	}

	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}



}
