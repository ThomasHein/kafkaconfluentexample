package com.example.demokafkaproducer.jpa;

import com.example.demokafkaproducer.jpa.model.Products;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Products,Integer> {
    List<Products> findByName(String name);

}
