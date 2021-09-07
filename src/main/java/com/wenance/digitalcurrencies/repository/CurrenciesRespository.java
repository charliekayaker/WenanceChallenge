package com.wenance.digitalcurrencies.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.model.MongoDBDate;

public interface CurrenciesRespository extends MongoRepository<CotizacionDTO, String> { 
		//b)		
		public List<CotizacionDTO> findByTimestampBetween(Date from, Date to) throws NullPointerException;
							 
		public CotizacionDTO timestampBetweenAndCurr1(MongoDBDate from, MongoDBDate to, String currencie);
		
}
	