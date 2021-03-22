package com.example.geoquiz.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SecondQuote {
	private DataCities[] data;
	private Link[] link;
	private Metadata metadata;
	public DataCities[] getData() {
		return data;
	}
	public void setData(DataCities[] data) {
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
