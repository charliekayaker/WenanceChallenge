package com.wenance.digitalcurrencies.model;


import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wenance.digitalcurrencies.bo.CurrencieValuePayload;
import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.dtos.StatisticsDTO;
import com.wenance.digitalcurrencies.enums.Currencies;

/**
 * Acá utilizamos la etiqueta superior @Service para hacer programación 
 * con DDD, Drive Domain Design donde exponemos nuestras reglas de negocio 
 * y funciones principales en una interface que deberá ser implementada
 *  
 * */
@Service
public interface CurrenciesService {
	
	//a)
	public void recordPrice(CotizacionDTO data);
	//b) Mirar repository.
	public List<CotizacionDTO> findBetweenDates(Date from, Date to);
	
	public List<CotizacionDTO> findAll();
	
	public CotizacionDTO findPriceByTimestamp(MongoDBDate date, String currencie);
	
	public StatisticsDTO findStatisticsBetween(Date from, Date to, Currencies currencie);
	
	public CotizacionDTO findLast(String currencie);
	
	public CurrencieValuePayload convertToUSD(CurrencieValuePayload request);
	
	public List<CotizacionDTO> getPaginatedInfo(int page, String from, String to, String currencie);
	
}