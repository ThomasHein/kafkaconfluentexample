package com.example.demokafka.model;

public class PersonOrder {
    private String firstname;
    private String lastname;
    private Double price;

    public PersonOrder(String firstname, String lastname, Double price) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.price = price;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
