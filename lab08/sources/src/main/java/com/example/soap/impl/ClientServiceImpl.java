package com.example.soap.impl;

import javax.annotation.PostConstruct;
import javax.jws.WebService;

import com.example.dao.ClientDao;
import com.example.dto.ClientRequest;
import com.example.dto.GetAllClientsResponse;
import com.example.model.Client;
import com.example.soap.services.ClientService;

@WebService(endpointInterface = "com.example.soap.services.ClientService")
public class ClientServiceImpl implements ClientService {
	
	private static ClientDao clientDao; 
	
	@PostConstruct
	public void initialize() {
		clientDao = new ClientDao();
	}
	
	@Override
	public void addClient(ClientRequest clientRequest) {
		Client client = new Client(clientRequest.getFirstName(), clientRequest.getLastName());
		clientDao.save(client);
	}

	@Override
	public GetAllClientsResponse getAllClients() {
		GetAllClientsResponse g = new GetAllClientsResponse();
		g.setAllClients(clientDao.getAll());
		return g;
	}

	@Override
	public void deleteClient(long id) {
		clientDao.delete(new Client(id));
	}
	
	
	

}
