package com.wenance.digitalcurrencies.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.externalservices.WSClientCurrenciesImpl;
import com.wenance.digitalcurrencies.model.CurrenciesServiceImpl;

@Service
public class JobManager {
	
	WSClientCurrenciesImpl ws;
	
	public JobManager() {		
		ws = new WSClientCurrenciesImpl();
	}
	
	@Scheduled(fixedRateString = "${saveCurrencies.job}")
	public void saveCurrentPrices() {
		System.out.println("TEST");
		new CurrenciesServiceImpl().recordPrice((CotizacionDTO) ws.getBTCPrice());
	}


}
