package com.example.geoquiz.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FirstQuote {
	private DataCountryDetails data;

	public DataCountryDetails getData() {
		return data;
	}

	public void setData(DataCountryDetails data) {
		this.data = data;
	}
}
