package com.example.model;

import java.time.LocalDate;

public class AccruedReceivable {
    private long id;
    private LocalDate paymentDeadline;
    private float amountToPay;
    private Installation installation;

    public AccruedReceivable(long id) {
        this.id = id;
    }

    public AccruedReceivable(LocalDate paymentDeadline, float amountToPay, Installation installation) {
        this.paymentDeadline = paymentDeadline;
        this.amountToPay = amountToPay;
        this.installation = installation;
    }

    public AccruedReceivable(long id, LocalDate paymentDeadline, float amountToPay, Installation installation) {
        this.id = id;
        this.paymentDeadline = paymentDeadline;
        this.amountToPay = amountToPay;
        this.installation = installation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(LocalDate paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public float getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(float amountToPay) {
        this.amountToPay = amountToPay;
    }

    public Installation getInstallation() {
        return installation;
    }

    public void setInstallation(Installation installation) {
        this.installation = installation;
    }
}
