package com.example.demokafkaproducer.model;

public class OrderSuccess {

    private Integer personId = new Double(Math.random()*10).intValue();
    private Integer orderId = new Double(Math.random()*1000).intValue();
    private String status = "Success";

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
}
