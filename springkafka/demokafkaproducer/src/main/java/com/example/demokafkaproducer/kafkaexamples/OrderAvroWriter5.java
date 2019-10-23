package com.example.demokafkaproducer.kafkaexamples;

import com.example.demokafkaproducer.model.OrderAvro;
import com.example.demokafkaproducer.model.OrderNotWorkingAvro;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import static com.example.demokafkaproducer.KafkaConfigurations.createAvroProductProducer;
import static com.example.demokafkaproducer.KafkaConfigurations.createNotWorkingProductProducer;
import static com.example.demokafkaproducer.KafkaConfigurations.orderAvroTopic;

/**
 * See Tutorial at
 * for producer https://dzone.com/articles/kafka-avro-serialization-and-the-schema-registry
 * for streams https://aseigneurin.github.io/2018/08/06/kafka-tutorial-7-kafka-streams-serdes-and-avro.html
 * Gradle Plugin Generation http://cloudurable.com/blog/avro/index.html
 *
 * Please the sChema tab in confluent gui
 * topic -> sChema
 * http://192.168.99.100:9021/clusters/Ze_NOufZQE-YVgrudC7eTw/management/topics/order-avro-topic/schema/value
 *
 * {
 *   "name": "OrderAvro",
 *   "type": "record",
 *   "namespace": "de.inmediasp.de.soft.demo.kafka",
 *   "fields": [
 *      {"name": "id", "type": "int"},
 *      {"name": "amount", "type": "double"},
 *      {"name": "personId", "type": "int"},
 *      {"name": "car", "type": "string"}
 *   ]
 * }
 */
public class OrderAvroWriter5 {



    public void endlessOrderWriter(){
        workingExample();
        //notWorkingExampleBecauseOfWrongSchema();
    }

    private void workingExample() {
        final Producer<Integer, OrderAvro> producer = createAvroProductProducer();
        long time = System.currentTimeMillis();
        Long sendMessageCount = 0L;

        try {
            while(true)  {
                sendMessageCount++;
                OrderAvro o = OrderAvro.newBuilder()
                        .setCar("Trabbi Avor")
                        .setId(new Double(Math.random()*1000).intValue())
                        .setAmount(Math.random()*1000)
                        .setPersonId(new Double(Math.random()*10).intValue())
                        .build();

                final ProducerRecord<Integer, OrderAvro> record = new ProducerRecord<Integer, OrderAvro>(orderAvroTopic, o.getId(), o);
                producer.send(record);
                System.out.println("New Avro Message "+record.value().toString());
                Thread.sleep(4000);

            }
        } catch (Exception ex){
            ex.printStackTrace();
        }finally {
            producer.flush();
            producer.close();
        }
    }

    /**
     * Be carefully this is not working because the shema is not known to the registry
     * There is no alignment with Kafka if the registry is aligned?
     * If you would register this schema to the registry than this would work
     *
     */
    private void notWorkingExampleBecauseOfWrongSchema() {
        final Producer<Integer, OrderNotWorkingAvro> producer = createNotWorkingProductProducer();
        long time = System.currentTimeMillis();
        Long sendMessageCount = 0L;

        try {
            while(true)  {
                sendMessageCount++;
                OrderNotWorkingAvro o = OrderNotWorkingAvro.newBuilder()
                        .setId(222)
                        .setName("Test")
                        .build();

                final ProducerRecord<Integer, OrderNotWorkingAvro> record = new ProducerRecord<Integer, OrderNotWorkingAvro>(orderAvroTopic, o.getId(), o);
                producer.send(record);
                System.out.println("New Avro Message "+record.value().toString());
                Thread.sleep(4000);

            }
        } catch (Exception ex){
            ex.printStackTrace();
        }finally {
            producer.flush();
            producer.close();
        }
    }

}
