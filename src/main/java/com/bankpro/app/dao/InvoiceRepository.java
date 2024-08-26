/**
 * 
 */
package com.bankpro.app.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.bankpro.app.model.AuditLogTrail;
import com.bankpro.app.model.DocInfoDetail;
import com.bankpro.app.model.InvoiceApprover;
import com.bankpro.app.model.InvoiceDetails;
import com.bankpro.app.model.InvoiceTypeMaster;
import com.bankpro.app.model.Payment;
import com.bankpro.app.model.Users;
import com.bankpro.app.model.Vendor;

/**
 * @author Lakharwal
 *
 */
@Repository
public class InvoiceRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	
	public InvoiceDetails saveInvoice(InvoiceDetails invoiceObj) {
		InvoiceDetails invObj = em.merge(invoiceObj);
		return invObj;
	}
	
	public void saveDocInfo(DocInfoDetail docRef){
		em.merge(docRef);
		
	}
	
	public void saveAppInvoice(InvoiceApprover invAppObj){
		em.merge(invAppObj);
		
	}
	
	public long saveInvoiceApprover(InvoiceApprover appObj) {
		InvoiceApprover appObjj = em.merge(appObj);
		return appObjj.getAppID();
	}
	
	public List<InvoiceDetails> getInvoiceObjBasedOnID(long invId){
		List<InvoiceDetails> invoiceObj = em
				.createNamedQuery(InvoiceDetails.FETCH_IVNVOICE_OBJ, InvoiceDetails.class)
				.setParameter("invoiceId", invId).getResultList();
		return invoiceObj;
	}
	
	public List<InvoiceDetails> getInvoiceObjBasedOnInvoiceNo(String invoiceNo, int orgId){
		System.out.println("invoiceNo    "+invoiceNo);
		System.out.println("orgId   "+orgId);
		List<InvoiceDetails> invoiceObj = em
				.createNamedQuery(InvoiceDetails.FETCH_INVOICE_OBJ_BASED_ON_INVOICE_NO, InvoiceDetails.class)
				.setParameter("invoiceNumber", invoiceNo)
				.setParameter("invoiceOrgId", orgId).getResultList();
		return invoiceObj;
	}
	
	public List<Users> getUserObejectBasedOnUserName(String username) {
		List<Users> usersObj = em
				.createNamedQuery(Users.GET_USER_OBJECT, Users.class)
				.setParameter("userEmail", username).getResultList();
		return usersObj;
	}
	
	public void saveAuditTrail(AuditLogTrail auditTrail) {
		em.merge(auditTrail);
	}
	
	public List<InvoiceDetails> createQueryForInvoice(int orgId) {
		@SuppressWarnings("unchecked")
		List<InvoiceDetails> invList = em.createQuery(
				"from InvoiceDetails where invoiceOrgId = " + orgId)
				.getResultList();
		return invList;
	}
	
	public List<?> queryToFetchBeneficiaryFromVendor(int orgId) {
		List<?> invList = em.createQuery("select ven.id, ven.supplierName from Vendor ven where ven.permissionFlag = 1 and ven.vendorOrgID = " + orgId)
				.getResultList();
		return invList;
	}
	
	public List<?> queryToFetchApproverNameForInvoice(int orgId) {
		String rr = "select distinct us.userId,us.userFirstName, us.userLastName from Users us, UserRoleRelation as role where us.userId = role.user.userId "
				+ " and us.userOrganization.orgID = "	+ orgId + " and role.urr_roleID = 4";
		List<?> usersArray = em
				.createNamedQuery(Users.Get_ApproverName_For_Invoiceame_For_Invoice)
				.setParameter("userOrganization", orgId).getResultList();
		return usersArray;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> doGetVendorSupplierNameBasedOnVenId(long vendId) {
		List<Vendor> vendList = em.createQuery("select supplierName from Vendor ven where ven.id = " + vendId).getResultList();
		return vendList;
	}
	
	public Object[] fetchInvoiceApprovalPendingList(String username) {
		System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		List<?> usersObj = em
				.createNamedQuery(Users.FIND_USER_ID_BASED_ON_USERNAME)
				.setParameter("userEmail", username).getResultList();		
		return (Object[])usersObj.get(0);
	}
	
	public List<?> getPendingListOfApproveInvoice(long userID) {
		List<?> invoiceObj = em
				.createNamedQuery(InvoiceDetails.Find_pending_Approved_Invoice)
				.setParameter("appUserID", userID).getResultList();		
		return invoiceObj;
	}
	
	public List<?> getVendorBasedOnMultipleVendorId(String vendID) {
		String query = "select ven.id, ven.userId, ven.newBankID, ven.primaryEmailId, ven.paymentMethod from Vendor ven where ven.id in ( "
				+ vendID + " )";
		System.out.println("query is    " + query);
		List<?> invoiceObj = em.createQuery(query).getResultList();
		// List<Vendor> invoiceObj = em
		// .createNamedQuery(Vendor.Fetch_Vendor_Based_On_MultipleVendorID,
		// Vendor.class)
		// .setParameter("id", vendID).getResultList();
		return invoiceObj;
	}
	
	public List<InvoiceDetails> createQueryForInvoiceBasedOnID(long invoiceId) {
		@SuppressWarnings("unchecked")
		List<InvoiceDetails> invList = em.createQuery("from InvoiceDetails where invoiceId = " + invoiceId)
				.getResultList();
		return invList;
	}
	
	public List<InvoiceApprover> doGetApproverListForView(long invID) {
		List<InvoiceApprover> invoiceObj = em
				.createNamedQuery(InvoiceApprover.Find_Invoice_Approver_Based_On_InvoiceID, InvoiceApprover.class)
				.setParameter("appInvoiceID", invID).getResultList();		
		return invoiceObj;
	}
	
	public List<InvoiceTypeMaster> getInvoiceType() {
		List<InvoiceTypeMaster> invoiceTypeList = em.createNamedQuery(InvoiceTypeMaster.FETCH_INVOICE_TYPE_LIST,
				InvoiceTypeMaster.class).getResultList();
		return invoiceTypeList;
	}
	
	public List<InvoiceApprover> getApproverObjList(long invoiceId) {
		List<InvoiceApprover> invApprObjList = em.createNamedQuery(InvoiceApprover.FETCH_APPROVE_INVOICE_OBJ,
				InvoiceApprover.class).setParameter("invoiceId", invoiceId).getResultList();
		return invApprObjList;	
	}
	
	public List<?> getApproveInvoiceHistory(long userID, int orgId) {
		List<?> invoiceObj = em
				.createNamedQuery(InvoiceDetails.FIND_APPROVAL_INVOICE_HISTORY)
				.setParameter("appUserID", userID)
				.setParameter("orgId", orgId)
				.getResultList();		
		return invoiceObj;
	}
	
	public List<?> searchInvoiceList(String startDate, String endDate, String name, long amountFrom, long amountTO, int orgId) {
		@SuppressWarnings("unchecked")
		
		DateFormat userDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");
		String convertedStartDate = "";
		try {
			if( (startDate != null) && (!startDate.equals("")) ) {
				Date fromDate = userDateFormat.parse(startDate);
				convertedStartDate = dateFormatNeeded.format(fromDate);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String convertedEndDate = "";
		try {
			if( (endDate !=null) && (!endDate.equals(""))) {
				Date lastDate = userDateFormat.parse(endDate);
				convertedEndDate = dateFormatNeeded.format(lastDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("From Date = " + convertedStartDate + " To Date = " + convertedEndDate);

		StringBuffer query = new StringBuffer("select InvoicevenId, invoiceId from InvoiceDetails envd where envd.invoiceOrgId = " + orgId);
		query.append(" AND envd.invoiceApprovalStatus = 0");
		
		if( (convertedStartDate != null) &&(!convertedStartDate.equals(""))) {
			query.append(" AND ( envd.invoiceCreatedDate >= " + "'" + convertedStartDate + "'");
		}
		if( (convertedEndDate != null) && (!convertedEndDate.equals(""))) {
			query.append(" AND envd.invoiceCreatedDate < " + "'" + convertedEndDate + "' ) ");
		}
		if(amountFrom > 0) {
			query.append(" AND  envd.invoiceAmount >= " + amountFrom);
		}
		if(amountTO > 0) {
			query.append(" AND envd.invoiceAmount < " + amountTO );
		}
		/*if( (name != null) && (!name.equals("")) ) {
			//query.append(" AND ( envd.InvoicevenId in (select id from Vendor where userId.userFirstName like '%" + name + "%' ) )"  );
			query.append(" AND envd.InvoicevenId in (select id from Vendor where userId in ( select userId from Users where userFirstName like '%" + name + "%' ) ) "  );
		}*/
		
		
		
		//query.append(" AND envd.invoiceId in(select appInvoiceID from InvoiceApprover where appStatus =0 and invoiceApprovalAccess =1)");
		
		if(name != null) {
			System.out.println("Name = " + name);
		}
		
		System.out.println("Final query is    " + query);
		List<InvoiceDetails> invoiceList = em.createQuery(query.toString()).getResultList();
		System.out.println("Data Found Count = " + invoiceList.size());
		return invoiceList;
	}
	
	public List<?> searchInvoiceApprovalHistoryList(String startDate, String endDate, String name, long amountFrom, long amountTO, int orgId) {
		@SuppressWarnings("unchecked")
		
		DateFormat userDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");
		String convertedStartDate = "";
		try {
			if( (startDate != null) && (!startDate.equals("")) ) {
				Date fromDate = userDateFormat.parse(startDate);
				convertedStartDate = dateFormatNeeded.format(fromDate);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String convertedEndDate = "";
		try {
			if( (endDate !=null) && (!endDate.equals(""))) {
				Date lastDate = userDateFormat.parse(endDate);
				convertedEndDate = dateFormatNeeded.format(lastDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		StringBuffer query = new StringBuffer("select InvoicevenId, invoiceId from InvoiceDetails envd where envd.invoiceOrgId = " + orgId);
		query.append(" AND envd.invoiceApprovalStatus in(1,3) ");
		
		if( (convertedStartDate != null) &&(!convertedStartDate.equals(""))) {
			query.append(" AND ( envd.invoiceCreatedDate >= " + "'" + convertedStartDate + "'");
		}
		if( (convertedEndDate != null) && (!convertedEndDate.equals(""))) {
			query.append(" AND envd.invoiceCreatedDate < " + "'" + convertedEndDate + "' ) ");
		}
		if(amountFrom > 0) {
			query.append(" AND  envd.invoiceAmount >= " + amountFrom);
		}
		if(amountTO > 0) {
			query.append(" AND envd.invoiceAmount < " + amountTO );
		}
		
		System.out.println("Final query is    " + query);
		List<InvoiceDetails> invoiceList = em.createQuery(query.toString()).getResultList();
		System.out.println("Data Found Count = " + invoiceList.size());
		return invoiceList;
	}
	
	public List<InvoiceDetails> getDataBasedOnSearchCondition(java.sql.Date dateFrom, java.sql.Date dateTo, String searchName, String amountFrom,
			String amountTo, int invoiceStatusField, int approvalStatusField, int approvalStatus, int orgID){
		CriteriaBuilder critBuild = em.getCriteriaBuilder();
		CriteriaQuery<InvoiceDetails> criteria = critBuild.createQuery(InvoiceDetails.class );
		Root<InvoiceDetails> payRoot = criteria.from(InvoiceDetails.class);
			criteria.where(getCommonWhereConditionForInvoiceSearch(critBuild, payRoot, dateFrom, dateTo, 
	                 searchName,  amountFrom, amountTo, invoiceStatusField,approvalStatusField, approvalStatus, orgID));		
		List<InvoiceDetails> invoiceList = em.createQuery(criteria).getResultList();				
		return invoiceList;		
	}
	
	
	private Predicate[] getCommonWhereConditionForInvoiceSearch(CriteriaBuilder cb, Root<InvoiceDetails> searchRoot, java.sql.Date dateFrom, 
			          java.sql.Date dateTo, String searchName, String amountFrom,
			           String amountTo, int invoiceStatusField, int approvalStatusField, int approvalStatus, int orgID) {
		List<Predicate> predicates = new ArrayList<>();
		if (searchName != null && searchName != "") {
			predicates.add(cb.like(searchRoot.<String> get("invoiceBeneficiaryName"), "%" + searchName + "%"));
		}
		predicates.add(cb.equal(searchRoot.<Integer> get("invoiceOrgId"), orgID));
		double fromAmt = Double.valueOf(amountFrom);
		double toAmt = Double.valueOf(amountTo);
		if (fromAmt > 0) {
			predicates.add(cb.greaterThanOrEqualTo(searchRoot.<Double> get("invoiceAmount"), fromAmt));
		}
		if (toAmt > 0) {
			predicates.add(cb.lessThanOrEqualTo(searchRoot.<Double> get("invoiceAmount"), toAmt));
		}		
		if (dateFrom != null) {
			predicates.add(cb.greaterThanOrEqualTo(searchRoot.<Date> get("invoiceDueDate"), dateFrom));
		}
		if (dateTo != null) {
			predicates.add(cb.lessThanOrEqualTo(searchRoot.<Date> get("invoiceDueDate"), dateTo));
		}
		if (invoiceStatusField != 0 || approvalStatusField != 0) {
			if (invoiceStatusField != 0) {
				predicates.add(cb.equal(searchRoot.<Integer> get("invoiceStatus"), approvalStatus));
			}
			if (approvalStatusField != 0) {
				predicates.add(cb.equal(searchRoot.<Integer> get("invoiceApprovalStatus"), approvalStatus));
			}
		}
		return predicates.toArray(new Predicate[] {});
	}
	
	public List<?> userNameBasedOnUserID(long orgID) {
		List<?> invApprUserList = em.createNamedQuery(Users.Fetch_UserNameWith_IDFor_ApprovalLOG).setParameter("userOrganization",(int) orgID).getResultList();
		return invApprUserList;	
	}
	
	public void doDeleteAlreadySavedInvoiceApprover(long invoiceID){
		em.createQuery("delete from InvoiceApprover where appInvoiceID = "+invoiceID).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<?> doGetPaymentBasedOnInvoiceID(long invoiceID){		
		List<?> payObj = em.createQuery("select approvalStatus from Payment where invoiceDetail = "+invoiceID).getResultList();
		return payObj;
	}
	
	public void doUpdateInvoiceForInvoiceStatus(long invID, int updateBy) {
		em.createNamedQuery(InvoiceDetails.Update_InvoiceStatus_For_Cancel)
				     .setParameter("invoiceUpdatedBy",updateBy)
				     .setParameter("invoiceStatus",4)
				     .setParameter("invoiceId",invID)
				     .setParameter("invoiceUpdatedDate", new Date()).executeUpdate();
	}
	
	public void udpateInvoiceApproverWhileCancel(long invID, int status, String Comment, int userID) {
		em.createNamedQuery(InvoiceApprover.udpate_For_Approval_Status_In_Cancel)
				     .setParameter("invoiceApprovalAccess",status)
				     .setParameter("appStatus",status)
				     .setParameter("appUpdatedBy",userID)
				     .setParameter("appComment",Comment)
				     .setParameter("appInvoiceID",invID)
				     .setParameter("appUpdatedDate", new Date()).executeUpdate();
	}
	
	public boolean doCheckForDuplicateInvoiceForSameVendor(String benId, String invoiceNo){
		List<?> invoiceObj = em
				.createNamedQuery(InvoiceDetails.Find_FOR_DUPLICATE_INVOICE_WITH_SAME_BEN)
				.setParameter("invoiceNumber", invoiceNo.trim())
				.setParameter("InvoicevenId", Long.valueOf(benId))
				.getResultList();		
		return invoiceObj.size() == 0 ? true :false;		
	}

}
