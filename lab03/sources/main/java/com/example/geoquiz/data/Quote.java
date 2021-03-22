package com.example.geoquiz.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
	private DataCountryDetails[] data;
	private Link[] link;
	private Metadata metadata;

	public DataCountryDetails[] getData() {
		return data;
	}

	public void setData(DataCountryDetails[] data) {
		this.data = data;
	}

	public Link[] getLink() {
		return link;
	}

	public void setLink(Link[] link) {
		this.link = link;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	
}
