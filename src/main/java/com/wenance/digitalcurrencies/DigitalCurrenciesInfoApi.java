package com.wenance.digitalcurrencies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class DigitalCurrenciesInfoApi {

	public static void main(String[] args) {
		SpringApplication.run(DigitalCurrenciesInfoApi.class, args);		
		
	}	

}
