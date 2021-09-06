package com.wenance.digitalcurrencies.externalservices;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wenance.digitalcurrencies.bo.EnqCurrVal_req;
import com.wenance.digitalcurrencies.bo.CurrencieValuePayload;
import com.wenance.digitalcurrencies.constants.Constants;
import com.wenance.digitalcurrencies.enums.Currencies;
import com.wenance.digitalcurrencies.services.AbstractClient;
import com.wenance.digitalcurrencies.utils.Utils;

@Service
public class WSPricesInUSD extends AbstractClient{

	protected WSPricesInUSD(@Value("${endpoints.url1}") String url, @Value("${endpoints.context1}") String contextPath) {
		super(url, contextPath); 
	}

	@Override
	public Object execute(Object request) {
		
		if(!(request instanceof EnqCurrVal_req))
			throw new IllegalArgumentException("Objeto de negocio no válido para esta operación");
		
		Currencies currencie = ((EnqCurrVal_req) request).getCurrencie();
		
		WebTarget client = createClient(Utils.getValueFromProperties(Constants.CONVERT, Constants.ENDPOINT_FILE_PROPERTIES), currencie);
		
		CurrencieValuePayload currencieValuePayload = new CurrencieValuePayload();
		currencieValuePayload.setAmnt(((EnqCurrVal_req) request).getValue());
		
		Response response = client.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(currencieValuePayload, MediaType.APPLICATION_JSON));
		
		
		return response.readEntity(CurrencieValuePayload.class);
	}

}
