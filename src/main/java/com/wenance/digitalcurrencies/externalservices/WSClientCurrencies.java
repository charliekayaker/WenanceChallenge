package com.wenance.digitalcurrencies.externalservices;


import javax.ws.rs.Produces;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wenance.digitalcurrencies.constants.Constants;
import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.services.AbstractClient;
import com.wenance.digitalcurrencies.utils.Utils;


public class WSClientCurrencies extends AbstractClient {

	protected WSClientCurrencies(String url, String contextPath) {
		super(url, contextPath);		
	}

	@Override	
	@Produces("application/json")
	public Object execute(Object request) {
				
		WebTarget client = createClient(Utils.getValueFromProperties(Constants.LAST_PRICE, Constants.ENDPOINT_FILE_PROPERTIES), request);
		
		Response response = client.request(MediaType.APPLICATION_JSON).get();
		
		return response.readEntity(CotizacionDTO.class);
	}
	
	

}
