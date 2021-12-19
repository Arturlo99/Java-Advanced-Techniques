package com.company.model;


public class Installation {
    private long id;
    private String routerNumber;
    private String serviceType;
    private String city;
    private String address;
    private String postcode;
    private Client client;
    private PriceList priceList;

    public Installation(long id) {
        this.id = id;
    }

    public Installation(long id, String routerNumber, String serviceType, String city, String address,
                        String postcode, Client client, PriceList priceList) {
        this.id = id;
        this.routerNumber = routerNumber;
        this.serviceType = serviceType;
        this.city = city;
        this.address = address;
        this.postcode = postcode;
        this.client = client;
        this.priceList = priceList;
    }

    public Installation(String routerNumber, String serviceType, String city, String address,
                        String postcode, Client client, PriceList priceList) {
        this.routerNumber = routerNumber;
        this.serviceType = serviceType;
        this.city = city;
        this.address = address;
        this.postcode = postcode;
        this.client = client;
        this.priceList = priceList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRouterNumber() {
        return routerNumber;
    }

    public void setRouterNumber(String routerNumber) {
        this.routerNumber = routerNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

}
