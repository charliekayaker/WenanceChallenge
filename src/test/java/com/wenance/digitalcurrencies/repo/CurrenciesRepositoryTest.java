package com.wenance.digitalcurrencies.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wenance.digitalcurrencies.dtos.CotizacionDTO;

public interface CurrenciesRepositoryTest extends JpaRepository<CotizacionDTO, String> {
	

}
