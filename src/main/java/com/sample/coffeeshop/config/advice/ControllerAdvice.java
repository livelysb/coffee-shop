package com.sample.coffeeshop.config.advice;

import com.sample.coffeeshop.common.CoffeeShopBadRequestException;
import com.sample.coffeeshop.common.CoffeeShopErrors;
import com.sample.coffeeshop.common.CoffeeShopException;
import com.sample.coffeeshop.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sample.coffeeshop.common.CoffeeShopErrors.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse error(Exception e) {
        log.error("[!] 예외발생 - {}", e.getMessage(), e);
        return new ErrorResponse(INTERNAL_SERVER_ERROR.name());
    }

    @ExceptionHandler(CoffeeShopException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse coffeeShopError(CoffeeShopException e) {
        log.error("[!] 예외발생 - {}: {}", e.getError().name(), e.getError().getErrorMsg(), e);
        return new ErrorResponse(e.getError().name());
    }

    @ExceptionHandler(CoffeeShopBadRequestException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse coffeeShopBadRequestError(CoffeeShopBadRequestException e) {
        log.warn("[!] 예외발생 - {}: {}", e.getError().name(), e.getError().getErrorMsg(), e);
        return new ErrorResponse(e.getError().name());
    }
}
