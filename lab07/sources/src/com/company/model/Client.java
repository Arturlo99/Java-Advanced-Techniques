package com.company.model;

public class Client {
    private long clientNumber;
    private String firstName;
    private String lastName;

    public Client(long clientNumber) {
        this.clientNumber = clientNumber;
    }

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Client(long id, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(long clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
