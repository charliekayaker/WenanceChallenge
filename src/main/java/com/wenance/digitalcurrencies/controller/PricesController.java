package com.wenance.digitalcurrencies.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wenance.digitalcurrencies.bo.CurrencieValuePayload;
import com.wenance.digitalcurrencies.bo.EnqCurrVal_req;
import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.dtos.StatisticsDTO;
import com.wenance.digitalcurrencies.enums.Currencies;
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
	

	
	@ApiOperation(value = "getPricesBetween", response = StatisticsDTO.class, produces ="application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/getStatistics/{from}/to/{to}/{currencie}")
	public ResponseEntity<StatisticsDTO> getStatisticsBetween(HttpServletRequest request, @PathVariable String from, @PathVariable String to, @PathVariable String currencie) {
			
		StatisticsDTO response = null;
		try {
			response = currenciesServiceImpl.findStatisticsBetween(Utils.getDateFromString(from), Utils.getDateFromString(to), Currencies.valueOf(currencie));
		} catch (ParseException e) {
			e.printStackTrace();
		}
			/*List<CotizacionDTO> result = null;
			
			try {				 
				 result = currenciesServiceImpl.findBetweenDates(Utils.getDateFromString(from), Utils.getDateFromString(to)).stream().filter(dto -> dto.getCurr1().equals(currencie))
						 .collect(Collectors.toList());
				 System.out.println("Statistics :  " + result.stream().filter(dto -> dto.getCurr1().equals(currencie)).mapToDouble(CotizacionDTO::priceAsDouble).summaryStatistics());
			}catch(NullPointerException nex) {
				throw new PricesNotFoundException();
			} catch (ParseException e) {				
				e.printStackTrace();
			}	*/		
		
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
		
		try {
			response = currenciesServiceImpl.findPriceByTimestamp(new MongoDBDate(Utils.getDateFromString(from)), currencie);		
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		
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
	
	@ApiOperation(value = "getPriceInUSD", response = CurrencieValuePayload.class, produces ="application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("/getPriceInUSD/{currencie}")	
	public ResponseEntity<CurrencieValuePayload> getPriceInUSD(HttpServletRequest request, @PathVariable String currencie) throws IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		
		CurrencieValuePayload value = mapper.readValue(request.getInputStream(), CurrencieValuePayload.class);
		
		return ResponseEntity.ok(currenciesServiceImpl.convertToUSD(new EnqCurrVal_req(Currencies.valueOf(currencie), value.getAmnt())));
	}
	
}