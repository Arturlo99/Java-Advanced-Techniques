package com.example.soap.services;

import javax.jws.WebService;

import com.example.dto.GetAllPriceListsResponse;
import com.example.dto.PriceListRequest;

@WebService(name = "PriceListService")
public interface PriceListService {
	public void addPriceList(PriceListRequest priceListRequest);
	public void deletePriceList(long id);
	public GetAllPriceListsResponse getAllPriceLists();
}
