package com.example.demokafkaproducer.kafkaexamples;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import static com.example.demokafkaproducer.KafkaConfigurations.createShippingProducer;
import static com.example.demokafkaproducer.KafkaConfigurations.orderTransactionTopic;
import static com.example.demokafkaproducer.KafkaConfigurations.shippingTransactionTopic;

public class OrderShippingTransaction4 {

    public void sendOrderShippingTransaction() {
        Producer<String, String> producer = createShippingProducer();
        try {
            producer.initTransactions();
            producer.beginTransaction();
            producer.send(new ProducerRecord<String, String>(orderTransactionTopic, // is existing
                    //"orderNotExistingtopic", // is not existing
                    "1", "OrderTest"));

            producer.send(new ProducerRecord<String, String>(shippingTransactionTopic, "1", "Shippingtest"));
            producer.commitTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            producer.abortTransaction();
        }
    }
}
