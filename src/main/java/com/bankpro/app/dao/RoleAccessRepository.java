package com.bankpro.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bankpro.app.model.RoleMaster;
import com.bankpro.app.model.TermMaster;


/**
 * @author Surendra
 *
 */

@Repository
public class RoleAccessRepository {

	@PersistenceContext
	private EntityManager em;
	
	public List<RoleMaster> getRoleList(int orgId) {
		System.out.println("In Role Access Repository : get Role by org id = " + orgId);
		List<RoleMaster> roleList = em.createNamedQuery(RoleMaster.FETCH_ROLE_LIST, RoleMaster.class).setParameter("orgId", orgId).getResultList();
		return roleList;
	}
	
	public void save(RoleMaster roleObj) {
		em.merge(roleObj);
	}
	
	public List<String> getUserPermissionList(String finalRole) {
		System.out.println("In RoleAccessRepository to get All permission of user on RoleId basis role ids are : " + finalRole);
		
		String query = "select roleDesciption from RoleMaster where roleId in ("+finalRole+")";
		System.out.println("In RoleAccessRepository Final Query = " + query);
		List<String> param = new ArrayList<String>();
		if(finalRole !=null && (!finalRole.equals(""))) {
			param = em.createQuery(query).getResultList();
		}
		System.out.println("Total Permissions = " + param);
		
		if(param != null  && param.size()> 0 ) {
			System.out.println("Permission found it is : " + param);
		}else{
			System.out.println("Permissions not found");
		}
		
		return param;
	}
	
	public List<TermMaster> getPaymentTermList() {
		List<TermMaster> termList = em.createNamedQuery(TermMaster.SELECT_TERM_LIST,TermMaster.class).getResultList();
		return termList;
	}
	
}
