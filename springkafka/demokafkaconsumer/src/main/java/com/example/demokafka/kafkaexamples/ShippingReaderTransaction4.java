package com.example.demokafka.kafkaexamples;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

import static com.example.demokafka.KafkaKonfiguration.bootstrapservers;
import static com.example.demokafka.KafkaKonfiguration.getTransactionConsumber;

public class ShippingReaderTransaction4 {

    public static void shippingReaderFromTransaction(){
        KafkaConsumer<String,String> consumer = getTransactionConsumber();
        consumer.subscribe(Collections.singletonList("shippingtransactiontopic"));

        while (true){
            waitALittleBit();
            System.out.println("Polled Information");
            PollFromTransactionTopicAndShowMessages(consumer);
        }
    }

    private static void PollFromTransactionTopicAndShowMessages(KafkaConsumer<String, String> consumer) {
        ConsumerRecords<String,String> records = consumer.poll(1000);
        Iterator it = records.iterator();
        while(it.hasNext()){
            System.out.println(it.next().toString());
        }
    }

    private static void waitALittleBit() {
        try{
            Thread.sleep(4000);
        }catch (Exception ex){

        }
    }
}
