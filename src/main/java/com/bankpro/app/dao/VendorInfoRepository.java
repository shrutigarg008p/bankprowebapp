package com.bankpro.app.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bankpro.app.model.ApproveVendor;
import com.bankpro.app.model.AuditLogTrail;
import com.bankpro.app.model.CompanyTypeMaster;
import com.bankpro.app.model.DocInfoDetail;
import com.bankpro.app.model.InvoiceDetails;
import com.bankpro.app.model.PaymentMethodMaster;
import com.bankpro.app.model.StateMaster;
import com.bankpro.app.model.TermMaster;
import com.bankpro.app.model.Users;
import com.bankpro.app.model.Vendor;
import com.bankpro.app.model.VendorTypeMaster;

@Repository
public class VendorInfoRepository {

	@PersistenceContext
	private EntityManager em;

	public long saveVendor(Vendor ven) {
		Vendor vend = em.merge(ven);
		return vend.getId();
	}

	public Vendor saveVendorObj(Vendor ven) {
		Vendor vend = em.merge(ven);
		return vend;
	}
	
	public void saveAuditTrail(AuditLogTrail auditTrail) {
		em.merge(auditTrail);
	}
	
	public void saveDocInfo(DocInfoDetail docInfo) {
		em.merge(docInfo);
	}

	public List<Vendor> getVendorList(int orgID, int fetchCondition) {
		List<Vendor> vendorList = em
				.createNamedQuery(Vendor.FETCH_VENDOR_OBJ, Vendor.class)
				.setParameter("vendorOrgID", orgID)
				.setParameter("benCategorizeValue", fetchCondition) .getResultList();
		return vendorList;
	}
	
	   public Object[] fetchUserIdAndOrgObjBasedOnUserName(String username) {
			List<?> usersObj = em
					.createNamedQuery(Users.FIND_USER_ID_BASED_ON_USERNAME)
					.setParameter("userEmail", username).getResultList();		
			return (Object[])usersObj.get(0);
		}

	public List<Vendor> getVendorObjBasedOnIDToUpdate(long venID) {
		List<Vendor> vendorList = em
				.createNamedQuery(Vendor.VENDOR_OBJ_BASED_ON_VENDID,
						Vendor.class).setParameter("id", venID).getResultList();
		return vendorList;

	}
	
	public void deleteDocInfoFromColumnOfVendor(int docID, String columnName) {
		String query = "update Vendor set " + columnName + " = null where id = " + docID + "";
		em.createQuery(query).executeUpdate();
	}
	
	public void changeVendorStatus(long venId) {
		String query = "update Vendor set verified = 1 where id = " + venId + "";
		em.createQuery(query).executeUpdate();
	}
	
	public void udpatePermissionFlag(long venID, boolean permissionFlag) {
		em.createNamedQuery(Vendor.Update_Permission_FLAG)
		.setParameter("id", venID)
		.setParameter("permissionFlag", permissionFlag).executeUpdate();
	}
	
	public void udpatePermissionFla(long venId) {
		String query = "update Vendor set verified = 1 where id = " + venId + "";
		em.createQuery(query).executeUpdate();
	}
	
	public List<Users> getUserObejectBasedOnUserName(String username) {
		List<Users> usersObj = em
				.createNamedQuery(Users.GET_USER_OBJECT, Users.class)
				.setParameter("userEmail", username).getResultList();
		return usersObj;
	}
	
	public List<VendorTypeMaster> getVendorTypeList() {
		List<VendorTypeMaster> vendorTypeList = em.createNamedQuery(VendorTypeMaster.FETCH_VENDOR_TYPE_LIST,
				VendorTypeMaster.class).getResultList();
		return vendorTypeList;
	}
	
	public List<CompanyTypeMaster> getCompanyTypeList() {
		List<CompanyTypeMaster> companyTypeList = em.createNamedQuery(CompanyTypeMaster.FETCH_COMPANY_TYPE_LIST,
				CompanyTypeMaster.class).getResultList();
		return companyTypeList;
	}

	public List<StateMaster> getStateList(int countryId) {
		List<StateMaster> stateList = em.createNamedQuery(StateMaster.SELECT_STATE_LIST,
				StateMaster.class)
				.setParameter("stateContMaterId", countryId).getResultList();
		return stateList;
	}
	
	public List<TermMaster> getPaymentTermList() {
		List<TermMaster> termList = em.createNamedQuery(TermMaster.SELECT_TERM_LIST,
				TermMaster.class).getResultList();
		return termList;
	}
	
	public List<PaymentMethodMaster> getPaymentMethodList() {
		List<PaymentMethodMaster> payMethodList = em.createNamedQuery(PaymentMethodMaster.SELECT_PAYMENT_METHOD_LIST,
				PaymentMethodMaster.class).getResultList();
		return payMethodList;
	}
	
	public List<Vendor> getVendorListForApproval(int orgId) {
		List<Vendor> vendorList = em.createNamedQuery(Vendor.FETCH_VENDOR_OBJ_FOR_APPROVAL,
				Vendor.class).setParameter("orgId", orgId).getResultList();
		return vendorList;
	}
	
	public void approveVendor(long vendorId) {
		String query = "update Vendor set permissionFlag = 1 where id = " + vendorId + "";
		em.createQuery(query).executeUpdate();
	}
	
	public void declineVendor(long vendorId) {
		String query = "update Vendor set permissionFlag = 2 where id = " + vendorId + "";
		em.createQuery(query).executeUpdate();
	}
	
	public void saveApproveVendor(ApproveVendor appVendor) {
		em.merge(appVendor);
	}
	
	public List<ApproveVendor> getApproverObjList(long vendorId) {
		List<ApproveVendor> vendApprObjList = em.createNamedQuery(ApproveVendor.FETCH_APPROVE_VENDOR_OBJ,
				ApproveVendor.class).setParameter("vendorId", vendorId).getResultList();
		return vendApprObjList;	
	}
	
	public List<Vendor> searchVendorForApproval(String startDate, String endDate, String supplierName, int orgId) {
		
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

		StringBuffer query = new StringBuffer("from Vendor where vendorOrgID = " + orgId);
		query.append(" AND verified = 0");
		query.append(" AND permissionFlag = 0");
		
		if( (convertedStartDate != null) &&(!convertedStartDate.equals(""))) {
			query.append(" AND ( createdDate >= " + "'" + convertedStartDate + "'");
		}
		if( (convertedEndDate != null) && (!convertedEndDate.equals(""))) {
			query.append(" AND createdDate < " + "'" + convertedEndDate + "' ) ");
		}
		if( (supplierName != null) && (!supplierName.equals("")) ) {
			//query.append(" AND userId.userFirstName like '%" + firstName + "%' "  );
			query.append(" AND supplierName like '%" + supplierName + "%' "  );
		}
		
		System.out.println("Final query is    " + query);
		List<Vendor> vendorList = em.createQuery(query.toString()).getResultList();
		System.out.println("Data Found Count = " + vendorList.size());
		return vendorList;
		
	}
	
	public List<ApproveVendor> getVendorApprovalHistoryList(long userId) {
		List<ApproveVendor> vendorHistoryList = em.createNamedQuery(ApproveVendor.FETCH_VENDOR_APPROVAL_HISTORY,
				ApproveVendor.class).setParameter("userId", userId).getResultList();
		return vendorHistoryList;
	}
	
public List<Vendor> searchVendorApprovalHistory(String startDate, String endDate, String supplierName, int orgId) {
		
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

		StringBuffer query = new StringBuffer("from Vendor where vendorOrgID = " + orgId);
		query.append(" AND verified = 0");
		query.append(" AND permissionFlag in(1,2)");
		
		if( (convertedStartDate != null) &&(!convertedStartDate.equals(""))) {
			query.append(" AND ( createdDate >= " + "'" + convertedStartDate + "'");
		}
		if( (convertedEndDate != null) && (!convertedEndDate.equals(""))) {
			query.append(" AND createdDate < " + "'" + convertedEndDate + "' ) ");
		}
		if( (supplierName != null) && (!supplierName.equals("")) ) {
			//query.append(" AND userId.userFirstName like '%" + firstName + "%' "  );
			query.append(" AND supplierName like '%" + supplierName + "%' "  );
		}
		
		System.out.println("Final History query is    " + query);
		List<Vendor> vendorList = em.createQuery(query.toString()).getResultList();
		System.out.println("Data Found Count = " + vendorList.size());
		return vendorList;
		
	}

	public List<Vendor> searchVendorList(String suplierName,String showInactive, int orgId) {
		String activeStatus = "0";
		if (showInactive.equalsIgnoreCase("true")) {
			activeStatus = "1";
			System.out.println("In VendorInfoRepository, Activation status = "	+ activeStatus);
		}
		String query = "from Vendor where vendorOrgID = " + orgId + " AND verified = " + activeStatus
				+ " AND  supplierName like '%" + suplierName + "%' and venBenCategorizeValue = 1";
		System.out.println("In Vendor Info Repository Final Query is : "+ query);
		List<Vendor> vendorList = em.createQuery(query).getResultList();
		System.out.println("Total Vendor searched = " + vendorList.size());
		return vendorList;
	}
	
	public List<Vendor> searchEmployees(String name, int orgId, int fetchCondition) {
		List<Vendor> venList = em.createNamedQuery(Vendor.SEARCH_BY_NAME_ForEmployee, Vendor.class)
				.setParameter("vendorOrgID", orgId)
				.setParameter("benCategorizeValue", fetchCondition)
				.setParameter("supplierName","%" +name+ "%").getResultList();
		return venList;
	}
	
}
