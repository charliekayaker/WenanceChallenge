package com.wenance.digitalcurrencies.bo;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
public @Data class CurrencieValuePayload {
	
	private	String amnt;
	private String currencie;

}
