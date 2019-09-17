package com.example.demokafka;

import com.example.demokafka.jpa.model.Products;
import com.example.demokafka.jpa.model.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EndlessDbWriter {

    private ProductsRepository productsRepository;

    @Autowired
    public EndlessDbWriter(ProductsRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    public void write() {
        try{
            while(true){

                System.out.println("Sending new user to DB");
                Products pr = productsRepository.save(new Products("Chip "+new Date().toString(),4.4));
                System.out.println(pr);
                Thread.sleep(4000);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }
}
