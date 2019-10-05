package com.example.demokafkaproducer.kafkaexamples;

import com.example.demokafkaproducer.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import static com.example.demokafkaproducer.KafkaConfigurations.createOrderProducer;



public class EndlessOrderWriter1 {

    private final static String TOPIC = "streams-order-input";

    public void endlessOrderWriter(){
        final Producer<String, String> producer = createOrderProducer();
        long time = System.currentTimeMillis();
        Long sendMessageCount = 0L;

        try {
            while(true)  {
                sendMessageCount++;
                Order o = new Order();
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                final ProducerRecord<String, String> record =
                        new ProducerRecord<>(TOPIC, o.getId().toString(),ow.writeValueAsString(o));

                RecordMetadata metadata = producer.send(record).get();
                System.out.println(metadata.topic()+" "+metadata.offset());

                long elapsedTime = System.currentTimeMillis() - time;
                Thread.sleep(1000);

            }
        } catch (Exception ex){
            ex.printStackTrace();
        }finally {
            producer.flush();
            producer.close();
        }
    }

}
