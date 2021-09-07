package com.wenance.digitalcurrencies;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.exception.PricesNotFoundException;
import com.wenance.digitalcurrencies.model.CurrenciesServiceImpl;
import com.wenance.digitalcurrencies.repo.CurrenciesRepositoryTest;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class DigitalCurrenciesITest { //Acá debería heredar de una clase base pero por temas de tiempo lo hacemos así
	
	public static final String USD = "USD";
	public static final String ETH = "ETH";
	public static final String BTC = "BTC";
	
	@Mock
	private CurrenciesRepositoryTest currenciesRepositoryTest;
	
	@InjectMocks
	private CurrenciesServiceImpl currenciesServiceImpl;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none(); //deprecated -> Assert.assertThrows(null, null); en su lugar
	
	private CotizacionDTO dto1; 
	private CotizacionDTO dto2;
	private CotizacionDTO dto3;
	private CotizacionDTO dto4;
	
	
	@Before
	public void before() {
		
		dto1 = new CotizacionDTO();
		dto1.setCurr1(BTC);
		dto1.setCurr2(USD);
		dto1.setLprice("45000.29");
		dto1.setTimestamp(new Date(new Date().getTime()- 15000));
		
		dto2 = new CotizacionDTO();
		dto2.setCurr1(BTC);
		dto2.setCurr2(USD);
		dto2.setLprice("47222.72");
		dto2.setTimestamp(new Date(new Date().getTime()- 65000));
		
		dto3 = new CotizacionDTO();
		dto3.setCurr1(ETH);
		dto3.setCurr2(USD);
		dto3.setLprice("3900.55");
		dto3.setTimestamp(new Date(new Date().getTime()- 95000));
		
		dto4 = new CotizacionDTO();
		dto4.setCurr1(ETH);
		dto4.setCurr2(USD);
		dto4.setLprice("4500.22");
		dto4.setTimestamp(new Date(new Date().getTime()- 35000));
		
		List<CotizacionDTO> list = new ArrayList<>();
		list.add(dto1);
		list.add(dto2);
		list.add(dto3);
		list.add(dto4);
		
		when(currenciesRepositoryTest.findAll()).thenReturn(list);
		
	}
	
	
	@Test
	public void testNotMorePage() throws PricesNotFoundException {
				
		Assert.assertThrows(PricesNotFoundException.class, null);
	}
	
	@Test
	public void getAllPrices() {
		List<CotizacionDTO> cotizaciones = currenciesRepositoryTest.findAll();
		assertNotNull(cotizaciones);
		System.out.println(cotizaciones.size());
	}
}
