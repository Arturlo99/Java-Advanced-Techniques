package com.example.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "PaymentRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentRequest {
    private String paymentDate;
    private float paymentAmount;
    private long installationId;
	public float getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public long getInstallationId() {
		return installationId;
	}
	public void setInstallationId(long installationId) {
		this.installationId = installationId;
	}
}
