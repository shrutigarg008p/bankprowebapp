/**
 * 
 */
package com.bankpro.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Admin
 *
 */
@Entity
@Table(name = "pv_city_master")
public class CitiMaster {
	@Id
	@GeneratedValue
	@Column(name = "city_id")
	private int citiId;
	
	@Column(name = "city_name")
	private String citiName;
	
	@OneToOne(cascade = CascadeType.ALL)
	private StateMaster citiStateId;
	
	@Column(name = "citi-flag")
	private boolean citiFlag;

	/**
	 * @return the citiId
	 */
	public int getCitiId() {
		return citiId;
	}

	/**
	 * @param citiId
	 *            the citiId to set
	 */
	public void setCitiId(int citiId) {
		this.citiId = citiId;
	}

	/**
	 * @return the citiName
	 */
	public String getCitiName() {
		return citiName;
	}

	/**
	 * @param citiName
	 *            the citiName to set
	 */
	public void setCitiName(String citiName) {
		this.citiName = citiName;
	}

	/**
	 * @return the citiStateId
	 */
	public StateMaster getCitiStateId() {
		return citiStateId;
	}

	/**
	 * @param citiStateId
	 *            the citiStateId to set
	 */
	public void setCitiStateId(StateMaster citiStateId) {
		this.citiStateId = citiStateId;
	}

	/**
	 * @return the citiFlag
	 */
	public boolean isCitiFlag() {
		return citiFlag;
	}

	/**
	 * @param citiFlag
	 *            the citiFlag to set
	 */
	public void setCitiFlag(boolean citiFlag) {
		this.citiFlag = citiFlag;
	}

}
