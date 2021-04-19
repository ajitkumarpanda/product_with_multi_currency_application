package com.tect.task.controller;

import com.tect.task.domain.ProductDto;
import com.tect.task.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @PostMapping(value = "/addProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto addProduct(@RequestBody ProductDto product) {
        logger.info("ADD_PRODUCT, product{} requested ", product);
        return service.saveProduct(product);
    }

    @GetMapping(value = "/allProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDto> findAllProducts() {
        logger.info("ALL_PRODUCT, all products requested ");
        return service.getProducts();
    }

    @GetMapping(value = "/productById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto findProductById(@PathVariable int id) {
        logger.info("FIND_PRODUCT, productId{} requested ", id);
        return service.getProductById(id);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto updateProduct(@RequestBody ProductDto product) {
        logger.info("UPDATE_PRODUCT, product{} requested to update ", product);
        return service.updateProduct(product);
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteProduct(@RequestBody ProductDto product) {
        logger.info("DELETE_PRODUCT, product{} requested to delete ", product);
        return service.deleteProduct(product);
    }
}
