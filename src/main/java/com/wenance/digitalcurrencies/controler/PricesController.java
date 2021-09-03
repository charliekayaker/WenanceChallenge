package com.wenance.digitalcurrencies.controler;

import java.text.ParseException;
import java.util.List;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.dtos.PricesDetailsDTO;
import com.wenance.digitalcurrencies.enums.Currencies;
import com.wenance.digitalcurrencies.exception.PricesNotFoundException;
import com.wenance.digitalcurrencies.externalservices.WSClientCurrenciesImpl;
import com.wenance.digitalcurrencies.model.CurrenciesServiceImpl;
import com.wenance.digitalcurrencies.utils.Utils;

import io.swagger.annotations.ApiOperation;

/**
 * *Podríamos hacer la conversión en un controlador separado a la consulta de precios, pero para acelerar un poco el proceso
 * vamos a hacerlo en este mismo controller.
 * @author charlie
 *
 */

@RestController
@Singleton
@RequestMapping("api")  //# http://localhost:9800/api/
public class PricesController {
	
	@Autowired
	WSClientCurrenciesImpl ws;
	@Autowired
	CurrenciesServiceImpl currenciesServiceImpl;
	
	
	@ApiOperation(value = "getPrices", response = CotizacionDTO.class, produces ="application/json")
	@GetMapping("/getPrices")
	public ResponseEntity<List<CotizacionDTO>> getPrices() {		
			
		
		return ResponseEntity.ok(currenciesServiceImpl.findAll());
	}
	

	@ApiOperation(value = "getPricesBetween", response = CotizacionDTO.class, produces ="application/json")
	@GetMapping("/getPricesBetween/{from}/to/{to}")
	public ResponseEntity<List<CotizacionDTO>> getPricesBetween(HttpServletRequest request, @PathVariable String from, @PathVariable String to) {
			
			List<CotizacionDTO> result = null;
			
			try {				 
				 result = currenciesServiceImpl.findBetweenDates(Utils.getDateFromString(from), Utils.getDateFromString(to));
				 System.out.println("Statistics :  " + result.stream().mapToDouble(CotizacionDTO::getLpriceAsDouble).summaryStatistics());
			}catch(NullPointerException nex) {
				throw new PricesNotFoundException();
			} catch (ParseException e) {				
				e.printStackTrace();
			}	
		
			System.out.println("SIZE : " + result.size());
		
		return ResponseEntity.ok(result);
	}
	

	
	
	
	
	
	
}
