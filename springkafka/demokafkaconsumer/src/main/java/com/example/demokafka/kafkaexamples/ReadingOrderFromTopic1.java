package com.example.demokafka.kafkaexamples;

import com.example.demokafka.model.Person;
import com.example.demokafka.model.PersonSerde;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Collections;

import static com.example.demokafka.KafkaKonfiguration.*;


/**
 * Play a little with the writer turn it of and on and set have happend with the
 * consumer, when does it provide an output.
 * What meesages does the consumer take after a restart, all or just the last once?
 */
public class ReadingOrderFromTopic1 {
    /**
     * No nice code
     * only once processing is not guaranteed
     */
    public static void readOrderPerPollOldWay1(){

        KafkaConsumer<String, String> consumer =
                new KafkaConsumer<String, String>(getOrderProperties());
        consumer.subscribe(Collections.singletonList(streamorderinput));
        while (true) {
            waitALittleBit();
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Current order is: " + record.value());
            }
        }
    }

    /**
     * Differences Consumer And Stream API
     * https://stackoverflow.com/questions/44014975/kafka-consumer-api-vs-streams-api
     * Big Big Benefit Quaranteed only once processing
     * Easier selection of information
     */

    public  static void readPersonPerStream(){
        StreamsBuilder builder = new StreamsBuilder();
         //builder.stream(personinput,Consumed.with(Serdes.String(), PersonSerde.getPersonSerde()))
           //     .foreach((key,person)->System.out.println("Gefunden " + key+" "+person.getFirstname()));

        builder.stream(personinput,Consumed.with(Serdes.String(), PersonSerde.getPersonSerde()))
                .groupByKey()
                .count()
                .toStream()
                .foreach((key,value)->System.out.println("Id: "+key+" hat "+value+ "Einträge"));


        KafkaStreams streams = new KafkaStreams(builder.build(), getPersonProperties());
        streams.start();
    }

    private static void waitALittleBit() {
        try{
            Thread.sleep(4000);
        }catch (Exception ex){ }
    }
}
