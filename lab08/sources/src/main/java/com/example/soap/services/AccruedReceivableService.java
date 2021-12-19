package com.example.soap.services;

import javax.jws.WebService;

import com.example.dto.ClientsAccruedReceivablesFromToRequest;
import com.example.dto.AccruedReceivableRequest;
import com.example.dto.AccruedReceivablesResponse;

@WebService(name = "AccruedReceivableService")
public interface AccruedReceivableService {
	AccruedReceivablesResponse getClientsChargesFromTo(ClientsAccruedReceivablesFromToRequest a);
	public void deleteAccruedReceivable(long id);
	public void addAccruedReceivable(AccruedReceivableRequest a);
}
