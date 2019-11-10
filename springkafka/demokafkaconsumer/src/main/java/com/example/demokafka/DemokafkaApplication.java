package com.example.demokafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demokafka.kafkaexamples.JoinOfTopics5;

@SpringBootApplication
public class DemokafkaApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DemokafkaApplication.class, args);
        System.out.println("Starting application");

        //readOrderPerPollOldWay1();
        //readPersonPerStream();
        //readPersonFromTable();
        //shippingReaderFromTransaction();
        JoinOfTopics5.readPersonAndOrder();

    }
}
