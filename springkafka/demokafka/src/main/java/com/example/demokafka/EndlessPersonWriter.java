package com.example.demokafka;

import com.example.demokafka.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import static com.example.demokafka.KafkaEndlessWriter.createPPersonroducer;


public class EndlessPersonWriter {

    private final static String TOPIC = "streams-person-input";

    public void endlessPersonWriter(){
        final Producer<String, String> producer = createPPersonroducer();
        long time = System.currentTimeMillis();
        Long sendMessageCount = 0L;

        try {
            while(true)  {
                sendMessageCount++;
                Person p = new Person();
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                final ProducerRecord<String, String> record =
                        new ProducerRecord<>(TOPIC, p.getGuid().toString(),ow.writeValueAsString(p));

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
