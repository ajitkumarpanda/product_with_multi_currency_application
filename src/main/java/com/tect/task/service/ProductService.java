package com.tect.task.service;

import com.tect.task.exception.ProductNotFoundException;
import com.tect.task.domain.ProductConverter;
import com.tect.task.domain.ProductDto;
import com.tect.task.entity.Product;
import com.tect.task.repository.ProductRepository;
import com.tect.task.util.CurrencyExchangeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CurrencyExchangeUtil currencyExchangeUtil;

    @Autowired
    private ProductConverter productConverter;

    public ProductDto saveProduct(ProductDto productDto) {

        Product product = productConverter.fromProductDtoToProduct(productDto);

        product = repository.save(product);

        logger.info("ADD_PRODUCT, product{} added to database ", product);

        return productConverter.fromProductToProductDto(product);
    }

    public List<ProductDto> getProducts() {
        List<Product> productList = repository.findAll();
        logger.info("ALL_PRODUCTS, all products{} from database ", productList);
        return productConverter.fromProductListToProductDtoList(productList);
    }

    public ProductDto getProductById(int id) {
        Optional<Product> product = repository.findById(id);

        if (!product.isPresent())
            throw new ProductNotFoundException("Product not present for Id - " + id);
        logger.info("FIND_PRODUCT, product{} from database ", product);

        return productConverter.fromProductToProductDto(product.get());
    }

    public String deleteProduct(ProductDto productDto) {
        Product product = productConverter.fromProductDtoToProduct(productDto);
        repository.delete(product);
        logger.info("DELETE_PRODUCT, product{} deleted from database ", product);

        return "Removed product !!";
    }

    public ProductDto updateProduct(ProductDto productDto) {
        Product product = productConverter.fromProductDtoToProduct(productDto);
        Product existingProduct = repository.findById(product.getId()).orElse(null);

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        product = repository.save(existingProduct);
        logger.info("UPDATE_PRODUCT, product{} updated in database ", product);

        return productConverter.fromProductToProductDto(product);
    }

}