package com.example.demokafka;

import com.example.demokafka.jpa.model.ProductsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemokafkaApplication {


	public static void main(String[] args) {
		SpringApplication.run(DemokafkaApplication.class, args);

	}

	public EndlessDbWriter endlessDbWriter(ProductsRepository repository){
	    return new EndlessDbWriter(repository);
    }

    @Bean
    public CommandLineRunner demo(ProductsRepository repository) {
        return (args) -> {
            new KafkaEndlessWriter(endlessDbWriter(repository)).startEndlessWriter();
        };
    }

}
