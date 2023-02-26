package com.sample.coffeeshop.common;

public class CoffeeShopBadRequestException extends CoffeeShopException {
    public CoffeeShopBadRequestException(CoffeeShopErrors error) {
        super(error);
    }
}
