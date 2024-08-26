package com.bankpro.app.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bankpro.app.dto.NewUserRegistorDTO;
import com.bankpro.app.dto.OrganizationDetailDTO;
import com.bankpro.app.dto.RoleMasterDTO;
import com.bankpro.app.dto.UserDetailDTO;
import com.bankpro.app.model.OrganizationDetail;
import com.bankpro.app.model.RoleMaster;
import com.bankpro.app.model.Users;
import com.bankpro.app.services.UserRegistorService;

@Controller
@RequestMapping(value = "/register/*")
public class UserRegistorController {
	
	private static final Logger LOGGER = Logger.getLogger(UserRegistorController.class);
	@Autowired
	UserRegistorService urs;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/firstRegistration",method = RequestMethod.POST)
	public void registerUser(@RequestBody NewUserRegistorDTO user)throws AddressException, MessagingException {
		System.out.print("in class of a controllerrrrrr");
		urs.createUser(user.getFirstName().trim(), user.getLastName().trim(), user.getContactNo(), user.getEmail().trim(), user.getOrganization().trim());
	    urs.sendEmail(user.getEmail(),user.getFirstName().trim());
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/addNewUser", method = RequestMethod.POST)
	public void addNewUser(@RequestBody NewUserRegistorDTO user, Principal principal)throws AddressException, MessagingException {
		 urs.addNewUser(user,principal.getName());
		 urs.sendEmail(user.getEmail(),user.getFirstName().trim());

	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/roleValue", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<RoleMasterDTO> getValueForRoleDD(Principal principal) {
		List<RoleMaster> roleUserObj = urs.fetchVlaueForUserRole();
		System.out.println("in the methis of get the valueeeeeeeeeeeeeeeeeee   "+ roleUserObj.size());
		List<RoleMasterDTO> roleDTOList = RoleMasterDTO.mapFromRoleUserEntities(roleUserObj);
		System.out.println("Role DTO List size = " + roleDTOList.size());
		return roleDTOList;

	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/userList", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE })
	public List<UserDetailDTO> getUserList(Principal pri) {
		System.out.println("pri          "+pri.getName());
		List<Users> userList = urs.getAllUserList(pri.getName());
		List<?> roleUserArray = urs.relatedUserRoleBasedValue();
		Map<String, String> roleMap = new HashMap<>();
		if(roleUserArray != null){			
			for (int i = 0; i < roleUserArray.size(); i++) {
				Object[] rowObj = (Object[])roleUserArray.get(i);
				roleMap.put(rowObj[0].toString(), rowObj[1].toString());				
			}			
		}		
		List<UserDetailDTO> userDTOList = UserDetailDTO.mapFromUserEntities(userList, roleMap);	
		return userDTOList;
	}

	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updtUserDetail",method = RequestMethod.PUT)
	public void updateUserDetail(@RequestBody UserDetailDTO user, Principal principal)throws AddressException, MessagingException {
		System.out.print(" In UserRegistorController: updateUserDetail method");
		urs.updateUser(user,principal.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/searchUser", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<UserDetailDTO> searchUserList(@RequestParam("firstName") String firstName, @RequestParam("showInactive") String showInactive) {
		System.out.println("In Search User method with Fname = " + firstName);
		System.out.println("In Search User method with show inActive = " + showInactive);
		List<Users> userList = urs.searchUserList(firstName, showInactive);
		List<?> roleUserArray = urs.relatedUserRoleBasedValue();
		Map<String, String> roleMap = new HashMap<>();
		if(roleUserArray != null){			
			for (int i = 0; i < roleUserArray.size(); i++) {
				Object[] rowObj = (Object[])roleUserArray.get(i);
				roleMap.put(rowObj[0].toString(), rowObj[1].toString());				
			}			
		}
		System.out.println("rolw value in map is    "+roleMap);
		System.out.println(" UserRegistorController total users =    " + userList.size());
		System.out.println(" UserRegistorController total searched users found =    " + userList.size());
		
		List<UserDetailDTO> userDTOList = UserDetailDTO.mapFromUserEntities(userList, roleMap);
		return userDTOList;
	}
	
		
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/disableUser",method = RequestMethod.PUT)
	public void disableUser(@RequestBody UserDetailDTO user, Principal pri)throws AddressException, MessagingException {
		long userId = user.getUserId();
		System.out.print(" In UserRegistorController: disableUser method userId = " + userId);
		urs.disableUser(userId, pri.getName());
	
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/orgDetail", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public OrganizationDetailDTO getOrganizationDetail(Principal principal) {
		String userName = principal.getName();
		List<?> orgDetailList = urs.getOrganizationDetail(userName);
		Object[] row = (Object[])orgDetailList.get(0);

		long id = Long.valueOf(row[0].toString());
		String name = row[1].toString();
		
		OrganizationDetailDTO orgDTOOb = OrganizationDetailDTO.mapFromOrgDetailEntities(id,name);
		return orgDTOOb;

	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/forgotPasswrod",method = RequestMethod.POST)
	public long disableUser(@RequestParam("userName") String userName)throws AddressException, MessagingException {
		System.out.print(" In UserRegistorController: forgot Password method userName = " + userName);
		long usrCnt = urs.forgotPassword(userName);
		if(usrCnt > 0) {
			System.out.println("Going to send mail for forgot password to : " + userName);
			urs.sendEmail(userName,"");
		}
		return usrCnt;
	
	}
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
    	System.out.println("in exceptionnnnnnnnnnnnn     "+exc.getMessage()+"       rr     "+exc);
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
