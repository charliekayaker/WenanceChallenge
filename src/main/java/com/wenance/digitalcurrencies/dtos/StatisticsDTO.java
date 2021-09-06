package com.wenance.digitalcurrencies.dtos;

import lombok.Data;

public @Data class StatisticsDTO {
	
	private String dateFrom;
	private String dateTo;
	private String currencie;
	private String averageValue;
	private String percentageDiff;
	private String maxValue;

}
