package com.wenance.digitalcurrencies.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
			InputStream in = new BufferedInputStream(new FileInputStream(propertiesName));
			prop.load(in);

		} catch (IOException io) {
			io.printStackTrace();
		}

		return prop.getProperty(key);
	}

	public static Date getDateFromString(String sDate)  {

		DateFormat inDateFormat = new SimpleDateFormat(Constants.DATE_TEMPLATE);
		
		Date date = null;
		
		try {
				date = inDateFormat.parse(sDate);
				
		}catch(ParseException e) {
			
			e.printStackTrace();
			
		}
				

		return date;
	}

	
	public static Object addCustomTimeStamp(Object obj) {

		if (obj == null) {
			throw new NullPointerException();
		}

		String className = obj.getClass().getName();		
		
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
			
			Class<?>[] pType = { Date.class };
			Date date = new Date(System.currentTimeMillis());
			Object[] pValue = {  new Date(date.getTime() - Constants.TIMEOFFSET ) };
						
			
			Method m;			
			m = clazz.getMethod("setTimestamp", pType);
			m.invoke(obj, pValue );			
			
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException 
						| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace(); //Cualquiera de estos errores es por reflection por eso no los tratamos por separados.		
		}

		return obj;
	}
	

	
	public static String getTimesStampAsString() {
		
		Date date = new Date(System.currentTimeMillis());
		
		return getDateFormated(date);
	}
	
	public static String getPercentDiffBetween(Double avg, Double max) {
		
		String result = getCustomDecimalFormat("#.##").format(100-(avg * 100 / max)) + Constants.SPACE + Constants.PERCENT;
		
		return result;
	}
	
	public static DecimalFormat getCustomDecimalFormat(String format){
		
		return new DecimalFormat(format);
	}
	
	public static String getDateFormated(Date date) {
		
		SimpleDateFormat formater = new SimpleDateFormat(Constants.DATE_TEMPLATE);
		
		return formater.format(date);
	}

}
