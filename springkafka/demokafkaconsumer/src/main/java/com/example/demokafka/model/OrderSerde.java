package com.example.demokafka.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import com.example.demokafka.serializer.JsonPOJODeserializer;
import com.example.demokafka.serializer.JsonPOJOSerializer;

public class OrderSerde {

    public static Serializer<Order> getOrderSerializer() {
        Serializer<Order> serializer = new JsonPOJOSerializer<>();
        Map<String, Object> serdeProps = new HashMap<>();
        serdeProps.put("JsonPOJOClass", Order.class);
        serializer.configure(serdeProps, false);
        return serializer;

    }

    public static Deserializer<Order> getOrderDeserializer() {
        final Deserializer<Order> deserializer = new JsonPOJODeserializer<>();
        Map<String, Object> serdeProps = new HashMap<>();
        serdeProps.put("JsonPOJOClass", Order.class);
        deserializer.configure(serdeProps, false);
        return deserializer;
    }

    public static Serde<Order> getOrderSerde() {
        return Serdes.serdeFrom(getOrderSerializer(), getOrderDeserializer());
    }

}
