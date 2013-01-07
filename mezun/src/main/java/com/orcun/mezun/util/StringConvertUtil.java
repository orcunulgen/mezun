package com.orcun.mezun.util;

public class StringConvertUtil {
	
	public static Long stringToLong(String str) {
		Long result = null;
		try {
			result = Long.valueOf(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
		return result;

	}

}
