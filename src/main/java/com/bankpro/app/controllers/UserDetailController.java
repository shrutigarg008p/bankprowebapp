package com.bankpro.app.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bankpro.app.model.Users;

@Controller
@RequestMapping(value = "/user/*")
public class UserDetailController {

	private static final Logger LOGGER = Logger.getLogger(UserRegistorController.class);
	
	@RequestMapping(value="userDetails", method=RequestMethod.POST)
	public List<Users> viewUserDetails(@RequestBody int userId) {
		
		System.out.println("UserDetailController in viewUserDetails() method ");
		List<Users> userList = null;
		
		return userList;
	}
	
}
