package com.anuj.springboot.productrestapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.anuj.springboot.productrestapi.model.Product;

@SpringBootTest
class ProductrestapiApplicationTests {

	@Value("${productrestapi.services.url}")
	private String baseURL;
	
	@Test
	void testGetProduct() {
		RestTemplate restTemplate = new RestTemplate();
		Product product = restTemplate.getForObject(baseURL+"product/3",Product.class);
		assertNotNull(product);
	}
	
	@Test
	void testcreateProduct() {
		RestTemplate restTemplate = new RestTemplate();
		Product product = new Product();
		product.setName("Test Create");
		product.setDescription("Tesing creating functionality");
		product.setPrice(100);
		Product responseProduct = 	restTemplate.postForObject(baseURL+"products/",product,Product.class);
		assertNotNull(product);
	}
}
