package com.wenance.digitalcurrencies.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomRespository extends MongoRepository<String, String> { //VOY A TENER QUE HACER UNA CLASE PRICE QUE SEA MODELO DE LA COTIZACION Y ALGUN ID

}
