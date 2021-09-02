package com.wenance.digitalcurrencies.model;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;
import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.dtos.PricesDetailsDTO;

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
	public List<PricesDetailsDTO> findBetweenDates(Timestamp from, Timestamp to);

}
