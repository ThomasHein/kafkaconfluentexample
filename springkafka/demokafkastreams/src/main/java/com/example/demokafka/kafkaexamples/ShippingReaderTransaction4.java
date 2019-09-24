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

public class ShippingReaderTransaction4 {

    public void ShippingReaderFromTransaction(){

        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "transaction-shipping");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:39092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Collections.singletonList("shippingtransactiontopic"));

        while (true){
            try{
                Thread.sleep(4000);
            }catch (Exception ex){

            }
            ConsumerRecords<String,String> records = consumer.poll(1000);
            System.out.println("Polled Information");
            Iterator it = records.iterator();
            while(it.hasNext()){
                System.out.println(it.next().toString());
            }
        }




    }
}
