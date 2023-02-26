package com.sample.coffeeshop.common;

import lombok.Getter;


@Getter
public class CoffeeShopException extends RuntimeException {
    private final CoffeeShopErrors error;

    public CoffeeShopException(CoffeeShopErrors error) {
        this.error = error;
    }
}
