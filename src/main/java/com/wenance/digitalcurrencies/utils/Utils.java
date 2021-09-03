package com.wenance.digitalcurrencies.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
			InputStream in = new BufferedInputStream(new FileInputStream(propertiesName));
			prop.load(in);

		} catch (IOException io) {
			io.printStackTrace();
		}

		return prop.getProperty(key);
	}

	public static Date getDateFromString(String sDate) throws ParseException {

		DateFormat inDateFormat = new SimpleDateFormat(Constants.DATE_TEMPLATE);

		Date date = inDateFormat.parse(sDate);

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
		SimpleDateFormat formater = new SimpleDateFormat(Constants.DATE_TEMPLATE);
		
		return formater.format(date);
	}
	

}
