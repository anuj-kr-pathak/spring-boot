package com.anuj.springboot.productrestapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anuj.springboot.productrestapi.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
