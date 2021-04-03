package com.example.geoquiz.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataCountryDetails {
	private String capital;
	private String code;
	private String [] currencyCodes;
	private String flagImageUri;
	private String name;
	private String numRegions;
	private String wikiDataId;
	
	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String[] getCurrencyCodes() {
		return currencyCodes;
	}

	public void setCurrencyCodes(String[] currencyCodes) {
		this.currencyCodes = currencyCodes;
	}

	public String getFlagImageUri() {
		return flagImageUri;
	}

	public void setFlagImageUri(String flagImageUri) {
		this.flagImageUri = flagImageUri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumRegions() {
		return numRegions;
	}

	public void setNumRegions(String numRegions) {
		this.numRegions = numRegions;
	}

	public String getWikiDataId() {
		return wikiDataId;
	}

	public void setWikiDataId(String wikiDataId) {
		this.wikiDataId = wikiDataId;
	}


	@Override
	public String toString() {
		return this.capital;
	}
}
