/**
 * 
 */
package com.bankpro.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Admin
 *
 */
@Entity
@Table(name = "pv_state_master")

@NamedQueries({
	@NamedQuery(name = StateMaster.SELECT_STATE_LIST, query = "from StateMaster where stateFlag = 1 and stateContMaterId = :stateContMaterId")
})

public class StateMaster {
	
	public static final String SELECT_STATE_LIST = "StateMaster.SELECT_STATE_LIST";
	
	@Id
	@GeneratedValue
	@Column(name = "state_id")
	private int stateId;
	
	@Column(name = "state_countryId")
	private int stateContMaterId;
	
	@Column(name = "state_name")
	private String stateName;
	
	@Column(name = "state_shortName")
	private String stateShortName;
	
	@Column(name = "state_flag")
	private boolean stateFlag;

	/**
	 * @return the stateId
	 */
	public int getStateId() {
		return stateId;
	}

	/**
	 * @param stateId
	 *            the stateId to set
	 */
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the stateContMaterId
	 */
	/*public CountryMaster getStateContMaterId() {
		return stateContMaterId;
	}*/

	/**
	 * @param stateContMaterId
	 *            the stateContMaterId to set
	 */
	/*public void setStateContMaterId(CountryMaster stateContMaterId) {
		this.stateContMaterId = stateContMaterId;
	}*/

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName
	 *            the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return the stateShortName
	 */
	public String getStateShortName() {
		return stateShortName;
	}

	/**
	 * @param stateShortName
	 *            the stateShortName to set
	 */
	public void setStateShortName(String stateShortName) {
		this.stateShortName = stateShortName;
	}

	/**
	 * @return the stateFlag
	 */
	public boolean isStateFlag() {
		return stateFlag;
	}

	/**
	 * @param stateFlag
	 *            the stateFlag to set
	 */
	public void setStateFlag(boolean stateFlag) {
		this.stateFlag = stateFlag;
	}

}
