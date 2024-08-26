package com.bankpro.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bankpro.app.model.Employee;
import com.bankpro.app.model.Vendor;

/**
 * @author Surendra
 *
 */

@Repository
public class EmployeeRepository {
	
	@PersistenceContext
	private EntityManager em;	
	
	


}
