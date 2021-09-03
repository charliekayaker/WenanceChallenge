package com.wenance.digitalcurrencies.externalservices;

import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.wenance.digitalcurrencies.constants.Constants;
import com.wenance.digitalcurrencies.utils.Utils;

@Component
public class WSClientCurrenciesImpl {
	
	 private static final Logger log = Logger.getLogger(WSClientCurrencies.class.getName());
	 
	 WSClientCurrencies ws = null;
	 
	 public WSClientCurrenciesImpl() {
		 	String url = Utils.getValueFromProperties(Constants.URL, Constants.ENDPOINT_FILE_PROPERTIES);		
			String contextPath = Utils.getValueFromProperties(Constants.CONTEXT, Constants.ENDPOINT_FILE_PROPERTIES);		
			ws = new WSClientCurrencies(url, contextPath);
	 }

	public Object getBTCPrice() {		
		Object response =  ws.execute(null);		
		return response;
	}

}
