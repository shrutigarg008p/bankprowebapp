/**
 * 
 */
package com.bankpro.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bankpro.app.dto.NewUserRegistorDTO;
import com.bankpro.app.services.UserRegistorService;

/**
 * @author Admin
 *
 */
@Controller
@RequestMapping("/resetpassword")
public class ResetPassController {
	@Autowired
	UserRegistorService us;
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.POST)
	public void resetPassword(@RequestBody NewUserRegistorDTO usr){
		us.resetPassword(new BCryptPasswordEncoder().encode(usr.getPassword()),usr.getEmail(), usr.isLoginTermsAndConditon());
		
	}
}
