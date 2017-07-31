package com.ftlh.wechat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sun.util.logging.resources.logging;

public class TimeUtil {
	
	
	public static long getSends2000Tonow(){
		  SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");

		   java.util.Date date = null;
		try {
			date = myFormatter.parse("2000-01-01");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	   //	System.err.println("date gettime ==="+date.getTime());
		   long now= (System.currentTimeMillis()/1000);
		//	System.err.println("now==="+now);
				 
	  return now-(date.getTime()/1000);
	}
	
	
}
