package com.sabsari.dolphin.utils.constants;

public enum ISO8601Format {
	
	DATETIME_PATTERN			("yyyyMMdd'T'HHmmssZZ"),
	DATETIME_EXTENDED_PATTERN	("yyyy-MM-dd'T'HH:mm:ssZZ"),	
	TIME_PATTERN				("HHmmss"),
	TIME_EXTENDED_PATTERN		("HH:mm:ss"),
	DATE_PATTERN				("yyyyMMdd"),
	DATE_EXTENDED_PATTERN		("yyyy-MM-dd");
	
	private String format;
	
	ISO8601Format(String format){
		this.format = format;
	}

	public String getFormat() {
		return this.format;
	}

	public static ISO8601Format search(String format) {
		for (ISO8601Format type : ISO8601Format.values()) {
			if(type.getFormat().equals(format))
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
