package com.wenance.digitalcurrencies.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.dtos.PricesDetailsDTO;

public interface CurrenciesRespository extends MongoRepository<CotizacionDTO, String> { 
		//b)
		@Query("{'date' : {$gte: ?0, $lte:?1 }}")	
		public List<PricesDetailsDTO> getPricesDetailsFromPeriod(Date from, Date to) throws NullPointerException;
}
