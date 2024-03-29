package com.example.demokafka.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import com.example.demokafka.serializer.JsonPOJODeserializer;
import com.example.demokafka.serializer.JsonPOJOSerializer;

public class PersonSerde {

    public static Serializer<Person> getPersonSerializer() {
        Serializer<Person> serializer = new JsonPOJOSerializer<>();
        Map<String, Object> serdeProps = new HashMap<>();
        serdeProps.put("JsonPOJOClass", Person.class);
        serializer.configure(serdeProps, false);
        return serializer;

    }

    public static Deserializer<Person> getPersonDeserializer() {
        final Deserializer<Person> deserializer = new JsonPOJODeserializer<>();
        Map<String, Object> serdeProps = new HashMap<>();
        serdeProps.put("JsonPOJOClass", Person.class);
        deserializer.configure(serdeProps, false);
        return deserializer;
    }

    public static Serde<Person> getPersonSerde() {
        return Serdes.serdeFrom(getPersonSerializer(), getPersonDeserializer());
    }

}
