package com.anuj.springboot.productrestapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anuj.springboot.productrestapi.model.Product;
import com.anuj.springboot.productrestapi.repos.ProductRepository;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="Product Rest Endpoints")
//@Hidden used to hide controller class for swagger-UI
//if want to hide method then you have to just add @Hidden annotation
//@Hidden
public class ProductController {

	@Autowired
	ProductRepository productRepository;
	
	//@Hidden
	@GetMapping("/products")
	public List<Product> getProducts(){
		return productRepository.findAll();
	}
	
	@GetMapping("/product/{id}")
	//read only is true when we are caching select query
	//cacheable is accepting the name of cache for which we has created config
	@Transactional(readOnly = true)
	@Cacheable("product-cache")
	@Operation(summary = "Return a product",description = "Takes Id return single product")
	public @ApiResponse(description = "Return a Product") Product getProducts(@Parameter(description = "Id of product") @PathVariable("id") int id){
		return productRepository.findById(id).get();
	}
	
	@RequestMapping(value="/products/",method=RequestMethod.PUT)
	public Product updateProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	//@Valid  annotation is used to check all validation we have defined on Object field.
	@RequestMapping(value="/products/",method=RequestMethod.POST)
	public Product createProduct(@Valid @RequestBody Product product) {
		return productRepository.save(product);
	}

	//CacheEvict is used to delete data from cache
	@CacheEvict("product-cache")
	@RequestMapping(value="/products/{id}",method=RequestMethod.DELETE)
	public void deleteProduct(@PathVariable("id") int id) {
		productRepository.deleteById(id);
	}
	
}
