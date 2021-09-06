package com.wenance.digitalcurrencies.externalservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wenance.digitalcurrencies.services.IService;

@Component
public class WSPricesInUSDImpl {
	
	 @Autowired
	 @Qualifier("WSPricesInUSD")
	 IService ws;
	
	public Object convertCurrencietoUSD(Object request) {
		return ws.execute(request);
	}
	
}
