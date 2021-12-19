package com.example.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.example.model.Client;

@XmlType(name = "GetAllClientsResponse")
public class GetAllClientsResponse {
	private List<Client> clients;

	public List<Client> getAllClients() {
		return clients;
	}

	public void setAllClients(List<Client> allClients) {
		this.clients = allClients;
	}

}
