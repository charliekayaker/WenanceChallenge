package com.wenance.digitalcurrencies.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.externalservices.WSClientCurrenciesImpl;
import com.wenance.digitalcurrencies.model.CurrenciesServiceImpl;

@Service
public class JobManager {
	
	@Autowired
	WSClientCurrenciesImpl wsClientCurrenciesImpl;
	@Autowired
	CurrenciesServiceImpl currenciesServiceImpl;
	
		
	@Scheduled(fixedRateString = "${saveCurrencies.job}")
	public void saveCurrentPrices() {		
		CotizacionDTO cotizacionDTO = (CotizacionDTO) wsClientCurrenciesImpl.getBTCPrice();
		System.out.println(cotizacionDTO.getLprice());
		currenciesServiceImpl.recordPrice(cotizacionDTO);
	}


}
