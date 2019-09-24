package com.example.demokafkaproducer;

import com.example.demokafkaproducer.jpa.ProductsRepository;
import com.example.demokafkaproducer.kafkaexamples.EndlessDbWriter3;
import com.example.demokafkaproducer.kafkaexamples.OrderShippingTransaction4;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemokafkaApplication {


	public static void main(String[] args) {
		SpringApplication.run(DemokafkaApplication.class, args);

	}

	public EndlessDbWriter3 endlessDbWriter(ProductsRepository repository){
	    return new EndlessDbWriter3(repository);
    }

    @Bean
    public CommandLineRunner demo(ProductsRepository repository) {
        return (args) -> {
            new KafkaEndlessWriterProcessesAsync(endlessDbWriter(repository)).startEndlessWriter();

        };
    }

}
