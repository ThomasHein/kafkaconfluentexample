package com.example.demokafka;

import com.example.demokafka.kafkaexamples.MaterializedViewPerson2;
import com.example.demokafka.kafkaexamples.ShippingReaderTransaction4;
import com.example.demokafka.model.Person;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemokafkaApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DemokafkaApplication.class, args);


		System.out.println("Starting application");
		new ShippingReaderTransaction4().ShippingReaderFromTransaction();
		//PeronTableReading();
		return;


	}

	private static void PeronTableReading() throws InterruptedException {
		KafkaStreams streams = new MaterializedViewPerson2().getTable();

		System.out.println("Started");
		Thread.sleep(5000);
		ReadOnlyKeyValueStore<String, Person> keyValueStore =
				streams.store("personsStore", QueryableStoreTypes.keyValueStore());

		int counter = 0;
		while(true){

			System.out.println("****\n*****\n");
			KeyValueIterator<String,Person> iterator = keyValueStore.all();
			counter = 0;
			while (iterator.hasNext()){
				KeyValue<String,Person> k = iterator.next();
				Person p = k.value;
				System.out.println("count for hello:" + p.getFirstname());
				counter++;
			}

			System.out.println("count: "+counter);
			Thread.sleep(100000);

		}
	}

}
