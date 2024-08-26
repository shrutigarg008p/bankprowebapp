package com.bankpro.app.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/fileUploadProcess/**")
public class FileUploadController {
	
	
	
	
	
@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/fileUploadOnServer", method = RequestMethod.POST)
public @ResponseBody String fileUploadToServer(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws IOException {
		if (request instanceof MultipartHttpServletRequest) {
			if (!file.isEmpty()) {
				String fielName = file.getOriginalFilename();
				byte[] bytes = file.getBytes();
//				String rootPath = System.getProperty("catalina.home");
				String rootPath = "/home/ubuntu/paymentvista/bankprowebapp/target/bankproapp/resources/public/";
				File dir = new File(rootPath + File.separator + "tmpFiles");
//				System.out.println("dir is          " + dir.getAbsolutePath());
				if (!dir.exists())
					dir.mkdirs();
				File serverFile = new File(dir.getAbsolutePath()+ File.separator + fielName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
//				System.out.println("serverFile.getAbsolutePath()        "+ serverFile.getAbsolutePath());
				return serverFile.getName();

			}

		} else {
			System.out.println("sorry u r in the wrong direction...........");
		}

		return null;
	}

}
