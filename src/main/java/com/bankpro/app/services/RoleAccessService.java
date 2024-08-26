package com.bankpro.app.services;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankpro.app.dao.RoleAccessRepository;
import com.bankpro.app.dao.UserRegistorRepository;
import com.bankpro.app.dto.RoleMasterDTO;
import com.bankpro.app.model.PaymentApprover;
import com.bankpro.app.model.RoleMaster;
import com.bankpro.app.model.TermMaster;
import com.bankpro.app.model.Users;

/**
 * @author Surendra
 *
 */

@Service
public class RoleAccessService {
	
	@Autowired
	RoleAccessRepository roleRepo;
	
	@Autowired
	UserRegistorRepository urr;

	@Transactional
	public List<RoleMaster> getRoleList(String userName) {
		List<Users> usrList=urr.getUserObejectBasedOnUserName(userName);
		Users userObj = usrList.get(0);
		int orgId= userObj.getUserOrganization().getOrgID();
		System.out.println("In RoleAccess Service Role list Method : orgId  " + orgId);
		List<RoleMaster> roleList = roleRepo.getRoleList(orgId);
		return roleList;
	}
	
	@Transactional
	public void addNewRole(RoleMasterDTO roleDTO, String userName) {
		
		System.out.println("In RoleAccessService addNewRole method");
		
		List<Users> usrList=urr.getUserObejectBasedOnUserName(userName);
		Users userObj = usrList.get(0);
		int orgId= userObj.getUserOrganization().getOrgID();
		int userId = userObj.getUserId();
		System.out.println("In Role Service Add Role with : orgId  " + orgId);
		
		RoleMaster roleMaster = new RoleMaster();
		
		roleMaster.setRoleName(roleDTO.getRoleName());
		//roleMaster.setRoleDesciption(roleDTO.getRoleDesciption());
		String permisions = "";
		List<?> roleDesc = roleDTO.getSelectedPermissionList();
		for (int i = 0; i < roleDesc.size(); i++) {
			String paramName = (String) roleDesc.get(i);
			System.out.println("Permission Name  = " + paramName);
			permisions = permisions + paramName+",";
		}
		//parmisions = parmisions.replaceAll(", $", "");
		if (permisions.endsWith(",")) {
			permisions = permisions.substring(0, permisions.length() - 1);
		}
		System.out.println("Total Permissions are : " + permisions);
		roleMaster.setRoleDesciption(permisions);
		
		roleMaster.setRoleStatus("Y");
		roleMaster.setRoleCreatedBy(userId);
		roleMaster.setRoleCreatedDate(new Date());
		roleMaster.setOrgId(orgId);
		
		roleRepo.save(roleMaster);
		
	}
	
	@Transactional
	public Users getUserObject(String userName) {
		System.out.println("In RoleAccessService Login is user Name :   " + userName);
		List<Users> users = (List<Users>) urr.getUserObejectBasedOnUserName(userName);
		return users.get(0);
	}
	
	@Transactional
	public List<String> getAllPermissionOfUser(String finalRole) {
		System.out.println("In RoleAccess Service to get All permission sof user");
		List<String> userRoleList = roleRepo.getUserPermissionList(finalRole);
		return userRoleList;
		
	}
	
	@Transactional
	public List<TermMaster> getPaymentTermList() {
		List<TermMaster> termList = roleRepo.getPaymentTermList();
		return termList;
	}
	
}
