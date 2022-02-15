package com.anuj.springboot.productrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


/*@EnableCaching annotation is used to inform spring container about cache*/
//@OpenAPIDefinition annotation is used for swagger api

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title="Product API",version="1.0",description="These API for products"))
public class ProductrestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductrestapiApplication.class, args);
	}

}
