package com.example.soap.services;

import javax.jws.WebService;

import com.example.dto.ClientRequest;
import com.example.dto.GetAllClientsResponse;

@WebService(name = "ClientService")
public interface ClientService {
	public void addClient(ClientRequest clientRequest);
	public GetAllClientsResponse getAllClients();
	public void deleteClient(long id);
}
