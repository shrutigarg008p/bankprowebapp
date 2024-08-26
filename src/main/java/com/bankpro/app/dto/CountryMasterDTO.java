/**
 * 
 */
package com.bankpro.app.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.CountryMaster;

/**
 * @author Admin
 *
 */
public class CountryMasterDTO {
	private int countryID;
	private String countryName;

	public CountryMasterDTO(int countryIdd, String countryNamee) {
		this.countryID = countryIdd;
		this.countryName = countryNamee;

	}


	public static CountryMasterDTO mapCountryEntity(CountryMaster countryy) {
		return new CountryMasterDTO(countryy.getCountryId(),
				countryy.getCountryName());
	}

	public static List<CountryMasterDTO> mapCountryList(
			List<CountryMaster> country) {
		return country.stream().map((countryy) -> mapCountryEntity(countryy))
				.collect(Collectors.toList());
	}

	/**
	 * @return the countryID
	 */
	public int getCountryID() {
		return countryID;
	}

	/**
	 * @param countryID
	 *            the countryID to set
	 */
	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName
	 *            the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
