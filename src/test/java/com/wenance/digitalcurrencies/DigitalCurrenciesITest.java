package com.wenance.digitalcurrencies;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class DigitalCurrenciesITest { //Acá debería heredar de una clase base pero por temas de tiempo lo hacemos así
	

}
