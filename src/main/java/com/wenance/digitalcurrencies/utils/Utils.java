package com.wenance.digitalcurrencies.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.wenance.digitalcurrencies.constants.Constants;


public class Utils {
	
	
	public static String getValueFromProperties(String key, String propertiesName) {		
		
		Properties prop = null;
		
		try {
			  prop = new Properties();
			  InputStream in = new BufferedInputStream (new FileInputStream(propertiesName));
			  prop.load(in);			  
			  
		 }catch(IOException io) {			
			 io.printStackTrace();
		 }
		
		return prop.getProperty(key);		
	}
	
	public static Date getDateFromString(String sDate) throws ParseException {
		
		DateFormat inDateFormat = new SimpleDateFormat(Constants.DATE_TEMPLATE);			
				
		Date date = inDateFormat.parse(sDate);
		
		return date;
	}
	
		
}
