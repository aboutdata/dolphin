package com.sabsari.dolphin.core.common;

public enum ISO8601 {
	
	DATETIME_PATTERN			("yyyyMMdd'T'HHmmssZZ"),
	DATETIME_EXTENDED_PATTERN	("yyyy-MM-dd'T'HH:mm:ssZZ"),	
	TIME_PATTERN				("HHmmss"),
	TIME_EXTENDED_PATTERN		("HH:mm:ss"),
	DATE_PATTERN				("yyyyMMdd"),
	DATE_EXTENDED_PATTERN		("yyyy-MM-dd");
	
	private String format;
	
	ISO8601(String format){
		this.format = format;
	}

	public String format() {
		return this.format;
	}

	public static ISO8601 search(String format) {
		for (ISO8601 type : ISO8601.values()) {
			if(type.format().equals(format))
				return type;
		}
		return null;
	}
	
	public static boolean isValidFormat(String format) {
		if (search(format) == null) {
			return false;
		}
		else {
			return true;
		}
	}
}
