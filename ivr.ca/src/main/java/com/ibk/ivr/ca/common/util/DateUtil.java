package com.ibk.ivr.ca.common.util;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
	
	public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	public static final SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyyMM");
	    
    public static Date getDate() {
        return new Date(System.currentTimeMillis());
    }
    
    public static Date getDate(int field, int amount) {
    	Calendar c = Calendar.getInstance();
    	c.add(field, amount);
    	return c.getTime();
    }
    
    public static String getDateString(String format, int field, int amount) {
    	SimpleDateFormat sf = new SimpleDateFormat(format);
    	Calendar c = Calendar.getInstance();
    	c.add(field, amount);
    	return sf.format(c.getTime());
    }
    
    public static String getDateString(SimpleDateFormat format, int field, int amount) {
    	Calendar c = Calendar.getInstance();
    	c.add(field, amount);
    	return format.format(c.getTime());
    }
    
    public static String getDateString(String format) {
    	SimpleDateFormat sf = new SimpleDateFormat(format);
    	return sf.format(DateUtil.getDate());
    }
    
    public static String getDateString(Date date, String format) {
    	SimpleDateFormat sf = new SimpleDateFormat(format);
    	return sf.format(date);
    }
    
    public static Date parseDate(String str, String format) throws ParseException {
    	SimpleDateFormat sf = new SimpleDateFormat(format);
    	return sf.parse(str);
    }
    
    public static Date parseDate(String str, String format, int field, int amount) throws ParseException {
    	SimpleDateFormat sf = new SimpleDateFormat(format);
    	Date date = sf.parse(str);
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.add(field, amount);
    	return c.getTime();
    }
}
