package com.bankpro.app.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankpro.app.dao.EmployeeRepository;
import com.bankpro.app.dao.UserRegistorRepository;
import com.bankpro.app.dao.VendorInfoRepository;
import com.bankpro.app.dto.BankDetailsDTO;
import com.bankpro.app.dto.EmployeeDTO;
import com.bankpro.app.dto.VendorInfoDTO;
import com.bankpro.app.model.AuditLogTrail;
import com.bankpro.app.model.Employee;
import com.bankpro.app.model.NewBankDetails;
import com.bankpro.app.model.OrganizationDetail;
import com.bankpro.app.model.Users;
import com.bankpro.app.model.Vendor;

/**
 * @author Surendra
 *
 */

@Service
public class EmployeeService {
	private int sessionUserId;
	private int orgID;
	

	@Autowired
	EmployeeRepository empRepo;
	
	@Autowired
	VendorInfoRepository vir;
	
	
	int startRow = 0;
	Scanner reader = null;

	@Transactional
	public List<Vendor> getEmployeeList(String userName) {
		Object[] row = vir.fetchUserIdAndOrgObjBasedOnUserName(userName);
		sessionUserId = Integer.valueOf(row[0].toString());
		orgID = ((OrganizationDetail)row[1]).getOrgID();
		List<Vendor> venList = vir.getVendorList(orgID, 2); //2 is pass to identify that current request is for fetch the vendor list 
		return venList;
	}

	@Transactional
	public void addNewEmployee(VendorInfoDTO venDTO, String userName) {
		Vendor venObj = new Vendor();
		Users userOb = new Users();
		NewBankDetails bankOb = new NewBankDetails();
		venObj = doCommonForSetVendorObj(venDTO, venObj);
		venObj.setCreatedDate(new Date());
		venObj.setCreatedBy(sessionUserId);
		
		// Set User Details which are going to save un Users
		userOb.setUserFirstName(venDTO.getFirstName());
		userOb.setUserLastName(venDTO.getLastName());
		userOb.setUserEmail(venDTO.getEmail());
		userOb.setUserContectNo(venDTO.getPhnNo());
//		userOb.setUserEmployeeNo(venDTO.getEmpNumber()+""); 

		// Set Bank Details which are going to save un BankDetails
		bankOb.setBankAccountNo(venDTO.getAccountNo());
		bankOb.setBankIFSCCode(venDTO.getIfscCode());
		bankOb.setBankName(venDTO.getBankName());
		bankOb.setBankBranchAddress(venDTO.getBranchAddress());

		venObj.setUserId(userOb);
		venObj.setNewBankID(bankOb);

		System.out.println("In Service Employee Object going to save");
		long vendId = vir.saveVendor(venObj);
		AuditLogTrail alt = new AuditLogTrail("EmployeeDetail", "Create", "Employee Create","New Employee", 
				                            "New Employee", userName, vendId);				
		vir.saveAuditTrail(alt);

	}
	
	public Vendor doCommonForSetVendorObj(VendorInfoDTO venDTO, Vendor venObj){
		venObj.setPan_number(venDTO.getPanNumber());
		venObj.setPaymentMethod(venDTO.getPaymentMethod());
		venObj.setPermissionFlag(true);				
		venObj.setEmpNumber(venDTO.getVenEmployeeNo());
		venObj.setVenBenCategorizeValue(2);  // 2 flag for identification that current line is related to employee.
		venObj.setMID(venDTO.getmID());		
		venObj.setVendorOrgID(orgID);
		venObj.setPhoneNo(venDTO.getPhnNo());
		venObj.setSupplierName(venDTO.getFirstName()+" "+venDTO.getLastName());
		return venObj;
	}

	@Transactional
	public void updateEmployee(VendorInfoDTO venDTO, String userName) {
		List<Vendor> venList = vir.getVendorObjBasedOnIDToUpdate(venDTO.getVendID());
		if(venList != null && !venList.isEmpty()){
			Vendor venObj = venList.get(0);
			venObj = doCommonForSetVendorObj(venDTO, venObj);
			venObj.setUpdateDate(new Date());
			venObj.setUpdatedBy(sessionUserId);
			
			Users userOb = venObj.getUserId();			
			userOb.setUserFirstName(venDTO.getFirstName());
			userOb.setUserLastName(venDTO.getLastName());
			userOb.setUserEmail(venDTO.getEmail());
			userOb.setUserContectNo(venDTO.getPhnNo());
			
			NewBankDetails bankOb = venObj.getNewBankID();
			bankOb.setBankAccountNo(venDTO.getAccountNo());
			bankOb.setBankIFSCCode(venDTO.getIfscCode());
			bankOb.setBankName(venDTO.getBankName());
			bankOb.setBankBranchAddress(venDTO.getBranchAddress());
			
			venObj.setUserId(userOb);
			venObj.setNewBankID(bankOb);
			
			long vendId = vir.saveVendor(venObj);
			AuditLogTrail alt = new AuditLogTrail("EmployeeDetail", "Update", "Employee Update","Update Employee", 
					                            "Update Employee", userName, vendId);				
			vir.saveAuditTrail(alt);			
		}

	}

	@Transactional
	public void disableEmp(long empId) {
		System.out.println("In EmpService to dissable empId : " + empId);
		vir.udpatePermissionFlag(empId, false);
	}

	@Transactional
	public List<Vendor> searchEmployees(String firstName) {
		List<Vendor> venList = vir.searchEmployees(firstName, orgID, 2);
		return venList;
	}

	@Transactional
	public void uploadEmpBatchFile(File fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			
			reader = new Scanner(fis);
			int rowCount = countRows(fileName);
			System.out.println(" no. of row isw      " + rowCount);

			for (int i = 1; i <= rowCount; i++) {
				String[] finalArrayToPersist = buildFileReaderArray();
				System.out.println("finalArrayToPersist " + finalArrayToPersist + " value of counter is " + i);

				if (finalArrayToPersist != null) {
					if(finalArrayToPersist[1] != "END"){
						
						System.out.println("Returned array length = " + finalArrayToPersist.length);
						saveEmployeeRecord(finalArrayToPersist);

					}
					else {
						System.out.println("in the end of the file now we have nothing to read"); 
					}
					 
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public int countRows(File fullFileName) {
		int count = 0;
		try {
			LineNumberReader ln = new LineNumberReader(new java.io.FileReader(fullFileName));
			while (ln.readLine() != null) {
				count++;
			}
			//count++;
			ln.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	public String[] buildFileReaderArray() {
		String[] record = new String[15];
		System.out.println("Record = " + record);
		if (reader.hasNextLine()) {
			System.out.println("Has next line");
			if (startRow == 0) {
				System.out.println("when first time statrt is zerooooooo");
				reader.nextLine();
				startRow = 1;
				record = new String[0];
				return null;
			} else {
				System.out.println("system has to read the record nowwwwwwww    ");
				String row = reader.nextLine();
				System.out.println("row data is    " + row);
				record = row.split(",");
				
			}
		} else {
			record[1] = "END";
			return record;
		}
		return record;
	}
	
	@Transactional
	public void saveEmployeeRecord(String[] empArray) {
		
		Employee emp = new Employee();
		Users userObj = new Users();
		NewBankDetails bankObj = new NewBankDetails();

		// Set User Detail of Employee
		userObj.setUserFirstName(empArray[0]);
		userObj.setUserLastName(empArray[1]);
		userObj.setUserEmail(empArray[3]);
		userObj.setUserContectNo(empArray[8]);

		// Set Bank Detail of Employee
		bankObj.setBankAccountNo(empArray[7]);
		bankObj.setBankIFSCCode(empArray[9]);
		bankObj.setBankName(empArray[10]);
		bankObj.setBankBranchAddress(empArray[11]);

		// Set Employee Detail
		emp.setPanNumber(empArray[6]);
		emp.setPaymentMethod(empArray[4]);
		emp.setEmpNumber(Integer.parseInt(empArray[2]));
		emp.setMmId(empArray[5]);
		// emp.setOrgId(orgId);
		emp.setStatus("Active");
		
		emp.setUserDetail(userObj);
		emp.setBankDetail(bankObj);

		// empRepo.save(emp);
		
		
	}


}