package com.wenance.digitalcurrencies.enums;

public enum Currencies {
	
	BTC("BTC"), ETH("ETH"), USD("USD");
	
	String symbol;
	
	Currencies(String symbol){
		this.symbol = symbol;
	}
	
	
	public String getSymbol() {
		return this.symbol;
	}
	

}
