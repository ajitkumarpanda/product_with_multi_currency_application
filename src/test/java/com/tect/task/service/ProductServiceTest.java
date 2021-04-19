package com.tect.task.service;

import com.tect.task.domain.ProductConverter;
import com.tect.task.domain.ProductDto;
import com.tect.task.entity.Product;
import com.tect.task.repository.ProductRepository;
import com.tect.task.util.Currency;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductConverter productConverter;

    @Test
    void saveProduct() {
        Product product = aProduct();
        when(productRepository.save(product)).thenReturn(product);
        when(productConverter.fromProductToProductDto(product)).thenReturn(aProductDto());
        assertEquals(product.getName(), productService.saveProduct(aProductDto()).getName());
    }

     @Test
    void getProducts() {
        when(productRepository.findAll()).thenReturn(aProductList());
         when(productConverter.fromProductListToProductDtoList(aProductList())).thenReturn(aProductDtoList());
        assertEquals(2, productService.getProducts().size());
    }

    @Test
    void getProductById() {
        Product product = aProduct();
        when(productRepository.findById(anyInt())).thenReturn(java.util.Optional.of(product));
        when(productConverter.fromProductToProductDto(product)).thenReturn(aProductDto());
        assertEquals(product.getName(), productService.getProductById(anyInt()).getName());
    }

    @Test
    void deleteProduct() {
        Product product = aProduct();
        when(productConverter.fromProductToProductDto(product)).thenReturn(aProductDto());
        productService.deleteProduct(aProductDto());
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void updateProduct() {

    }

    private Product aProduct() {
        Product newProduct = new Product();
        newProduct.setName("laptop");
        newProduct.setDescription("laptop for personal use");
        newProduct.setPrice(2000);
        return newProduct;
    }

    private List<Product> aProductList(){
        return Stream.of(
                new Product("laptop", "Dell XPX i10", 1800),
                new Product("mobile", "Iphone 12 pro", 999))
                .collect(Collectors.toList());
    }

    private ProductDto aProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setName("laptop");
        productDto.setDescription("laptop for personal use");
        productDto.setCurrencies(priceMap());
        return productDto;
    }

    private Map<String, Double> priceMap() {
        Map<String, Double> currencies = new HashMap<>();
        currencies.put(Currency.GBP.toString(), 100.00);
        currencies.put(Currency.EUR.toString(), 200.00);
        currencies.put(Currency.USD.toString(), 300.00);
        return currencies;
    }

    private List<ProductDto> aProductDtoList(){
        return Stream.of(
                new ProductDto("laptop", "Dell XPX i10", 100, priceMap()),
                new ProductDto("mobile", "Iphone 12 pro", 200, priceMap()))
                .collect(Collectors.toList());
    }

}