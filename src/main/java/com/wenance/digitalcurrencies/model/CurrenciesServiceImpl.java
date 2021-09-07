package com.wenance.digitalcurrencies.model;

import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Service;

import com.wenance.digitalcurrencies.bo.CurrencieValuePayload;
import com.wenance.digitalcurrencies.constants.Constants;
import com.wenance.digitalcurrencies.dtos.CotizacionDTO;
import com.wenance.digitalcurrencies.dtos.StatisticsDTO;
import com.wenance.digitalcurrencies.enums.Currencies;
import com.wenance.digitalcurrencies.externalservices.WSPricesInUSDImpl;
import com.wenance.digitalcurrencies.repository.CurrenciesRespository;
import com.wenance.digitalcurrencies.utils.Utils;


@Service
public class CurrenciesServiceImpl implements CurrenciesService {
	
	public CurrenciesServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	private final MongoTemplate mongoTemplate;	
	
	@Autowired
	private WSPricesInUSDImpl _wsPricesInUSDImpl;
			
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
	public CurrencieValuePayload convertToUSD(CurrencieValuePayload request) {
		
		return (CurrencieValuePayload) _wsPricesInUSDImpl.convertCurrencietoUSD(request);
	}
	
	
	@Override	
	public List<CotizacionDTO> getPaginatedInfo(int page, String from, String to , String currencie) {	
		
		final int max_rows = 100;
		
		if((from != null && from.length()> 1) && (to != null && to.length() > 1))
		    return getPaginatedInfoWithTimeStampFilter(page, max_rows, from, to, currencie);
		else
			return getPaginatedInfo(page, max_rows);			
		
	}
	
	
	
	private List<CotizacionDTO> getPaginatedInfo(int page, int pageSize){
		
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("timestamp")));
				
		return currenciesrepository.findAll(pageable).toList();
	}
	
	private List<CotizacionDTO> getPaginatedInfoWithTimeStampFilter(int page, int pageSize, String from, String to, String currencie){
		
		
		List<CotizacionDTO> result = currenciesrepository.findAll().stream()
			.filter(dto -> dto.getTimestamp()!=null && dto.getTimestamp().getTime()
				>=Utils.getDateFromString(from).getTime() 
					&& dto.getTimestamp() !=null && dto.getTimestamp().getTime() 
				 		<= Utils.getDateFromString(to).getTime()).collect(Collectors.toList()); 
		
		result = currencie!=null && currencie.length()>1 ? result.stream().filter(dto -> dto.getCurr1().equals(currencie)).collect(Collectors.toList()) : result;
		
		if( currencie!=null && currencie.length()>1 )
			result.stream().filter(dto -> dto.getCurr1().equals(currencie));
		
			
		if(result!=null && result.size()> pageSize ) {
			if(page * pageSize > result.size()-100)
				return result.subList(page * pageSize,(page + 1) * pageSize -  (((page+1) * pageSize) - result.size()));
			else	
			   return result.subList(page * pageSize, (page + 1) * pageSize);
		}
				
		return result;
	}
		
	/*Pageable pageable = PageRequest.of(page, 200, Sort.by(Sort.Order.desc("timestamp")));
	
	result = currenciesrepository.findAll(pageable)
				.filter(dto -> dto.getTimestamp().getTime() >= Utils.getDateFromString(from).getTime() &&
						 dto.getTimestamp().getTime() <= Utils.getDateFromString(to).getTime()).toList();*/
	
}
