package com.example.demokafka.kafkaexamples;

import com.example.demokafka.model.Person;
import com.example.demokafka.model.PersonSerde;
import com.example.demokafkaproducer.model.OrderAvro;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import com.example.demokafka.kafkaexamples.*;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.example.demokafka.KafkaConfiguration.*;

public class JoinOfTopics5 {

    private static final String personTopic = "streams-person-input";
    private static final String orderTopic = "streams-order-input";

    public  static void readPersonAndOrder(){

        GenericAvroSerde avroSerde = new GenericAvroSerde();
        Map config = new HashMap<String,String>();
        config.put("schema.registry.url", "http://localhost:8081");
        avroSerde.configure(config,false);

        StreamsBuilder builder = new StreamsBuilder();

        final KStream<String, Person> persons = builder.stream(personTopic);
        final KStream<String, GenericRecord> orders = builder.stream(orderTopic);;

        persons.leftJoin(
                orders,
                ( personValue, orderValue) -> {
                    if(personValue.getGuid() == orderValue.get("id")){
                       return personValue.getGuid();
                    }
                    return "";
                },
                JoinWindows.of(Duration.ofSeconds(10)),
                Joined.with(
                        Serdes.String(), /* key */
                        PersonSerde.getPersonSerde(),
                        avroSerde
                )
        ).foreach((key,value) -> System.out.println(key+" "+value)) ;

        KafkaStreams streams = new KafkaStreams(builder.build(), getPersonJoinProperties());
        streams.start();
    }
}
