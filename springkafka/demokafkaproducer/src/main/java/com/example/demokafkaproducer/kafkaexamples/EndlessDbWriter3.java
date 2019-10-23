package com.example.demokafkaproducer.kafkaexamples;

import java.util.Date;

import com.example.demokafkaproducer.jpa.ProductsRepository;
import com.example.demokafkaproducer.jpa.model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EndlessDbWriter3 {

    private ProductsRepository productsRepository;

    @Autowired
    public EndlessDbWriter3(ProductsRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    public void write() {
        try{
            while(true){
                System.out.println("Sending new user to DB");
                Products pr =  productsRepository.save(new Products("Chip "+new Date().toString(),4.4));
                System.out.println(pr);
                Thread.sleep(4000);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }
}
