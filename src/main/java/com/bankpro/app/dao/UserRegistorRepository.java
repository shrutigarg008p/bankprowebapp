/**
 * 
 */
package com.bankpro.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bankpro.app.model.AuditLogTrail;
import com.bankpro.app.model.CountryMaster;
import com.bankpro.app.model.DocInfoDetail;
import com.bankpro.app.model.NewBankDetails;
import com.bankpro.app.model.OrganizationDetail;
import com.bankpro.app.model.RoleMaster;
import com.bankpro.app.model.UserRoleRelation;
import com.bankpro.app.model.Users;

/**
 * @author Admin
 *
 */
@Repository
public class UserRegistorRepository {

	@PersistenceContext
	private EntityManager em;
	
	
	/**
	 *
	 * save changes made to a user, or insert it if its new
	 *
	 * @param user
	 */
	public Users save(Users user) {
		Users usr = em.merge(user);
		return usr;
	}
	
	public long saveUser(Users user) {
		Users usr = em.merge(user);
		return usr.getUserId();
	}
	
	public void saveDocInfo(DocInfoDetail docInfo) {
		em.merge(docInfo);
	}

	public Long save(NewBankDetails bankDetail) {
		NewBankDetails uu = em.merge(bankDetail);
		return uu.getBankId();
	}
	
	public void save(AuditLogTrail auditTrail) {
		em.merge(auditTrail);
	}

	public List<?> getUserForLogin(String username) {

		List<?> usersArray = em
				.createNamedQuery(Users.FIND_FOR_LOGIN)
				.setParameter("userEmail", username).getResultList();
		return usersArray;
	}
	
	public List<Users> getUserObejectBasedOnUserName(String username) {
		List<Users> usersObj = em
				.createNamedQuery(Users.GET_USER_OBJECT, Users.class)
				.setParameter("userEmail", username).getResultList();
		return usersObj;
	}

	public List<CountryMaster> getCountryList() {

		List<CountryMaster> countryList = em.createNamedQuery(
				CountryMaster.FIND_COUNTRYNAME, CountryMaster.class)
				.getResultList();
		return countryList;
	}

	public List<RoleMaster> roleMasterList() {

		List<RoleMaster> roleList = em.createNamedQuery(RoleMaster.role_Master,RoleMaster.class)
				.setParameter("roleStatus", "Y")
				.getResultList();
		return roleList;
	}
	public List<?> selectedRoleValue() {
		List<?> roleList = em.createNamedQuery(RoleMaster.value_Array_For_Role)
				.setParameter("roleStatus", "Y")
				.getResultList();
		return roleList;
	}

	public List<NewBankDetails> getBankDetailToView(int orgID) {
		List<NewBankDetails> bankList = em.createNamedQuery(
				NewBankDetails.bankDetail, NewBankDetails.class)
				.setParameter("bankOrgId", orgID)
				.getResultList();
		return bankList;
	}
	
	public List<NewBankDetails> getBankObjBasedOnBankID(Long bankID) {

		List<NewBankDetails> bankRefrence = em
				.createNamedQuery(NewBankDetails.find_WithBankID, NewBankDetails.class)
				.setParameter("bankId", bankID).getResultList();
		return bankRefrence;
	}
	
    public boolean isUsernameAvailable(String username) {
    	System.out.println("Users.FIND_BY_USERNAME     "+Users.FIND_BY_USERNAME);
        List<?> users = em.createNamedQuery(Users.FIND_BY_USERNAME)
                .setParameter("userEmail", username)
                .getResultList();
        System.out.println("loist size for userrrrrrr     "+users.size());
        return users.isEmpty();
    }    
 
    
	public boolean isOrganizationNameAvailable(String orgName) {
		List<?> orgName1 = em.createNamedQuery(OrganizationDetail.FIND_FOR_ORG_NAME)
				        .setParameter("orgName", orgName).getResultList();
		System.out.println("loist size for userrrrrrr     " + orgName1.size());
		return orgName1.isEmpty();
	}
	
	public List<?> getFlagToShowCompanyDialog(String username) {
		List<?> users = em
				.createNamedQuery(Users.FLAG_COMPANY_DIALOG_VIEW)
				.setParameter("userEmail", username).getResultList();
		return users;
	}
	
	public List<AuditLogTrail> getAuditTrailList(long refID, String refType) {

		List<AuditLogTrail> auditList = em.createNamedQuery(AuditLogTrail.auditObject,AuditLogTrail.class)
				.setParameter("auditCombineReferenceId", refID)
				.setParameter("auditOperationType", refType)
				.getResultList();
		return auditList;
	}
	
	public void updateToSetAcctDesable(Long bankID) {
	 em.createNamedQuery(NewBankDetails.update_BankStatus)
				.setParameter("bankId", bankID)
				.setParameter("bankActive", false).executeUpdate();
		}
	
	public List<Users> getAllUserList(int orgId) {
    	@SuppressWarnings("unchecked")
		List<Users> users = em.createQuery("from Users where userOrganization = "+ orgId).getResultList();
        System.out.println("list size for user =      "+users.size());
        return users;
    }
	
	public List<Users> searchUserList(String firstName, String showInactive) {
		System.out.println("In Register Repository firstname = " + firstName);
		System.out.println("In Register Repository showInactive = " + showInactive);
		
		String activeStatus = "'Active'";
		if(showInactive.equalsIgnoreCase("true")) {
			activeStatus = "'InActive'";
			System.out.println("In Repository Activation status = " + activeStatus);
		}
		
		// List<Users> usr = em.createNamedQuery(Users.SEARCH_BY_NAME, Users.class).setParameter("activeStatus",activeStatus).setParameter("firstName",firstName + "%" ).getResultList();
		
		String query = "from Users where userStatus in("+ activeStatus +") AND  userFirstName like '%"+ firstName +"%'";
		System.out.println("Final Query is : " + query);
		List<Users> usr = em.createQuery(query).getResultList();
		
		System.out.println("Total Users searched = " + usr.size());
		return usr;
	}
	
	public List<DocInfoDetail> getDocDataForView(int refID, String refType) {
		List<DocInfoDetail> docList = em
				.createNamedQuery(DocInfoDetail.put_doc_Detail,
						DocInfoDetail.class)
				.setParameter("docReferenceType", refType)
				.setParameter("docReferenceId", refID).getResultList();
		return docList;
	}
	
	public void deleteDocInfo(int docID) {		
		 em.createNamedQuery(DocInfoDetail.delete_docInfo)
					.setParameter("docId", docID).executeUpdate();
			}
	
	public void deleteDocInfoFromColumn(int docID, String columnName) {
		String query = "update NewBankDetails set " + columnName + " = null where bankId = " + docID + "";
		em.createQuery(query).executeUpdate();
	}
	public void deleteDocInfoFromColumnOfOrg(int docID, String columnName) {
		String query = "update OrganizationDetail set " + columnName + " = null where orgID = " + docID + "";
		em.createQuery(query).executeUpdate();
	}

	public void  disableUser(long userId) {
		String query = "update Users set userStatus = 'InActive' where userId = " + userId;
		em.createQuery(query).executeUpdate();
	}
	
	public List<?> getOrganizationDetail(int orgID) {
		List<?> orgList = em.createNamedQuery(
				OrganizationDetail.FIND_ORG_DETAIL_BY_ID)
				.setParameter("orgId", orgID)
				.getResultList();
		return orgList;
	}
	
	public List<OrganizationDetail> getOrganizationById(int orgId) {
		List<OrganizationDetail> orgList = em.createNamedQuery( OrganizationDetail.FIND_ORG_DETAIL_OBJ_BY_ID)
				.setParameter("orgId", orgId)
				.getResultList();
		return orgList;
	}
	
	public long  forgotPassword(String userName) {
		String query = "select count(*) from Users where userStatus = 'Active' AND userEmail = " + "'"+userName+"'";
		System.out.println(" finally query is = " + query);

		Long userCount = (Long)em.createQuery(query).getSingleResult();
		
		return userCount;
	}
	
	public RoleMaster saveUserRole(RoleMaster role) {
		RoleMaster roleDetails = em.merge(role);
		return roleDetails;
	}
	
	public void saveRoleRelation(UserRoleRelation roleRelation) {
		em.merge(roleRelation);
	}

}
