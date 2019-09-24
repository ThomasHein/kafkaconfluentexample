package com.example.demokafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.demokafka.kafkaexamples.MaterializedViewPerson2.readPersonFromTable;
import static com.example.demokafka.kafkaexamples.ShippingReaderTransaction4.shippingReaderFromTransaction;

@SpringBootApplication
public class DemokafkaApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DemokafkaApplication.class, args);
        System.out.println("Starting application");

        //readPersonFromTable();
        //shippingReaderFromTransaction();

    }
}
