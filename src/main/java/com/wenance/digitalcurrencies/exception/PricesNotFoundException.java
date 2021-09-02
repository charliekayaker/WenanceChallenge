package com.wenance.digitalcurrencies.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Prices not found")
public class PricesNotFoundException extends RuntimeException {
}
