package com.example.soap.services;

import javax.jws.WebService;

import com.example.dto.GetAllInstallationsResponse;
import com.example.dto.InstallationRequest;

@WebService(name = "InstallationService")
public interface InstallationService {
	public void addInstallation(InstallationRequest installationRequest);
	public GetAllInstallationsResponse getAllClientsInstallations(long clientNumber);
	public void deleteInstallation(long id);
}
