package com.wenance.digitalcurrencies.repo;

import javax.persistence.Entity;
import javax.persistence.Id;


import com.wenance.digitalcurrencies.dtos.CotizacionDTO;


//Es el mismo objeto (mas un id) pero como el repo JPA utiliza entidades es necesario ponerle la etiqueta, ahora si lo hubiese dejado en el objeto base utilizado por mongo entonces
//no hubiese funcioado porque mongo rechaza este tipo de objetos o almenos su repositorio.
@Entity
public class CotizacionDtoTest extends CotizacionDTO{
	
	@Id
	private Integer id;

}
