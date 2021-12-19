package com.example.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.example.model.Payment;

@XmlType(name = "GetAllPaymentsResponse")
public class GetAllPaymentsResponse {
	private List<Payment> payments;

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> priceLists) {
		this.payments = priceLists;
	}
	

}
