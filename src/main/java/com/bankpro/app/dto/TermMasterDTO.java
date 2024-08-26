package com.bankpro.app.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.StateMaster;
import com.bankpro.app.model.TermMaster;

public class TermMasterDTO {

	private int termId;
	private String termName;
	private String description;
	private int termValue;
	
	
	public TermMasterDTO() {
		
	}
	
	public TermMasterDTO(TermMaster termMaster) {
	
		this.termId = termMaster.getTermId();
		this.termName = termMaster.getTermName();
		this.description = termMaster.getDescription();
		this.termValue = termMaster.getTermValue();
		
	}
	
	public static TermMasterDTO mapTerm(TermMaster termMaster) {
		return new TermMasterDTO(termMaster);
	}
	
	public static List<TermMasterDTO> mapFromTermEntities(List<TermMaster> term) {
		return term.stream().map((termMaster) -> mapTerm(termMaster)).collect(Collectors.toList());
	}
	

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
