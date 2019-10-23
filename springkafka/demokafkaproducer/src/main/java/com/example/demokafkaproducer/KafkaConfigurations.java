package com.example.demokafkaproducer;

import com.example.demokafkaproducer.model.OrderAvro;
import com.example.demokafkaproducer.model.OrderNotWorkingAvro;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaConfigurations {

    public final static String orderAvroTopic = "order-avro-topic";

    public final static String BOOTSTRAP_SERVERS =
            "localhost:39092";

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

    public static Producer<String, String> createShippingProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put("acks", "all");

        props.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG,"300");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaShipping");
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put("transactional.id","ShippingProducerId");
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

    public static Producer<Integer, OrderAvro> createAvroProductProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put("acks", "all");

        props.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG,"10000");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleAvroProductProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(AbstractKafkaAvroSerDeConfig.AUTO_REGISTER_SCHEMAS, false);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        return new KafkaProducer<>(props);
    }

    public static Producer<Integer, OrderNotWorkingAvro> createNotWorkingProductProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put("acks", "all");

        props.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG,"10000");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleAvroProductProducer");
        props.put(AbstractKafkaAvroSerDeConfig.AUTO_REGISTER_SCHEMAS, false);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        return new KafkaProducer<>(props);
    }
}
