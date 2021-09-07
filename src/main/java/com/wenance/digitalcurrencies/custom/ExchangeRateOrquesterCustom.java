package com.wenance.digitalcurrencies.custom;

import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wenance.digitalcurrencies.bo.CurrencieValuePayload;
import com.wenance.digitalcurrencies.bo.RateConvertRequest;
import com.wenance.digitalcurrencies.bo.RateConvertResponse;
import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.model.CurrenciesServiceImpl;

/**
 * Aclaración, en las arquitecturas contemporáneas de microservicios, la orquestación es asincrónica y se hace utilizando colas MQ o
 * en algunos casos más precarios solo threads, en este caso vamos a utilizar el ANTIPATRÓN, es decir invocación de manera síncrona.
 * 
 * Además cuando se trata de microservicios transacionales se utiliza un patrón SAGA que conserva la atomicidad de la orquestación,
 * es decir si uno falla se vuelven todos atrás.
 * */

@Service
public class ExchangeRateOrquesterCustom {
	
	 @Autowired	 
	 private CurrenciesServiceImpl currenciesServiceImpl;
	 
	 @Autowired
	 private CurrencieValuePayload currencieValuePayloadRequest;
	
	 private final String UNDERSCORE = "_";
	 
	public RateConvertResponse execute(RateConvertRequest rateConvertRequest) {
		
		String[] values = getValuesBetween(UNDERSCORE, rateConvertRequest.getAmnt());
		
		// e.g : "2.22_BTC"
		if(values.length>2) { 
			throw new IllegalArgumentException();
		}
		
		currencieValuePayloadRequest.setAmnt(values[0]);
		currencieValuePayloadRequest.setCurrencie(values[1]);
		
		CurrencieValuePayload currencieValuePayloadResponse = currenciesServiceImpl.convertToUSD(currencieValuePayloadRequest);
				
		CotizacionDTO cotizacionDTO = currenciesServiceImpl.findLast(values[1]);
		
		RateConvertResponse rateConvertResponse = new RateConvertResponse();
		
		rateConvertResponse.setAmnt(currencieValuePayloadResponse.getAmnt());
		rateConvertResponse.setCurrencie(cotizacionDTO.getCurr1());
		rateConvertResponse.setRate_in_usd(cotizacionDTO.getLprice());
		
		return rateConvertResponse;
	}
	
	
	private String[] getValuesBetween(String tok, String s) {		
		
	   StringTokenizer st = new StringTokenizer(s, tok);
	   
	   String[] result = new String[st.countTokens()];
	   
	   int n = 0;
	   while(st.hasMoreElements()) {
		   result[n] = (String) st.nextElement();
		   n++;		   
	   }
	   
	   return result;
	}
		
	
}
