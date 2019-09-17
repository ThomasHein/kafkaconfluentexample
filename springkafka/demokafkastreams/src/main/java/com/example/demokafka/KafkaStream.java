package com.example.demokafka;

import com.example.demokafka.model.Person;
import com.example.demokafka.model.PersonSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import scala.reflect.io.File;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class KafkaStream {

    public KafkaStreams getTable() throws InterruptedException {
        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:39092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        // setting offset reset to earliest so that we can re-run the demo code with the same pre-loaded data
        // Note: To re-run the demo, you need to use the offset reset tool:
        // https://cwiki.apache.org/confluence/display/KAFKA/Kafka+Streams+Application+Reset+Tool
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        final StreamsBuilder builder = new StreamsBuilder();

        KTable<String, Person> stream =  builder.stream("streams-person-input",Consumed.with(Serdes.String(),PersonSerde.getPersonSerde()))
                .filter((k,v)-> v.toString().toLowerCase().contains("mueller0"))
                .groupByKey()
                .reduce(
                        (current, newest)->{
                           return newest;
                        }
                        ,
                        Materialized.<String, Person, KeyValueStore<Bytes, byte[]>>as("personsStore"));

        //stream.toStream().to("streams-wordcount-output");
        KafkaStreams streams = new KafkaStreams(builder.build(),props);
        streams.start();
        return streams;



        //return streams.store(persons.queryableStoreName(), QueryableStoreTypes.keyValueStore());

    }
}
