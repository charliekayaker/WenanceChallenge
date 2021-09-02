package com.wenance.digitalcurrencies.controler;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.dtos.PricesDetailsDTO;
import com.wenance.digitalcurrencies.enums.Currencies;
import com.wenance.digitalcurrencies.exception.PricesNotFoundException;
import com.wenance.digitalcurrencies.model.CurrenciesServiceImpl;

import io.swagger.annotations.ApiOperation;


@RestController
@Singleton
@RequestMapping("api")  //# http://localhost:9800/api/
public class PricesController {
	
	
	@ApiOperation(value = "getPrices", response = CotizacionDTO.class, produces ="application/json")
	@GetMapping("/")
	public ResponseEntity<List<PricesDetailsDTO>> getPrices()
			throws Exception {
		
		CotizacionDTO cot =  new CotizacionDTO();
		cot.setCurrency(Currencies.BTC.getSymbol());
		cot.setPrice("$45.23");
		///		
			CurrenciesServiceImpl currenciesServiceImpl = new CurrenciesServiceImpl();
			List<PricesDetailsDTO> result = currenciesServiceImpl.findBetweenDates(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
			if(result == null)
				throw new PricesNotFoundException(); // Hay que manejarlo en CurrenciesServiceImpl
		///
		
		return ResponseEntity.ok(result);
	}
	
	
	
}
