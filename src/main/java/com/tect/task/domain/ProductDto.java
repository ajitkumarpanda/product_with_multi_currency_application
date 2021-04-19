package com.tect.task.domain;

import java.util.Map;

public class ProductDto {

    private String name;

    private String description;

    private double price;

    private Map<String, Double> currencies;


    public ProductDto() {
        super();
    }

    public ProductDto(String name, String description, double price, Map<String, Double> currencies) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.currencies = currencies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Map<String, Double> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, Double> currencies) {
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        return "ProductMapper{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", prices=" + currencies +
                '}';
    }
}
