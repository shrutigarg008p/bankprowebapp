package com.bankpro.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "pv_term_master")

@NamedQueries({
	@NamedQuery(name = TermMaster.SELECT_TERM_LIST, query = "from TermMaster ")
})

public class TermMaster {
	
	public static final String SELECT_TERM_LIST = "TermMaster.SELECT_TERM_LIST";
	
	@Id
	@GeneratedValue
	@Column(name = "term_ID")
	private int termId;
	
	@Column(name = "term_name")
	private String termName;
	
	@Column(name = "term_description")
	private String description;
	
	@Column(name = "term_value")
	private int termValue;
	
	
	

	/**
	 * @return the termId
	 */
	public int getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(int termId) {
		this.termId = termId;
	}

	/**
	 * @return the termName
	 */
	public String getTermName() {
		return termName;
	}

	/**
	 * @param termName the termName to set
	 */
	public void setTermName(String termName) {
		this.termName = termName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the termValue
	 */
	public int getTermValue() {
		return termValue;
	}

	/**
	 * @param termValue the termValue to set
	 */
	public void setTermValue(int termValue) {
		this.termValue = termValue;
	}
	
	

}
