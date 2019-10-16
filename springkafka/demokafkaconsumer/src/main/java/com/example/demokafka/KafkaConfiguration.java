package com.example.demokafka;

import com.example.demokafka.model.Person;
import com.example.demokafka.model.PersonSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

public class KafkaConfiguration {

    public static String bootstrapservers = "192.168.99.100:39092";
    public static String streamorderinput = "streams-order-input";
    public static String personinput = "streams-person-input";

    public static Properties getMaterializedViewProperties(){
         Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapservers);
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        // setting offset reset to earliest so that we can re-run the demo code with the same pre-loaded data
        // Note: To re-run the demo, you need to use the offset reset tool:
        // https://cwiki.apache.org/confluence/display/KAFKA/Kafka+Streams+Application+Reset+Tool
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    public static Properties getOrderProperties(){
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapservers);
        props.put("application.id", "OrderReaderExampleStream1");
        props.put("group.id", "OrderReader");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("enable.auto.commit","true");
        return props;
    }

    public static Properties getPersonProperties(){
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapservers);
        props.put("application.id", "PersonReaderExampleStream1");
        return props;
    }

    public static Properties getPersonJoinProperties(){
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapservers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        //props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serde< Person >.getClass().getName());
        props.put("application.id", "PersonReaderJoin");
        return props;
    }

    public static KafkaConsumer<String,String> getTransactionConsumber(){
        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "transaction-shipping");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapservers);
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

        return new KafkaConsumer<String, String>(props);
    }
}
