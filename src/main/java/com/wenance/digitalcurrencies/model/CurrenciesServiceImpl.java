package com.wenance.digitalcurrencies.model;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
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
	public List<PricesDetailsDTO> findBetweenDates(Timestamp from, Timestamp to) {		
		return currenciesrepository.getPricesDetailsFromPeriod(from, to);	
	}



	
	
	
}
