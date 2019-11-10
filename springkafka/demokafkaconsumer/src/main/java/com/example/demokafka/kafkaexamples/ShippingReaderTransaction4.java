package com.example.demokafka.kafkaexamples;

import java.util.Collections;
import java.util.Iterator;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import static com.example.demokafka.KafkaConfiguration.getTransactionConsumber;
import static com.example.demokafka.KafkaConfiguration.shippingtransactiontopic;

public class ShippingReaderTransaction4 {



    public static void shippingReaderFromTransaction() {
        KafkaConsumer<String, String> consumer = getTransactionConsumber();
        consumer.subscribe(Collections.singletonList(shippingtransactiontopic));

        while (true) {
            waitALittleBit();
            System.out.println("Polled Information");
            PollFromTransactionTopicAndShowMessages(consumer);
        }
    }

    private static void PollFromTransactionTopicAndShowMessages(KafkaConsumer<String, String> consumer) {
        ConsumerRecords<String, String> records = consumer.poll(1000);
        Iterator it = records.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    private static void waitALittleBit() {
        try {
            Thread.sleep(4000);
        } catch (Exception ex) {

        }
    }
}
