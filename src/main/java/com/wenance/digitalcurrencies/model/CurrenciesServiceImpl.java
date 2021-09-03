package com.wenance.digitalcurrencies.model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wenance.digitalcurrencies.constants.Constants;
import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
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
	public List<CotizacionDTO> findBetweenDates(Date from, Date to) throws NullPointerException  {		
		return currenciesrepository.findByTimestampBetween(new Date(from.getTime() - Constants.TIMEOFFSET), new Date(to.getTime() - Constants.TIMEOFFSET));	
	}
	
	@Override
	public List<CotizacionDTO> findAll(){
		return currenciesrepository.findAll();
	}
	
	


	
	
	
}
