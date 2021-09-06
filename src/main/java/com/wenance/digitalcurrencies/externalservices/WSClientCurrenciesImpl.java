package com.wenance.digitalcurrencies.externalservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

//import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import com.wenance.digitalcurrencies.enums.Currencies;
import com.wenance.digitalcurrencies.services.IService;

@Component
public class WSClientCurrenciesImpl {
	
//	 private static final Logger log = Logger.getLogger(WSClientCurrencies.class.getName());
	 
	 @Autowired
	 @Qualifier("WSClientCurrencies")
	 IService ws = null;
	

	public Object getBTCPrice() {		
		Object response =  ws.execute(Currencies.BTC);
		return response;
	}
	
	public Object getETHPrice() {		
		Object response =  ws.execute(Currencies.ETH);		
		return response;
	}

}
