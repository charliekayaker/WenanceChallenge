package com.wenance.digitalcurrencies.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.dtos.PricesDetailsDTO;

public interface CurrenciesRespository extends MongoRepository<CotizacionDTO, String> { //VOY A TENER QUE HACER UNA CLASE PRICE QUE SEA MODELO DE LA COTIZACION Y ALGUN ID
		//b)
		@Query("{'date' : {$gte: ?0, $lte:?1 }}")	
		public List<PricesDetailsDTO> getPricesDetailsFromPeriod(Timestamp from, Timestamp to);
}
