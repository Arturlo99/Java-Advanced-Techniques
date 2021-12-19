package com.example.soap.impl;

import javax.annotation.PostConstruct;
import javax.jws.WebService;

import com.example.dao.ClientDao;
import com.example.dao.InstallationDao;
import com.example.dao.PriceListDao;
import com.example.dto.GetAllInstallationsResponse;
import com.example.dto.InstallationRequest;
import com.example.model.Installation;
import com.example.soap.services.InstallationService;

@WebService(endpointInterface = "com.example.soap.services.InstallationService")
public class InstallationServiceImpl implements InstallationService {

	private static InstallationDao installationDao;
	private static PriceListDao priceListDao;
	private static ClientDao clientDao;

	@PostConstruct
	public void initialize() {
		installationDao = new InstallationDao();
		priceListDao = new PriceListDao();
		clientDao = new ClientDao();
	}

	@Override
	public void addInstallation(InstallationRequest iR) {
		Installation installation = new Installation(iR.getRouterNumber(), iR.getServiceType(), iR.getCity(),
				iR.getAddress(), iR.getPostcode(), clientDao.get(iR.getClientNumber()) , priceListDao.get(iR.getPriceListId()));
		installationDao.save(installation);
	}

	@Override
	public GetAllInstallationsResponse getAllClientsInstallations(long clientNumber) {
		GetAllInstallationsResponse gair = new GetAllInstallationsResponse();
		gair.setInstallations(installationDao.getAll());
		return gair;
	}

	@Override
	public void deleteInstallation(long id) {
		installationDao.delete(new Installation(id));
	}

}
