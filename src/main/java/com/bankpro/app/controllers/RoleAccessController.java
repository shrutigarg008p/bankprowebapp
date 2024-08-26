package com.bankpro.app.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bankpro.app.dto.RoleMasterDTO;
import com.bankpro.app.dto.TermMasterDTO;
import com.bankpro.app.model.RoleMaster;
import com.bankpro.app.model.TermMaster;
import com.bankpro.app.model.UserRoleRelation;
import com.bankpro.app.model.Users;
import com.bankpro.app.services.RoleAccessService;



/**
 * @author Surendra
 *
 */

@Controller
@RequestMapping(value = "/role-access/*")
public class RoleAccessController {

private static Logger logger = Logger.getLogger(RoleAccessController.class);
	
	@Autowired
	RoleAccessService roleSerivce;
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/roleList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RoleMasterDTO> getRoleList(Principal pri) {
		System.out.println("Loggedin user name = " + pri.getName());
		List<RoleMaster> role = roleSerivce.getRoleList(pri.getName());
		List<RoleMasterDTO> roleList = RoleMasterDTO.mapFromRoleUserEntities(role);
		System.out.println("After map with DTO role count = " + roleList.size());	
		return roleList;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/addNewRole", method = RequestMethod.POST)
	public void addNewEmployee(@RequestBody RoleMasterDTO roleDTO, Principal pri) throws AddressException, MessagingException {
		System.out.println("In RoleAccessController addNewRole method");
		System.out.println("Loggedin user name = " + pri.getName());
		roleSerivce.addNewRole(roleDTO, pri.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/userRoleList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public RoleMasterDTO getUserRoleList(Principal pri) {
		RoleMasterDTO userParamList = new RoleMasterDTO();
		
		Users userObj = roleSerivce.getUserObject(pri.getName());
		String userName = userObj.getUserFirstName() + " " + userObj.getUserLastName();
		int userId = userObj.getUserId();
		String userEmail = userObj.getUserEmail();
		List<UserRoleRelation> roleRelation = userObj.getRoleRelation();
		
		List<Integer> roleId = new ArrayList<>();
		if(roleRelation != null) {
			for(UserRoleRelation role : roleRelation) {
				roleId.add(role.getUrr_roleID());
			}
		}
		
		String roles = "";
		for (Integer role: roleId) {
            roles = roles+role+",";
		}
		
		String finalRole = "";
		if (roles.endsWith(",")) {
			finalRole = roles.substring(0, roles.length() - 1);
		}
		
			System.out.println("In Controller finalRole = " + finalRole);
			List<String> permissions = roleSerivce.getAllPermissionOfUser(finalRole);
			
			String userParam = "";
			if(permissions != null && !(permissions.equals(""))) {
				for (String param: permissions) {
					userParam = userParam+param+",";
				}
			}
			String finalUserParam = "";
			if (userParam.endsWith(",")) {
				finalUserParam = userParam.substring(0, userParam.length() - 1);
			}
			
			if(finalUserParam != null && !(finalUserParam.equals(""))) {
				userParamList = new RoleMasterDTO(finalUserParam,userName,userEmail,
						             userObj.getUserContectNo(), userObj.getUserOrganization().getOrgID());
			}
			System.out.println("Values set in  DTO Roles are: " + userParamList.getRoleDesciption());

			return userParamList;
		
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paymentTerms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TermMasterDTO> getPaymentTerms() {
		List<TermMaster> terms = roleSerivce.getPaymentTermList();
		List<TermMasterDTO> termList = TermMasterDTO.mapFromTermEntities(terms);
		System.out.println("After map with Term DTO term count = " + termList.size());	
		return termList;
	}
	
}
