package com.example.demokafka;

import com.example.demokafka.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.data.Date;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@Service
public class KafkaEndlessWriter {


    private EndlessDbWriter endlessDbWriter;

    @Autowired
    public KafkaEndlessWriter(EndlessDbWriter endlessDbWriter){
        this.endlessDbWriter = endlessDbWriter;
    }

    private final static String BOOTSTRAP_SERVERS =
            "192.168.99.100:39092";

    public static Producer<String, String> createPPersonroducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put("acks", "all");

        props.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG,"10000");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExamplePersonProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    public static Producer<String, String> createOrderProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put("acks", "all");

        props.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG,"10000");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleOrderProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    public void startEndlessWriter(){

        Runnable taskOrder = () -> {
            new EndlessOrderWriter().endlessOrderWriter();
        };

        Runnable task = () -> {
            new EndlessPersonWriter().endlessPersonWriter();
        };

        Runnable taskDb = () -> {
            this.endlessDbWriter.write();
        };

        Thread th = new Thread(task);
        Thread th2 = new Thread(taskOrder);
        Thread th3 = new Thread(taskDb);
        th.start();
        th2.start();
        th3.start();

    }


}
