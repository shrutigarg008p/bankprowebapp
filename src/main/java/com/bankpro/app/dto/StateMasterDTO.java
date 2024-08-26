package com.bankpro.app.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.CompanyTypeMaster;
import com.bankpro.app.model.StateMaster;

public class StateMasterDTO {

	private int stateId;
	private String stateName;
	private String stateSortName;
	
	public StateMasterDTO() {
		
	}
	
	public StateMasterDTO(StateMaster stateMaster) {
		
		this.stateId = stateMaster.getStateId();
		this.stateName = stateMaster.getStateName();
		//this.stateSortName = stateMaster.getStateShortName();
		
	}
	
	public static StateMasterDTO mapState(StateMaster stateMaster) {
		return new StateMasterDTO(stateMaster);
	}
	
	public static List<StateMasterDTO> mapFromStateEntities(List<StateMaster> state) {
		return state.stream().map((stateMaster) -> mapState(stateMaster)).collect(Collectors.toList());
	}
	
	
	/**
	 * @return the stateId
	 */
	public int getStateId() {
		return stateId;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}
	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	/**
	 * @return the stateSortName
	 */
	public String getStateSortName() {
		return stateSortName;
	}
	/**
	 * @param stateSortName the stateSortName to set
	 */
	public void setStateSortName(String stateSortName) {
		this.stateSortName = stateSortName;
	}
	
	
	
}
