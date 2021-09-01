package com.wenance.digitalcurrencies.controler;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.ApiOperation;


@RestController
@Singleton
@RequestMapping("api")  //# http://localhost:9800/api/
public class PricesController {
	
	@ApiOperation(value = "getPrices", response = String.class, produces = "plain/text"/*"application/json"*/)
	@GetMapping("/")
	public ResponseEntity<String> getPrices()
			throws Exception {
		
		long mediationTime = System.currentTimeMillis();		
			
		String result = "H O L A ";
		
		return ResponseEntity.ok(result);
	}
	
	
	
}
