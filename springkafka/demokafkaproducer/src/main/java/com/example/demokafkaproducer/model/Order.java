package com.example.demokafkaproducer.model;

public class Order {

    private Integer id = new Double(Math.random() * 1000).intValue();
    private double amount = Math.random() * 1000;
    private Integer personId = new Double(Math.random() * 10).intValue();
    private String car = "Trabbi";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }
}
