package com.wenance.digitalcurrencies.controller;

import java.io.IOException;
import java.util.List;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.QueryParam;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wenance.digitalcurrencies.bo.RateConvertRequest;
import com.wenance.digitalcurrencies.bo.RateConvertResponse;
import com.wenance.digitalcurrencies.custom.ExchangeRateOrquesterCustom;
import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.dtos.StatisticsDTO;
import com.wenance.digitalcurrencies.enums.Currencies;
import com.wenance.digitalcurrencies.exception.PageNotFoundException;
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
	
	@Autowired
	private ExchangeRateOrquesterCustom exchangeRateOrquesterCustom;

		
	@ApiOperation(value = "getPrices", response = CotizacionDTO.class, produces ="application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/getPrices")
	public ResponseEntity<List<CotizacionDTO>> getPrices() {		
			
		
		return ResponseEntity.ok(currenciesServiceImpl.findAll());
	}
	

	
	@ApiOperation(value = "getPricesBetween", response = StatisticsDTO.class, produces ="application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/getStatistics/{from}/to/{to}/{currencie}")
	public ResponseEntity<StatisticsDTO> getStatisticsBetween(HttpServletRequest request, @PathVariable String from, @PathVariable String to, @PathVariable String currencie) {
			
		StatisticsDTO response = null;
		
		response = currenciesServiceImpl.findStatisticsBetween(Utils.getDateFromString(from), Utils.getDateFromString(to), Currencies.valueOf(currencie));
		
		return ResponseEntity.ok(response);
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
		
		response = currenciesServiceImpl.findPriceByTimestamp(new MongoDBDate(Utils.getDateFromString(from)), currencie);		
	
		return ResponseEntity.ok(response);
	}
	
	@ApiOperation(value = "getLastSaved", response = CotizacionDTO.class, produces ="application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/getLast/{currencie}")	
	public ResponseEntity<CotizacionDTO> getLastSaved(HttpServletRequest request, @PathVariable String currencie){
		
		return ResponseEntity.ok(currenciesServiceImpl.findLast(currencie));
	}
	
	@ApiOperation(value = "getPriceInUSD", response = RateConvertResponse.class, produces ="application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("/getPriceInUSD") // Podríamos poner un @QueryParam para mandar el parámetro del tipo de moneda pero lo recibimos en el json.	
	public ResponseEntity<RateConvertResponse> getPriceInUSD(HttpServletRequest request) throws BadRequestException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		RateConvertRequest _request = null;
		
		try {
			_request = mapper.readValue(request.getInputStream(), RateConvertRequest.class);
		} catch (IOException e) {			
			throw new BadRequestException();			
		}	
		
		RateConvertResponse response = exchangeRateOrquesterCustom.execute(_request);
		
		return ResponseEntity.ok(response);
	}
	
	@ApiOperation(value = "Devuelve todas las cotizaciones páginas, opcionalmente se puede acotar las fechas", response = List.class, produces ="application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/getPrices/{page}")	
	public ResponseEntity<List<CotizacionDTO>> getPricesInPageNumber(HttpServletRequest request, @PathVariable String page, 
			@QueryParam(value = "from") String from, @QueryParam(value = "to") String to, @QueryParam(value= "currencie") String currencie) throws PageNotFoundException {
		
		List<CotizacionDTO> anyPage = null;
		
		try {
				anyPage = currenciesServiceImpl.getPaginatedInfo(Integer.parseInt(page), from, to, currencie);
		
		}catch(IllegalArgumentException e) {
			if(e.toString().contains("java.lang.IllegalArgumentException: fromIndex"))
			throw new PageNotFoundException("No ha más páginas para mostrar . . . ");
		}
		
		return ResponseEntity.ok(anyPage);
	}
	
}
