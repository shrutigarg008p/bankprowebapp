package com.bankpro.app.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bankpro.app.dto.EmployeeDTO;
import com.bankpro.app.dto.VendorInfoDTO;
import com.bankpro.app.model.Employee;
import com.bankpro.app.model.Vendor;
import com.bankpro.app.services.EmployeeService;



/**
 * @author Surendra
 *
 */

@Controller
@RequestMapping(value = "/emp/*")
public class EmployeeDetailController {

	private static Logger logger = Logger.getLogger(EmployeeDetailController.class);
	
	@Autowired
	EmployeeService empSerivce;
	
	private static final String uploadFolderName = "uploadEmployee";
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public void addNewEmployee(@RequestBody VendorInfoDTO venDTO, Principal pri) {
		System.out.println("Loggedin user name = " + pri.getName());
		empSerivce.addNewEmployee(venDTO, pri.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/empList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendorInfoDTO> getEmployeeList(Principal pri) {
		List<Vendor> vendEmp = empSerivce.getEmployeeList(pri.getName());
		List<VendorInfoDTO> venDTO = VendorInfoDTO.mapVendorList(vendEmp);
		System.out.println("After map with DTO emp cpunt = " + venDTO.size());
		return venDTO;
	}
	
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updtEmpDetail", method = RequestMethod.PUT)
	public void updateEmployee(@RequestBody VendorInfoDTO venDTO, Principal pri) throws AddressException, MessagingException  {
		System.out.println("In Employee Detail Controller updateDetail method");
		empSerivce.updateEmployee(venDTO, pri.getName());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/disableEmp", method = RequestMethod.PUT)
	public void disableEmp(@RequestBody VendorInfoDTO venDTO) throws AddressException, MessagingException {
		long empId = venDTO.getVendID();
		empSerivce.disableEmp(empId);
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/searchEmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendorInfoDTO> searchEmployees(@RequestParam("firstName") String firstName) {
		System.out.println("In EmployeeDetailController searchEmployee method with name : " + firstName);
		List<Vendor> venList = empSerivce.searchEmployees(firstName);
		List<VendorInfoDTO> venDTOList = VendorInfoDTO.mapVendorList(venList);
		System.out.println("After mapping from EMPLOYEE DTO total employees = " + venDTOList.size());
		return venDTOList;
	}
	

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/uploadEmp", method = RequestMethod.POST)
	public void batchFileUploadToCreateEmployee(@RequestParam("fileName") String fileName) {
		//String rootPath = System.getProperty("catalina.home");
				String rootPath = "/home/ubuntu/paymentvista/bankprowebapp/target/bankproapp/resources/public/";
				File dir = new File(rootPath + File.separator + "tmpFiles");
				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				System.out.println("final file name is    " + serverFile);
				String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
				/*File mainDir = new File("C:/PaymentVistaFiles/BatchFileUpload/" + currentDate + File.separator + uploadFolderName);*/
				File mainDir = new File("/home/ubuntu/paymentvista/docupload/" + currentDate + File.separator + uploadFolderName);
				System.out.println("mainDir " + mainDir);
				if (!mainDir.exists())
					mainDir.mkdirs();
				System.out.println("When mainDir Not Exists, DIR path = " + mainDir + File.separator + fileName);
				System.out.println(dir.getAbsolutePath() + File.separator + fileName);
				System.out.println(mainDir + File.separator + fileName);
				
				Path movefrom = FileSystems.getDefault().getPath(dir.getAbsolutePath() + File.separator + fileName);
				System.out.println("movefrom         "+movefrom.getRoot());
				Path target = FileSystems.getDefault().getPath(mainDir + File.separator + fileName);
				System.out.println("target         "+target.getRoot());
				
				try {
					Files.move(movefrom, target, StandardCopyOption.REPLACE_EXISTING);
					empSerivce.uploadEmpBatchFile(new File(mainDir + File.separator + fileName));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*if (serverFile.renameTo(new File(mainDir + File.separator + fileName))) {
					System.out.println("Test");
					empSerivce.uploadEmpBatchFile(new File(mainDir + File.separator + fileName));
					
				} else {
					System.out.println("There is somthing problem in transfer the file. Please reupload the file once again");
				}*/
	}
	
	
	
}
