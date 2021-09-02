package com.wenance.digitalcurrencies.model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.dtos.PricesDetailsDTO;
import com.wenance.digitalcurrencies.repository.CurrenciesRespository;


/**
 * Drive Domain Desing - implementación
 * */
@Service
public class CurrenciesServiceImpl implements CurrenciesService {
	
	@Autowired
	private CurrenciesRespository currenciesrepository;

	@Override
	public void recordPrice(CotizacionDTO data) {
		
		currenciesrepository.insert(data);
	}

	@Override
	public List<PricesDetailsDTO> findBetweenDates(Date from, Date to) throws NullPointerException  {		
		return currenciesrepository.getPricesDetailsFromPeriod(from, to);	
	}
	
	


	
	
	
}
