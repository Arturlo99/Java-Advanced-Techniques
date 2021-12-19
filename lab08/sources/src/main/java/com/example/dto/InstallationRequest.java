package com.example.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "InstallationRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstallationRequest {
	private String routerNumber;
	private String serviceType;
	private String city;
	private String address;
	private String postcode;
	private long clientNumber;
	private long priceListId;

	public String getRouterNumber() {
		return routerNumber;
	}

	public void setRouterNumber(String routerNumber) {
		this.routerNumber = routerNumber;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public long getPriceListId() {
		return priceListId;
	}

	public void setPriceListId(long priceListId) {
		this.priceListId = priceListId;
	}

	public long getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(long clientNumber) {
		this.clientNumber = clientNumber;
	}
}
