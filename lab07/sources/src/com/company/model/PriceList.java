package com.company.model;

public class PriceList {
    private long id;
    private String serviceType;
    private float price;

    public PriceList(long id) {
        this.id = id;
    }

    public PriceList(String serviceType, float price) {
        this.serviceType = serviceType;
        this.price = price;
    }

    public PriceList(long id, String serviceType, float price) {
        this.id = id;
        this.serviceType = serviceType;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
