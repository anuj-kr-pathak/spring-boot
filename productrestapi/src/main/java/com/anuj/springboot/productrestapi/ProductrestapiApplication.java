package com.anuj.springboot.productrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


/*@EnableCaching annotation is used to inform spring container about cache*/

@SpringBootApplication
@EnableCaching
public class ProductrestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductrestapiApplication.class, args);
	}

}
