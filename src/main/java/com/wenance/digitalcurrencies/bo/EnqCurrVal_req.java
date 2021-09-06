package com.wenance.digitalcurrencies.bo;

import com.wenance.digitalcurrencies.enums.Currencies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class EnqCurrVal_req {
	
	private Currencies currencie;
	private String value;

}
