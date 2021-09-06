package com.wenance.digitalcurrencies.services;

import org.springframework.stereotype.Component;

@Component
public interface IService {
	
	public Object execute(Object request);

}
