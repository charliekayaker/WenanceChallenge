package com.wenance.digitalcurrencies.controler;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.exception.PricesNotFoundException;
import com.wenance.digitalcurrencies.model.CurrenciesServiceImpl;
import com.wenance.digitalcurrencies.model.MongoDBDate;
import com.wenance.digitalcurrencies.utils.Utils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	private CurrenciesServiceImpl currenciesServiceImpl;
	
	
	@ApiOperation(value = "getPrices", response = CotizacionDTO.class, produces ="application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/getPrices")
	public ResponseEntity<List<CotizacionDTO>> getPrices() {		
			
		
		return ResponseEntity.ok(currenciesServiceImpl.findAll());
	}
	

	/**
	 * PUNTO 1 B - LE FALTA LIMAR LA PRESENTACIÓN. :) TRABAJARLO EN EL SERVICE PARTE DEL DOMINIO
	 * */
	@ApiOperation(value = "getPricesBetween", response = CotizacionDTO.class, produces ="application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/getPricesBetween/{from}/to/{to}/{currencie}")
	public ResponseEntity<List<CotizacionDTO>> getPricesBetween(HttpServletRequest request, @PathVariable String from, @PathVariable String to, @PathVariable String currencie) {
			System.out.println("currencie : " + currencie);
			List<CotizacionDTO> result = null;
			
			try {				 
				 result = currenciesServiceImpl.findBetweenDates(Utils.getDateFromString(from), Utils.getDateFromString(to)).stream().filter(dto -> dto.getCurr1().equals(currencie))
						 .collect(Collectors.toList());
				 System.out.println("Statistics :  " + result.stream().filter(dto -> dto.getCurr1().equals(currencie)).mapToDouble(CotizacionDTO::priceAsDouble).summaryStatistics());
			}catch(NullPointerException nex) {
				throw new PricesNotFoundException();
			} catch (ParseException e) {				
				e.printStackTrace();
			}			
		
		return ResponseEntity.ok(result);
	}
	
	
	/**
	 * PUNTO 1 A
	 * */
	@ApiOperation(value = "getPricesInTimestamp", response = CotizacionDTO.class, produces ="application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/getPricesInTimestamp/{from}/{currencie}")	
	public ResponseEntity<CotizacionDTO> getPricesInTimestamp(@PathVariable String from, @PathVariable String currencie) {
		
		CotizacionDTO response = null;
		
		try {
			response = currenciesServiceImpl.findPriceByTimestamp(new MongoDBDate(Utils.getDateFromString(from)), currencie);		
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(response);
	}
	

	
	
	
	
	
	
}
