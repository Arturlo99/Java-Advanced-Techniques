package com.example.soap.impl;

import javax.annotation.PostConstruct;
import javax.jws.WebService;
import com.example.dao.PriceListDao;
import com.example.dto.GetAllPriceListsResponse;
import com.example.dto.PriceListRequest;
import com.example.model.PriceList;
import com.example.soap.services.PriceListService;

@WebService(endpointInterface = "com.example.soap.services.PriceListService")
public class PriceListServiceImpl implements PriceListService {

	private static PriceListDao priceListDao;
	
	@PostConstruct
	public void initialize() {
		priceListDao = new PriceListDao();
	}
	
	@Override
	public void addPriceList(PriceListRequest priceListRequest) {
		priceListDao.save(new PriceList(priceListRequest.getServiceType(),
				priceListRequest.getPrice()));
	}

	@Override
	public GetAllPriceListsResponse getAllPriceLists() {
		GetAllPriceListsResponse response = new GetAllPriceListsResponse();
		response.setPriceLists(priceListDao.getAll());
		return response;
	}

	@Override
	public void deletePriceList(long id) {
		priceListDao.delete(new PriceList(id));
	}

}
