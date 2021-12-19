package com.example.dto;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "AccruedReceivableRequest")
public class AccruedReceivableRequest {
	private String paymentDeadline;
	private float amountToPay;
	private long installationId;
	
	public String getPaymentDeadline() {
		return paymentDeadline;
	}
	public void setPaymentDeadline(String paymentDeadline) {
		this.paymentDeadline = paymentDeadline;
	}
	public long getInstallationId() {
		return installationId;
	}
	public void setInstallationId(long installationId) {
		this.installationId = installationId;
	}
	public float getAmountToPay() {
		return amountToPay;
	}
	public void setAmountToPay(float amountToPay) {
		this.amountToPay = amountToPay;
	}
	
}
