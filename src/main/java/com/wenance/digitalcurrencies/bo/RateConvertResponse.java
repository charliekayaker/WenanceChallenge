package com.wenance.digitalcurrencies.bo;

import lombok.Data;

public @Data class RateConvertResponse {
	
	private String amnt;
	private String currencie;
	private String rate_in_usd;

}
