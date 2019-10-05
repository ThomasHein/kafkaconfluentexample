package com.example.demokafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.demokafka.kafkaexamples.ReadingOrderFromTopic1.readPersonPerStream;

@SpringBootApplication
public class DemokafkaApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DemokafkaApplication.class, args);
        System.out.println("Starting application");

        //readOrderPerPollOldWay1();
        readPersonPerStream();
        //readPersonFromTable();
        //shippingReaderFromTransaction();

    }
}