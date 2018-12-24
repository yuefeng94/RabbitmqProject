package com.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.entity.Product;

public interface ProductMongoRepository extends MongoRepository<Product, String> {

}
