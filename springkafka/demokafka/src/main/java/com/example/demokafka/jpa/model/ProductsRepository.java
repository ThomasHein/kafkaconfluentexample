package com.example.demokafka.jpa.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Products,Integer> {
    List<Products> findByName(String name);

}
