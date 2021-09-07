package com.wenance.digitalcurrencies.externalservices;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


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
		
		if(!(request instanceof CurrencieValuePayload))
			throw new IllegalArgumentException("Objeto de negocio no válido para esta operación");
		
		Currencies currencie = Currencies.valueOf(((CurrencieValuePayload) request).getCurrencie()); //FALTA EL CURRENCY, VAMOS A TENER QUE DIVIDIR EL PAYLOAD O HACER ALGO PARA QUE RECIBA LO 
		WebTarget client = createClient(Utils.getValueFromProperties(Constants.CONVERT, Constants.ENDPOINT_FILE_PROPERTIES), currencie);
		
		
		Response response = client.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(request, MediaType.APPLICATION_JSON));
		
		
		return response.readEntity(CurrencieValuePayload.class);
	}

}
