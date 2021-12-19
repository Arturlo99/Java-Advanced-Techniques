package com.example.soap.impl;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.jws.WebService;

import com.example.dao.InstallationDao;
import com.example.dao.PaymentDao;
import com.example.dto.GetAllPaymentsResponse;
import com.example.dto.PaymentRequest;
import com.example.model.Payment;
import com.example.soap.services.PaymentService;

@WebService(endpointInterface = "com.example.soap.services.PaymentService")
public class PaymentServiceImpl implements PaymentService {
	
	private static PaymentDao paymentDao;
	private static InstallationDao installationDao;
	
	@PostConstruct
	public void initialize() {
		paymentDao = new PaymentDao();
		installationDao = new InstallationDao();
	}
	
	@Override
	public void addPayment(PaymentRequest paymentRequest) {
		paymentDao.save(new Payment(LocalDate.parse(paymentRequest.getPaymentDate()) ,
				paymentRequest.getPaymentAmount(), installationDao.get(paymentRequest.getInstallationId())));
	}

	@Override
	public void deletePayment(long id) {
		paymentDao.delete(new Payment(id));
	}

	@Override
	public GetAllPaymentsResponse getAllPayments() {
		GetAllPaymentsResponse response = new GetAllPaymentsResponse();
		response.setPayments(paymentDao.getAll());
		return response;
	}

}
