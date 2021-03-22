package com.example.geoquiz.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataCountries {
	private String code;
	private String [] currency;
	private String name;
	private String wikiDataId;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String[] getCurrency() {
		return currency;
	}
	public void setCurrency(String[] currency) {
		this.currency = currency;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWikiDataId() {
		return wikiDataId;
	}
	public void setWikiDataId(String wikiDataId) {
		this.wikiDataId = wikiDataId;
	}
}
