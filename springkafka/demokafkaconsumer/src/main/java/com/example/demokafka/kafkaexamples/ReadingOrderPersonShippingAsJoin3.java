package com.example.demokafka.kafkaexamples;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;

import java.time.Duration;

import static com.example.demokafka.KafkaConfiguration.personinput;
import static com.example.demokafka.KafkaConfiguration.streamorderinput;

public class ReadingOrderPersonShippingAsJoin3 {
    public void joinSeveralInputs(){

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String,String> orders = builder.stream(streamorderinput);
        KStream<String,String> persons = builder.stream(personinput);

        KStream personsOrders = orders.join(persons,
                (orderValue,personValue)-> orderValue+personValue,
                JoinWindows.of(Duration.ofSeconds(20)),
                Joined.with(
                        Serdes.String(), /* key */
                        Serdes.String(), /* left value */
                        Serdes.String()  /* right value */
                )
        );


    }
}
