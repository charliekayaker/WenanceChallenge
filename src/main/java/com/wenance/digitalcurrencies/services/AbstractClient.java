package com.wenance.digitalcurrencies.services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import com.wenance.digitalcurrencies.constants.Constants;
import com.wenance.digitalcurrencies.enums.Currencies;
 


public abstract class AbstractClient implements IService {
	
	 protected final String endpoint;	 
	
	
	protected AbstractClient(String url, String contextPath) {
		this.endpoint = url.concat(contextPath);
	}
	
	
    protected WebTarget createClient(String path, Object e) {       
    	
    	if(!(e instanceof Currencies)) {
    		throw new IllegalArgumentException();
    	} //Acá podriamos validar a futuro los diferentes objetos que va aceptar este método, hoy solo acepta Currencies pero lo dejemos como Object para que sea genérico y reutilizable
    	
    	String assembledPath = assembleEndpoint(path, (Currencies) e);
        Client client = ClientBuilder.newClient();        
        WebTarget target = client.target(assembledPath);
        return target;
        
    }
	
	private String assembleEndpoint(String path, Currencies e) {        
        
        return endpoint.concat(path).concat(Constants.SLASH + e.getSymbol()).concat(Constants.SLASH + Currencies.USD.getSymbol());
    }
}
