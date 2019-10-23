package com.example.demokafka.kafkaexamples;

import com.example.demokafka.model.Order;
import com.example.demokafka.model.OrderSerde;
import com.example.demokafka.model.Person;
import com.example.demokafka.model.PersonOrder;
import com.example.demokafka.model.PersonSerde;
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
import com.example.demokafkaproducer.model.OrderAvro;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.example.demokafka.KafkaConfiguration.*;

public class JoinOfTopics5 {



    public  static void readPersonAndOrder(){


        StreamsBuilder builder = new StreamsBuilder();

        final KStream<String, Person> persons = builder.stream(personinput,Consumed.with(Serdes.String(),PersonSerde.getPersonSerde()));
        final KStream<String, Order> orders = builder.stream(streamorderinput,Consumed.with(Serdes.String(), OrderSerde.getOrderSerde()));
        persons.join(
                orders,
                ( personValue, orderValue) -> {
                    if(personValue.getGuid()==orderValue.getPersonId()){
                        return new PersonOrder(personValue.getFirstname(),personValue.getLastname(),orderValue.getAmount());
                    }
                    return null;
                },
                JoinWindows.of(Duration.ofNanos(30)),
                Joined.with(
                        Serdes.String(), /*key*/
                        PersonSerde.getPersonSerde(),/*left*/
                        OrderSerde.getOrderSerde() /*right*/
                )
        )
        .filter((key,value) -> value!=null)
        .foreach((key,value)->{
            if(value==null){
                System.out.println("null join");
            } else {
                System.out.println("Joined Order for" +value.getFirstname()+value.getLastname());
            }

        });

        KafkaStreams streams = new KafkaStreams(builder.build(), getPersonJoinProperties());
        streams.start();
    }
}
