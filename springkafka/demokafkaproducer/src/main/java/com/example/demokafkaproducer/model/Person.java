package com.example.demokafkaproducer.model;

public class Person {
    private Integer guid = new Double(Math.random() * 10).intValue();
    private String firstname = "Klaus" + guid;
    private String lastname = "Mueller" + guid;
    private Integer alter = new Double(Math.random() * 100).intValue();

    public Integer getAlter() {
        return alter;
    }

    public void setAlter(Integer alter) {
        this.alter = alter;
    }

    public Integer getGuid() {
        return guid;
    }

    public void setGuid(Integer guid) {
        this.guid = guid;
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
}
