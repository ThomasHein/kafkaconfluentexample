package com.example.demokafka.kafkaexamples;

import com.example.demokafka.model.Person;
import com.example.demokafka.model.PersonSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;


import static com.example.demokafka.KafkaConfiguration.getMaterializedViewProperties;

public class MaterializedViewPerson2 {

    public KafkaStreams getTable() throws InterruptedException {
        final StreamsBuilder builder = new StreamsBuilder();
        builder.stream("streams-person-input",Consumed.with(Serdes.String(),PersonSerde.getPersonSerde()))
                .filter((k,v)-> v.toString().toLowerCase().contains("mueller0"))
                .groupByKey()
                .reduce(
                        (current, newest)->{
                           return newest;
                        }
                        ,
                        Materialized.<String, Person, KeyValueStore<Bytes, byte[]>>as("personsStore")
                );
        KafkaStreams streams = new KafkaStreams(builder.build(),getMaterializedViewProperties());
        streams.start();
        return streams;
    }

    public static void readPersonFromTable() throws InterruptedException {
        KafkaStreams streams = new MaterializedViewPerson2().getTable();

        System.out.println("Started");
        Thread.sleep(5000);
        ReadOnlyKeyValueStore<String, Person> keyValueStore =
                streams.store("personsStore", QueryableStoreTypes.keyValueStore());

        int counter = 0;
        while (true) {

            System.out.println("****\n*****\n");
            KeyValueIterator<String, Person> iterator = keyValueStore.all();
            counter = 0;
            while (iterator.hasNext()) {
                KeyValue<String, Person> k = iterator.next();
                Person p = k.value;
                System.out.println("count for hello:" + p.getFirstname());
                counter++;
            }

            System.out.println("count: " + counter);
            Thread.sleep(100000);

        }
    }
}
