package com.wenance.digitalcurrencies.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class CotizacionDTO {
	
	@JsonProperty("lprice")
	private String lprice;//price;
	
	@JsonProperty("curr1")
	private String curr1;//currency;
	
	@JsonProperty("curr2")
	private String curr2;//currencyRef;
	
	private Date timestamp;
	
	public Double priceAsDouble() {		
		return Double.parseDouble(lprice);
	}

}
