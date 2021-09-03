package com.wenance.digitalcurrencies.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.externalservices.WSClientCurrenciesImpl;
import com.wenance.digitalcurrencies.model.CurrenciesServiceImpl;
import com.wenance.digitalcurrencies.utils.Utils;

@Service
public class JobManager {
	
	@Autowired
	WSClientCurrenciesImpl wsClientCurrenciesImpl;
	@Autowired
	CurrenciesServiceImpl currenciesServiceImpl;
	
	/**
	 * En este método primero llamamos a la función obtener precio de la moneda digital y luego le añadimos manualmente el timestamp por no traerlo desde el Servicio.
	 * @author charlie
	 * */
	@Scheduled(fixedRateString = "${saveCurrencies.job}")
	public void saveCurrentPrices() {
		CotizacionDTO dto = (CotizacionDTO) Utils.addCustomTimeStamp(wsClientCurrenciesImpl.getBTCPrice());		
		currenciesServiceImpl.recordPrice(dto);
	}


}
