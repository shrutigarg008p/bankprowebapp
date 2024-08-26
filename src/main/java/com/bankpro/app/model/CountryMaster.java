/**
 * 
 */
package com.bankpro.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Admin
 *
 */
@Entity
@Table(name = "pv_country_master")
@NamedQueries({
	@NamedQuery(name = CountryMaster.FIND_COUNTRYNAME, query = "from CountryMaster")})
public class CountryMaster {
	public static final String FIND_COUNTRYNAME = "CountryMaster.findcountry";
	@Id
	@GeneratedValue
	@Column(name = "country_id")
	private int countryId;
	
	@Column(name = "country_name")
	private String countryName;
	
	@Column(name = "country_shortName")
	private String countryShortName;
	
	@Column(name = "country_flag")
	private boolean countryFlag;
	/**
	 * @return the countryId
	 */
	public int getCountryId() {
		return countryId;
	}
	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}
	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	/**
	 * @return the countryShortName
	 */
	public String getCountryShortName() {
		return countryShortName;
	}
	/**
	 * @param countryShortName the countryShortName to set
	 */
	public void setCountryShortName(String countryShortName) {
		this.countryShortName = countryShortName;
	}
	/**
	 * @return the countryFlag
	 */
	public boolean isCountryFlag() {
		return countryFlag;
	}
	/**
	 * @param countryFlag the countryFlag to set
	 */
	public void setCountryFlag(boolean countryFlag) {
		this.countryFlag = countryFlag;
	}
	
}
