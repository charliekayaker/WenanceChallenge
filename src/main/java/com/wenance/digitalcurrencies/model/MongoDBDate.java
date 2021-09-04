package com.wenance.digitalcurrencies.model;

import java.util.Date;

import com.wenance.digitalcurrencies.constants.Constants;

public class MongoDBDate extends Date {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MongoDBDate() {
		this.setTime(getTime() - Constants.TIMEOFFSET);
	}
	
	public MongoDBDate(Date date) {
		this.setTime(date.getTime() - Constants.TIMEOFFSET);
	}
	
	
	public MongoDBDate(long l) {
		super(l);		
	}
	
	

}
