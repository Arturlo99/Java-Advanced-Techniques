package com.example.soap.services;

import javax.jws.WebService;

import com.example.dto.GetAllPaymentsResponse;
import com.example.dto.PaymentRequest;

@WebService(name = "PaymentService")
public interface PaymentService {
	public void addPayment(PaymentRequest paymentRequest);
	public void deletePayment(long id);
	public GetAllPaymentsResponse getAllPayments();
}
