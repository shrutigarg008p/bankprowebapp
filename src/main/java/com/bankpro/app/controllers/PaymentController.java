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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bankpro.app.dto.ApprovePaymentDTO;
import com.bankpro.app.dto.BankDetailsDTO;
import com.bankpro.app.dto.BatchErrorFileUploadDTO;
import com.bankpro.app.dto.BeneficiaryDetailDTO;
import com.bankpro.app.dto.InvoiceDTO;
import com.bankpro.app.dto.PaymentApprovalLogDTO;
import com.bankpro.app.dto.PaymentApproverUserDTO;
import com.bankpro.app.dto.PaymentDTO;
import com.bankpro.app.dto.VendorInfoDTO;
import com.bankpro.app.model.InvoiceDetails;
import com.bankpro.app.model.Payment;
import com.bankpro.app.model.PaymentApprover;
import com.bankpro.app.model.Vendor;
import com.bankpro.app.services.PaymentService;

/**
 * @author Surendra
 *
 */

@Controller
@RequestMapping(value = "/payment/*")
public class PaymentController {

	private static final Logger LOGGER = Logger.getLogger(PaymentController.class);
	private static final String uploadFolderName = "PaymentUpload";
	
	@Autowired
	PaymentService paySerivce;
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/addPayment", method = RequestMethod.POST)
	public void addPayment(@RequestBody PaymentDTO payDTO, Principal pri) throws AddressException, MessagingException  {
		System.out.println("In PaymentController: addPayment method");
		paySerivce.addPayment(payDTO, pri.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/pendingPaymentApproval", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<ApprovePaymentDTO> pendingApprovalPaymentList(Principal principal) {
		List<PaymentApprover> payAppList= paySerivce.doGetValueForApprovalPayment(principal.getName());
		List<ApprovePaymentDTO> payDTO = ApprovePaymentDTO.mapPendingPaymentApprove(payAppList);
		return payDTO;

	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/approvePendingPaymentApp", method = RequestMethod.PUT)
	public void updatePaymentApprovalLog(@RequestParam("payAppID") long paymentId, @RequestParam("comment") String comment, Principal prin) {
		System.out.println("user name in contriller is   " + paymentId);
		paySerivce.doUpdateApprovePayment(comment, paymentId, prin.getName());
	}
	
	
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paymentApprover", method = RequestMethod.GET)
	public List<PaymentApproverUserDTO> getPaymentApproverList(Principal principal) {
		System.out.println("In PaymentController to fetch List of Payment Approval method");
		List<?> appArray = paySerivce.getPaymentApproverList(principal.getName());
		if(appArray!= null){
			List<PaymentApproverUserDTO> paymentApproval = PaymentApproverUserDTO.mapFromPaymentApproverEntity(appArray);
			return paymentApproval;
		}		
		return null;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getDataForBeneficiary", method = RequestMethod.GET)
	public List<BeneficiaryDetailDTO> benefeciaryList(Principal principal) {
		List<?> venArray = paySerivce.doGetValueOfBeneficiaryFromVendor(principal.getName());
		if(venArray!= null){
			List<BeneficiaryDetailDTO> lii = BeneficiaryDetailDTO.mapFromvendorBeneficiryName(venArray);
			return lii;
		}		
		return null;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paymentList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PaymentDTO> getEmployeeList(Principal pri) {
		List<Payment> payment = paySerivce.getPaymentList(pri.getName());
		List<PaymentDTO> paymentList = PaymentDTO.mapFromPaymentEntities(payment);
		System.out.println("After map with DTO payment count = " + paymentList.size());
		return paymentList;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/declinedPendingPaymentApp", method = RequestMethod.PUT)
	public void updatePaymentDeclinedLog(@RequestParam("payAppID") long paymentId, @RequestParam("comment") String comment, Principal prin) {
		System.out.println("user name in contriller is   " + paymentId);
		paySerivce.doUpdateDeclinedPayment(comment, paymentId, prin.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/approvalLogData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PaymentApprovalLogDTO> approvalLogDataForView(@RequestParam("paymentId") long paymentId) {
		List<PaymentApprover> AppPayLog = paySerivce.getApprovalLog(paymentId);
		System.out.println("Total Payment till now : " + AppPayLog.size());
		List<PaymentApprovalLogDTO> paymentList = PaymentApprovalLogDTO.approverPaymentLogEntities(AppPayLog);
		System.out.println("After map with DTO payment count = " + paymentList.size());
		return paymentList;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/batchUploadForPayment", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BatchErrorFileUploadDTO> batchFileUploadToCreatePayment(@RequestParam("fileName") String fileName, Principal pri) {
//		String rootPath = System.getProperty("catalina.home");
		String rootPath = "/home/ubuntu/paymentvista/bankprowebapp/target/bankproapp/resources/public/";
		File dir = new File(rootPath + File.separator + "tmpFiles");
		String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());	
		File mainDir = new File("/home/ubuntu/paymentvista/UploadedDocFolder/" + currentDate + File.separator + uploadFolderName);
//		File mainDir = new File("C:/PaymentVistaFiles/BatchFileUpload/" + currentDate + File.separator + uploadFolderName);
//		System.out.println("mainDir                   " + mainDir);
		if (!mainDir.exists())
			mainDir.mkdirs();		
		File mainFileToMove = new File(mainDir + File.separator + fileName);		
		Path movefrom = FileSystems.getDefault().getPath(dir.getAbsolutePath() + File.separator + fileName);
		Path target = FileSystems.getDefault().getPath(mainDir + File.separator + fileName);
		try {
			Files.move(movefrom, target, StandardCopyOption.REPLACE_EXISTING);
			paySerivce.batchFileUploadOfCSV(mainFileToMove, pri.getName());
			if(paySerivce.getErrorList() != null && !paySerivce.getErrorList().isEmpty()){
				System.out.println("file deleted susceesfully      "+mainFileToMove.delete());
				List<BatchErrorFileUploadDTO> errorDTO = BatchErrorFileUploadDTO.mapFromBatchUploadWithError(paySerivce.getErrorList());
				return errorDTO;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/cancelSelectedPayment", method = RequestMethod.PUT)
	public void finalSubmitToCancelPayment(@RequestParam("payID") long paymentId, Principal prin) {
		System.out.println("user name in contriller is   " + paymentId);
		paySerivce.doUpdateCancelPayment(paymentId, prin.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/savePaymentAsDraftOrSendForApproval", method = RequestMethod.POST)
	public void savePaymentAsDraftOrSendForApproval(
			  @RequestParam("noteForApprover") String approverComment, 
			  @RequestParam("requestList") String[] dataRequestList,
			  @RequestParam("saveCondition") String saveCondition,
			  @RequestParam("approverList") String[] approverList,
			  @RequestParam("reqListLength") int reqListLength, Principal prin) {
		paySerivce.savePaymentAndPaymentApproval(approverComment, dataRequestList, saveCondition, approverList, reqListLength, prin.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getPaymentReleaseList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PaymentDTO> getPaymentReleaseList(Principal pri) {
		List<Payment> payReleaseList = paySerivce.fetchPaymentReleaseList();
		if(payReleaseList != null && !payReleaseList.isEmpty()){
		    List<PaymentDTO> paymentList = PaymentDTO.mapFromPaymentEntities(payReleaseList);
		    return paymentList;
		}
		//List<PaymentDTO> paymentList = PaymentDTO.mapFromPaymentEntities(payment);
		//System.out.println("After map with DTO payment count = " + paymentList.size());
		return null;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getAcctForPayRelease", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BankDetailsDTO> getAcctForPayRelease(Principal pri) {
		List<?> accountNoList = paySerivce.fetchAccountForPaymentRelease();
		System.out.println(accountNoList.size());
		if(accountNoList != null && !accountNoList.isEmpty()){
		    List<BankDetailsDTO> paymentList = BankDetailsDTO.extractAccountNoForPaymentRelease(accountNoList);
		    return paymentList;
		}
		//List<PaymentDTO> paymentList = PaymentDTO.mapFromPaymentEntities(payment);
		//System.out.println("After map with DTO payment count = " + paymentList.size());
		return null;
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/searchForCreatePayment", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PaymentDTO> searchForCreatePayment( 
			  @RequestParam("fromDate") String fromDate,
			  @RequestParam("toDate") String toDate,
			  @RequestParam("searchName") String searchName,
			  @RequestParam("approvalStatus") int approvalStatus,
			  @RequestParam("fromAmt") Double fromAmt,
			  @RequestParam("toAmt") Double toAmt,
			  @RequestParam("searchCondition") String searchCondition, Principal prin) {
		List<Payment> payList = paySerivce.fetchQueryDataToSearch(fromDate, toDate, searchName, approvalStatus, fromAmt, toAmt,
				searchCondition);
		System.out.println("payList   sixe is    "+payList.size());
		if (payList != null && !payList.isEmpty()) {
			List<PaymentDTO> paymentList = PaymentDTO.mapFromPaymentEntities(payList);
			System.out.println("After map with DTO payment count = " + paymentList.size());
			return paymentList;
		}
		return null;
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> errorHandler(Exception exc) {
		System.out.println("in exceptionnnnnnnnnnnnn     " + exc.getMessage()+"       rr     " + exc);
		LOGGER.error(exc.getMessage(), exc);
		return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getPaymentHistoryList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PaymentDTO> getPaymentHistoryList(Principal pri) {
		List<Payment> payReleaseList = paySerivce.fetchPaymentHistoryList();
		if(payReleaseList != null && !payReleaseList.isEmpty()){
		    List<PaymentDTO> paymentList = PaymentDTO.mapFromPaymentEntities(payReleaseList);
		    return paymentList;
		}
		return null;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/finalPaymentRelease", method = RequestMethod.PUT)
	public void finalPaymentRelease(@RequestParam("payLst") String[] releasePayList, Principal prin) {
		System.out.println("controlllerrrrrrrrrrr    "+releasePayList);
		paySerivce.doUpdatePaymentForRelease(releasePayList, prin.getName());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getInvoiceObjBasedOnInvoiceNo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public InvoiceDTO getInvoiceObjBasedOnInvoiceNo(@RequestParam("invoiceNo") String invNo) {
		List<InvoiceDetails> invoiceList = paySerivce.getInvoiceObjBasedOnInvoiceNo(invNo);
		if(invoiceList != null && !invoiceList.isEmpty() && invoiceList.size() == 1){
			InvoiceDetails invObj = invoiceList.get(0);
			Date invDate = invObj.getInvoiceDueDate();
			double invAmout =  invObj.getInvoiceAmount();
			InvoiceDTO invDTO = InvoiceDTO.mapToShowDueAndPaymentBasedOnINvoiceNO(invDate, invAmout);
		    return invDTO;
		}
		return null;
	}
	
	
}
