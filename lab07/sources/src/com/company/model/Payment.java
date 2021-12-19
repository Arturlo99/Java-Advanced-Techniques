package com.company.model;

import java.time.LocalDate;

public class Payment {
    private long id;
    private LocalDate paymentDate;
    private float paymentAmount;
    private Installation installation;

    public Payment(LocalDate paymentDate, float paymentAmount, Installation installation) {
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.installation = installation;
    }

    public Payment(long id, LocalDate paymentDate, float paymentAmount, Installation installation) {
        this.id = id;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.installation = installation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public float getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(float paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Installation getInstallation() {
        return installation;
    }

    public void setInstallation(Installation installation) {
        this.installation = installation;
    }
}
