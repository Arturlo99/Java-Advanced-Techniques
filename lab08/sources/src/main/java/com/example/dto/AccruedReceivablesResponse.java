package com.example.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.example.model.AccruedReceivable;

@XmlType(name="AccruedReceivablesResponse")
public class AccruedReceivablesResponse {
	private List<AccruedReceivable> accruedReceivables;

	public List<AccruedReceivable> getAccruedReceivables() {
		return accruedReceivables;
	}

	public void setAccruedReceivables(List<AccruedReceivable> accruedReceivables) {
		this.accruedReceivables = accruedReceivables;
	}
	
	
}
