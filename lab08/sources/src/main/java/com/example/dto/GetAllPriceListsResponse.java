package com.example.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlType;

import com.example.model.PriceList;

@XmlType(name = "GetAllPriceListsResponse")
public class GetAllPriceListsResponse {
	private List<PriceList> priceLists;

	public List<PriceList> getPriceLists() {
		return priceLists;
	}

	public void setPriceLists(List<PriceList> priceLists) {
		this.priceLists = priceLists;
	}
	
}
