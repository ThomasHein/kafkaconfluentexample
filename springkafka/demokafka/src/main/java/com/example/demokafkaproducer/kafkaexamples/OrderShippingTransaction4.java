package com.example.demokafkaproducer.kafkaexamples;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import static com.example.demokafkaproducer.KafkaEndlessWriterProcessesAsync.createShippingProducer;

public class OrderShippingTransaction4 {

    public void sendOrderShippingTransaction(){
        Producer<String, String> producer = createShippingProducer();
        try {

            producer.initTransactions();
            producer.beginTransaction();

            producer.send(new ProducerRecord<String, String>(
                    "ordertransactiontopic", // is existing
                    //"orderNotExistingtopic", // is not existing
                    "1",
                    "OrderTest"));

            producer.send(new ProducerRecord<String, String>(
                    "shippingtransactiontopic",
                    "1",
                    "Shippingtest"));

            producer.commitTransaction();
        }catch (Exception ex){
            System.out.println(ex);
            producer.abortTransaction();
        }
    }
}
