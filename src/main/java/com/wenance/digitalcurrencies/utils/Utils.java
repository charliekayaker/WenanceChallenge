package com.wenance.digitalcurrencies.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


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
	
}
