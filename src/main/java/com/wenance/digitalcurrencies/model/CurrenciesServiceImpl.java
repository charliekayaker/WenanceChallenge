package com.wenance.digitalcurrencies.model;

import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.wenance.digitalcurrencies.bo.CurrencieValuePayload;
import com.wenance.digitalcurrencies.bo.EnqCurrVal_req;
import com.wenance.digitalcurrencies.constants.Constants;
import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.dtos.StatisticsDTO;
import com.wenance.digitalcurrencies.enums.Currencies;
import com.wenance.digitalcurrencies.externalservices.WSPricesInUSDImpl;
import com.wenance.digitalcurrencies.repository.CurrenciesRespository;
import com.wenance.digitalcurrencies.utils.Utils;


@Service
public class CurrenciesServiceImpl implements CurrenciesService {
	
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	private WSPricesInUSDImpl _wsPricesInUSDImpl;
	
	public CurrenciesServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Autowired
	private CurrenciesRespository currenciesrepository;

	@Override
	public void recordPrice(CotizacionDTO data) {		
		currenciesrepository.insert(data);
	}

	@Override
	public List<CotizacionDTO> findBetweenDates(Date from, Date to) throws NullPointerException  {		
		return currenciesrepository.findByTimestampBetween(new Date(from.getTime() - Constants.TIMEOFFSET), new Date(to.getTime() - Constants.TIMEOFFSET));	
	}
	
	@Override
	public List<CotizacionDTO> findAll(){
		return currenciesrepository.findAll();
	}

	@Override			 
	public CotizacionDTO findPriceByTimestamp(MongoDBDate date, String currencie) {
		 
		return currenciesrepository.timestampBetweenAndCurr1(date, new MongoDBDate(date.getTime()+999), currencie);
	}

	@Override
	public StatisticsDTO findStatisticsBetween(Date from, Date to, Currencies currencie) {
		//Usar un converter de List<CotizacionDTO> a StatisticsDTO 
		StatisticsDTO result = new StatisticsDTO();
		
		List<CotizacionDTO> list = findBetweenDates(from, to);
		
		DoubleSummaryStatistics statistics =  list.stream().filter(dto -> dto.getCurr1().equals(currencie.getSymbol()))
			.mapToDouble(CotizacionDTO::priceAsDouble).summaryStatistics();
		
		Double max = statistics.getMax();
		
		Double avg = statistics.getAverage();
		
		result.setDateFrom(from.toString());
		
		result.setDateTo(to.toString());
		
		result.setMaxValue(String.valueOf(max));
		
		result.setAverageValue(String.valueOf(avg));
		
		result.setCurrencie(currencie.getSymbol());
		
		result.setPercentageDiff(Utils.getPercentDiffBetween(avg, max));
		
		return result;
	}

	@Override
	public CotizacionDTO findLast(String currencie) {
		CotizacionDTO dto = new CotizacionDTO();
		dto.setCurr1(currencie);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("curr1").is(currencie))
		.limit(1)
		.with(Sort.by(Sort.Order.desc("timestamp")));
		return mongoTemplate.findOne(query, CotizacionDTO.class);
	}

	@Override
	public CurrencieValuePayload convertToUSD(EnqCurrVal_req request) {
		
		return (CurrencieValuePayload) _wsPricesInUSDImpl.convertCurrencietoUSD(request);
	}
	
	


	
	
	
}
