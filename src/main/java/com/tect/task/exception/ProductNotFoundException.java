package com.tect.task.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String exception) {
        super(exception);
    }
}