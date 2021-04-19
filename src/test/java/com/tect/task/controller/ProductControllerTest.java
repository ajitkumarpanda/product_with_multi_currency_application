package com.tect.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tect.task.domain.ProductDto;
import com.tect.task.entity.Product;
import com.tect.task.service.ProductService;
import com.tect.task.util.Currency;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ObjectMapper mapper = new ObjectMapper();


    @Test
    void addProduct() throws Exception {
     when(productService.saveProduct(any())).thenReturn(aProductDto());

     mockMvc.perform(post("/products/addProduct")
                .content(mapper.writeValueAsString(aProduct()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(aProductDto())));
    }

    @Test
    void findAllProducts() throws Exception {
        when(productService.getProducts()).thenReturn(Collections.singletonList(aProductDto()));
        mockMvc.perform(get("/products/allProducts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(Collections.singletonList(aProductDto()))))
                .andExpect(status().isOk());
    }

    @Test
    void findProductById() throws Exception{
        when(productService.getProductById(anyInt())).thenReturn(aProductDto());
        mockMvc.perform(get("/products/productById/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(aProductDto())))
                .andExpect(status().isOk());
    }

    @Test
    void updateProduct() throws Exception {
        when(productService.updateProduct(aProductDto())).thenReturn(aProductDto());

        mockMvc.perform(put("/products/update")
                .content(mapper.writeValueAsString(aProductDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(mapper.writeValueAsString(aProductDto())));
    }

    @Test
    void deleteProduct() throws Exception{
        when(productService.deleteProduct(any())).thenReturn("SUCCESS");
        mockMvc.perform(delete("/products/delete")
                .content(mapper.writeValueAsString(aProduct()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    private Product aProduct() {
        Product newProduct = new Product();
        newProduct.setName("laptop");
        newProduct.setDescription("laptop for personal use");
        newProduct.setPrice(2000);
        return newProduct;
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
}