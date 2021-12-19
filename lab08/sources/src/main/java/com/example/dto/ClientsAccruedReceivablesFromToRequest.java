package com.example.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ClientsAccruedReceivablesFromToRequest", propOrder = {"clientNumber", "fromDate", "toDate"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientsAccruedReceivablesFromToRequest {
	 private long clientNumber;
	 private String fromDate;
	 private String toDate;
	 
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public long getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(long clientNumber) {
		this.clientNumber = clientNumber;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
}
