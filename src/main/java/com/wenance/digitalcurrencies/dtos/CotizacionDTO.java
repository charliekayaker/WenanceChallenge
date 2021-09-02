package com.wenance.digitalcurrencies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

public @Data class CotizacionDTO {
	
	@JsonProperty("lprice")
	private String lprice;//price;
	
	@JsonProperty("curr1")
	private String curr1;//currency;
	
	@JsonProperty("curr2")
	private String curr2;//currencyRef;
	

}
