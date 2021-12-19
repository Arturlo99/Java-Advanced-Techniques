package com.example.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.example.model.Installation;

@XmlType(name = "GetAllInstallationsResponse")
public class GetAllInstallationsResponse {
	private List<Installation> installations;

	public List<Installation> getInstallations() {
		return installations;
	}

	public void setInstallations(List<Installation> installations) {
		this.installations = installations;
	}
	
	
}
