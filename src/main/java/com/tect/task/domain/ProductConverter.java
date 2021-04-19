package com.tect.task.domain;

import com.tect.task.entity.Product;
import com.tect.task.util.Currency;
import com.tect.task.util.CurrencyExchangeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductConverter {

    @Autowired
    private CurrencyExchangeUtil currencyExchangeUtil;

    public Product fromProductDtoToProduct(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        return product;
    }

    public ProductDto fromProductToProductDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCurrencies(currencyMap(product));
        return dto;
    }

    public List<ProductDto> fromProductListToProductDtoList(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            ProductDto dto = new ProductDto();
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setDescription(product.getDescription());
            dto.setCurrencies(currencyMap(product));
            productDtoList.add(dto);
        }
        return productDtoList;
    }

    public Map<String, Double> currencyMap(Product product) {
        Map<String, Double> currencies = new HashMap<>();
        currencies.put(Currency.GBP.toString(), getConvert(product, Currency.GBP.toString()));
        currencies.put(Currency.EUR.toString(), getConvert(product, Currency.EUR.toString()));
        currencies.put(Currency.USD.toString(), getConvert(product, Currency.USD.toString()));
        return currencies;
    }

    private double getConvert(Product product, String currency) {
        double price = currencyExchangeUtil.convert(currency, product.getPrice());
        price = (double) Math.round(price * 100) / 100;
        return price;
    }
}
