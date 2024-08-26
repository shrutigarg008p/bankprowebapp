package com.bankpro.app.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.bankpro.app.model.AuditLogTrail;
import com.bankpro.app.model.InvoiceDetails;
import com.bankpro.app.model.NewBankDetails;
import com.bankpro.app.model.Payment;
import com.bankpro.app.model.PaymentApprover;
import com.bankpro.app.model.Users;
import com.bankpro.app.model.Vendor;

/**
 * @author Surendra
 *
 */

@Repository
public class PaymentRepository {

	@PersistenceContext
	private EntityManager em;
	
	public long savePayment(Payment pay) {
		Payment p = em.merge(pay);
		return p.getPaymentId();
	}
	
	public void saveAuditTrail(AuditLogTrail auditTrail) {
		em.merge(auditTrail);
	}
	
	public void saveAppPayment(PaymentApprover payAppObj){
		em.merge(payAppObj);
	}
	
	public List<PaymentApprover> getListFromPaymentApprovalPending(long userId) {
		List<PaymentApprover> payAppObj = em
				.createNamedQuery(PaymentApprover.FETCH_PENDING_APPROVAL_PAYMENT_USERIDWISE, PaymentApprover.class)
				.setParameter("appUserID", userId).getResultList();
		return payAppObj;
	}
	
	public List<PaymentApprover> doGetApproverListBasedOnPaymentIDWithStatusCondition(long appID) {
		List<PaymentApprover> appPaymentObj = em
				.createNamedQuery(PaymentApprover.Find_PAYMENT_Approver_Based_On_PaymentID, PaymentApprover.class)
				.setParameter("payment", appID).getResultList();		
		return appPaymentObj;
	}
	
	public List<?> getPaymentApprover(int orgId) {
		List<?> usersArray = em
				.createNamedQuery(Users.GET_PAYMENT_APPROVER_LIST)
				.setParameter("userOrganization", orgId).getResultList();
		return usersArray;
	}
	
//	public List<InvoiceDetails> getInvoiceForVendor(long vendorId, int orgId) {
//		@SuppressWarnings("unchecked")
//		List<InvoiceDetails> invList = em.createQuery(
//				"from InvoiceDetails where invoiceOrgId = " + orgId + " AND InvoicevenId = " + vendorId + " AND invoiceApprovalStatus = 1" )
//				.getResultList();
//		System.out.println("In PaymentRepository Final Query executed : from InvoiceDetails where invoiceOrgId = " + orgId + " AND InvoicevenId = " + vendorId + " AND invoiceApprovalStatus = 1");
//		return invList;
//	}
	
	public List<Payment> getPaymentObjBasedOnID(long payID) {
		List<Payment> vendorList = em
				.createNamedQuery(Payment.FETCH_Payment_Obj_BasedOnPaymentID, Payment.class)
				.setParameter("paymentId", payID).getResultList();
		return vendorList;
	}

	public List<Payment> getPaymentList(int orgId) {
		List<Payment> payList = em.createNamedQuery(Payment.FETCH_ALL_PAYMENT, Payment.class).setParameter("orgId", orgId).getResultList();
		return payList;
	}
	
	public List<PaymentApprover> doGetApproverListBasedOnPayment(long appID) {
		List<PaymentApprover> appPaymentObj = em
				.createNamedQuery(PaymentApprover.Find_PAYMENT_Approver_For_Declined_On_PayID, PaymentApprover.class)
				.setParameter("payment", appID).getResultList();		
		return appPaymentObj;
	}
	
	public List<?> doGetVendorBasedOnName(String firstName, int orgId) {
		String query = "FROM Vendor ven, Users us where ven.userId = us.userId and us.userFirstName = '"+ firstName +"' and ven.vendorOrgID = "+ orgId +"";
		List<?> vendorList = em.createQuery(query).getResultList();
		return vendorList;
	}
	
	public List<InvoiceDetails> doGetInvoiceBasedOnInvoiceNo(String invoiceNo, int orgId) {
		List<InvoiceDetails> vendorList = em
				.createNamedQuery(InvoiceDetails.Invoice_Obj_Based_On_InvoiceNo, InvoiceDetails.class)
				.setParameter("invoiceNumber", invoiceNo)
				.setParameter("invoiceOrgId", orgId).getResultList();
		return vendorList;
	}
	
	public void updatePaymentStatusBasedOnPaymentID(long payID, int approvalStatus) {
          em.createQuery("update Payment set approvalStatus = "+approvalStatus+" where paymentId = "+payID).executeUpdate();
	}
	
	public List<Payment> getPaymentReleaseList(int orgId) {
		List<Payment> payRelList = em.createNamedQuery(Payment.FETCH_Payment_Release_Data, Payment.class)
				                .setParameter("orgId", orgId)
				                .setParameter("approvalStatus", 1).getResultList();
		return payRelList;
	}
	
	public List<?> fetchAccountListForPaymentRelBasedOnORG(int orgId) {		
		List<?> payRelList = em
				.createNamedQuery(NewBankDetails.find_accountNo_Based_On_ORG)
				.setParameter("bankOrgId", orgId).getResultList();
		return payRelList;
	}
	
	public List<Payment> getDataBasedOnSearchCondition(java.sql.Date fromDate, java.sql.Date toDate, 
			                        String searchName, int approvalStatus, double fromAmt,double toAmt, int orgID, String SearchCondition) {		
		CriteriaBuilder critBuild = em.getCriteriaBuilder();
		CriteriaQuery<Payment> criteria = critBuild.createQuery(Payment.class );
		Root<Payment> payRoot = criteria.from(Payment.class);
		System.out.println("SearchCondition                "+SearchCondition);
		if(SearchCondition.equalsIgnoreCase("createPayment")){
			criteria.where(getCommonWhereConditionForCreatePayment(critBuild, payRoot, fromDate, toDate, 
	                 searchName,  approvalStatus,  fromAmt, toAmt, orgID));
		}else if(SearchCondition.equalsIgnoreCase("Release")){
			criteria.where(getCommonWhereConditionForReleasePayment(critBuild, payRoot, fromDate, toDate, 
	                 searchName,  fromAmt, toAmt, orgID, SearchCondition));
		}else if(SearchCondition.equalsIgnoreCase("History")){
			criteria.where(getCommonWhereConditionForReleasePayment(critBuild, payRoot, fromDate, toDate, 
	                 searchName,  fromAmt, toAmt, orgID, SearchCondition));
		}
		
		List<Payment> payList = em.createQuery(criteria).getResultList();
				
		return payList;
	}
	
	private Predicate[] getCommonWhereConditionForCreatePayment(CriteriaBuilder cb,	Root<Payment> searchRoot, java.sql.Date fromDate, java.sql.Date toDate, 
            String searchName, int approvalStatus, double fromAmt,double toAmt, int orgID) {

		List<Predicate> predicates = new ArrayList<>();
		Join<Payment,Vendor> vendJoin = searchRoot.join("vendorDetail");
		Join<Payment,InvoiceDetails> invoiceJoin = searchRoot.join("invoiceDetail");
		if(searchName != null && searchName != ""){
			predicates.add(cb.like(vendJoin.<String>get("supplierName"),"%"+ searchName +"%"));
		}
		if (approvalStatus != 0) {
			predicates.add(cb.equal(searchRoot.<Integer> get("approvalStatus"),	approvalStatus));
		}
		predicates.add(cb.equal(searchRoot.<Integer> get("payOrgId"), orgID));
		if (fromDate != null) {
			predicates.add(cb.greaterThanOrEqualTo(invoiceJoin.<Date> get("invoiceDueDate"), fromDate));
		}
		if (toDate != null) {
			predicates.add(cb.lessThanOrEqualTo(invoiceJoin.<Date> get("invoiceDueDate"),toDate));
		}
		if(fromAmt >0){
			predicates.add(cb.greaterThanOrEqualTo(invoiceJoin.<Double> get("invoiceAmount"),fromAmt));
		}
		if(toAmt >0){
			predicates.add(cb.lessThanOrEqualTo(invoiceJoin.<Double> get("invoiceAmount"),toAmt));
		}

		return predicates.toArray(new Predicate[] {});
	}
	
	private Predicate[] getCommonWhereConditionForReleasePayment(CriteriaBuilder cb, Root<Payment> searchRoot, java.sql.Date fromDate,
			             java.sql.Date toDate, String searchName, double fromAmt, double toAmt, int orgID, String searchCondition) {
		List<Predicate> predicates = new ArrayList<>();
		Join<Payment,Vendor> vendJoin = searchRoot.join("vendorDetail");		
		if(searchName != null && searchName != ""){
			predicates.add(cb.like(vendJoin.<String>get("supplierName"),"%"+ searchName +"%"));
		}		
		predicates.add(cb.equal(searchRoot.<Integer> get("payOrgId"), orgID));
		
		if (searchCondition.equalsIgnoreCase("Release")) {
			predicates.add(cb.equal(searchRoot.<Integer> get("approvalStatus"),	1));
			if (fromDate != null) {
				predicates.add(cb.greaterThanOrEqualTo(searchRoot.<Date> get("paymentProcessDate"), fromDate));
			}
			if (toDate != null) {
				predicates.add(cb.lessThanOrEqualTo(searchRoot.<Date> get("paymentProcessDate"), toDate));
			}
		} else {
			Expression<Integer> exp = searchRoot.<Integer>get("approvalStatus");
			List<Integer> li = new ArrayList<>();
			li.add(6);
			li.add(7);
			predicates.add(exp.in(li));
			if (fromDate != null) {
				predicates.add(cb.greaterThanOrEqualTo(searchRoot.<Date> get("paymentDate"), fromDate));
			}
			if (toDate != null) {
				predicates.add(cb.lessThanOrEqualTo(searchRoot.<Date> get("paymentDate"), toDate));
			}
		}
		if(fromAmt >0){
			predicates.add(cb.greaterThanOrEqualTo(searchRoot.<Double> get("paymentAmount"),fromAmt));
		}
		if(toAmt >0){
			predicates.add(cb.lessThanOrEqualTo(searchRoot.<Double> get("paymentAmount"),toAmt));
		}

		return predicates.toArray(new Predicate[] {});
	}
	
	@SuppressWarnings("unchecked")
	public List<Payment> getPaymentHistoryList(int orgId) {		
		List<Payment> payRelList = em.createQuery("from Payment where payOrgId = "+ orgId +" and approvalStatus in(6, 7)").getResultList();
		return payRelList;
	}
	
		
}
