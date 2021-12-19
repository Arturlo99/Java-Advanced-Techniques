package com.example.soap.impl;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.jws.WebService;
import com.example.dao.AccruedReceivableDao;
import com.example.dao.InstallationDao;
import com.example.dto.ClientsAccruedReceivablesFromToRequest;
import com.example.dto.AccruedReceivableRequest;
import com.example.dto.AccruedReceivablesResponse;
import com.example.model.AccruedReceivable;
import com.example.soap.services.AccruedReceivableService;

@WebService(endpointInterface = "com.example.soap.services.AccruedReceivableService")
public class AccruedReceivableServiceImpl implements AccruedReceivableService {

	private static AccruedReceivableDao accruedReceivableDao;
	private static InstallationDao installationDao;

	@PostConstruct
	public void initialize() {
		accruedReceivableDao = new AccruedReceivableDao();
		installationDao = new InstallationDao();
	}

	@Override
	public AccruedReceivablesResponse getClientsChargesFromTo(ClientsAccruedReceivablesFromToRequest a) {
		AccruedReceivablesResponse response = new AccruedReceivablesResponse();
		response.setAccruedReceivables(
				accruedReceivableDao.getFromTo(a.getClientNumber(), a.getFromDate(), a.getToDate()));
		return response;
	}

	@Override
	public void deleteAccruedReceivable(long id) {
		accruedReceivableDao.delete(new AccruedReceivable(id));
	}

	@Override
	public void addAccruedReceivable(AccruedReceivableRequest a) {
		accruedReceivableDao.save(new AccruedReceivable(LocalDate.parse(a.getPaymentDeadline()), a.getAmountToPay(),
				installationDao.get(a.getInstallationId())));
	}
}
