package com.ibk.ivr.ca.common.util;

public class FormatUtil {
	
	public static String formatPhone(String value) {
		if(value == null) 
			return "";
		
		value = value.trim();
		
		int len = value.length();
		if(value.startsWith("02")) {
			if(len == 10)
				return value.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "$1-$2-$3");
			else if(len == 9)
				return value.replaceFirst("(\\d{2})(\\d{3})(\\d{4})", "$1-$2-$3");
		} else {
			if(len == 11)
				return value.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
			else if(len == 10)
				return value.replaceFirst("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");
			else if(len == 9)
				return value.replaceFirst("(\\d{2})(\\d{3})(\\d{4})", "$1-$2-$3");
		}
		return value;
	}
	
	public static String formatPhoneMasking(String value) {
		if(value == null) 
			return "";
		
		value = value.trim();
		
		int len = value.length();
		if(value.startsWith("02")) {
			if(len == 10)
				return value.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "$1-****-$3");
			else if(len == 9)
				return value.replaceFirst("(\\d{2})(\\d{3})(\\d{4})", "$1-****-$3");
		} else {
			if(len == 11)
				return value.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-****-$3");
			else if(len == 10)
				return value.replaceFirst("(\\d{3})(\\d{3})(\\d{4})", "$1-****-$3");
			else if(len == 9)
				return value.replaceFirst("(\\d{2})(\\d{3})(\\d{4})", "$1-****-$3");
		}
		return value;
	}
	
	public static String formatBizNo(String value) {
		if(value == null) 
			return "";

		value = value.trim();
		
		if(value.length() == 11)
			return value.replaceFirst("(\\d{3})(\\d{2})(\\d{5})", "$1-$2-$3");
		
		return value;
	}
	
	public static String formatDate(String value, String formt) {
		if(value == null) 
			return "";

		value = value.trim();
		
		if(value.length() == 8)
			return value.replaceFirst("(\\d{4})(\\d{2})(\\d{2})", formt);
		else if(value.length() == 6)
			return value.replaceFirst("(\\d{4})(\\d{2})", formt);
		
		return value;
	}
}
