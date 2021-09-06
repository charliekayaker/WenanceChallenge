package com.wenance.digitalcurrencies.jobs;

import java.util.Date;

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
	private WSClientCurrenciesImpl wsClientCurrenciesImpl;
	@Autowired
	private CurrenciesServiceImpl currenciesServiceImpl;
	
	/**
	 * En este método primero llamamos a la función obtener precio de la moneda digital y luego le añadimos manualmente el timestamp por no traerlo desde el Servicio.
	 * @author charlie
	 * */
	@Scheduled(fixedRateString = "${saveCurrencies.job1}")
	public void saveCurrentPrices() {
	   this.saveBTCPrice();
	   this.saveETHPrice();
	   System.out.println(new Date() + " EJECUTADO ");
	}
	
	
	//@Scheduled(fixedRateString = "${saveCurrencies.job1}")
	private void saveBTCPrice() {
		CotizacionDTO dto = (CotizacionDTO) Utils.addCustomTimeStamp(wsClientCurrenciesImpl.getBTCPrice());		
		currenciesServiceImpl.recordPrice(dto);
	}
	
	//@Scheduled(fixedRateString = "${saveCurrencies.job2}")
	private void saveETHPrice() {
		CotizacionDTO dto = (CotizacionDTO) Utils.addCustomTimeStamp(wsClientCurrenciesImpl.getETHPrice());		
		currenciesServiceImpl.recordPrice(dto);
		
	}


}
