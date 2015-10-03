package com.sabsari.dolphin.core.auth.domain.code;

import org.apache.commons.lang3.StringUtils;

import com.sabsari.dolphin.core.common.util.SimpleCrypto;

public enum HttpAuthenticationScheme {
	BASIC("Basic"),
	BEARER("Bearer");

	private String scheme;
	
	private HttpAuthenticationScheme(String scheme){
		this.scheme = scheme;
	}

	public String scheme() {
		return this.scheme;
	}
	
	public static HttpAuthenticationScheme search(String value) {
		for (HttpAuthenticationScheme scheme : HttpAuthenticationScheme.values()) {
			if(scheme.scheme().equals(value))
				return scheme;
		}
		return null;
	}
	
	public static boolean isValidScheme(String scheme) {
		if (search(scheme) == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static final String WWWAuthenticate_HEADER = "WWW-Authenticate";	
	public static final String Authorization_HEADER = "Authorization";	
	public static final String USER_PASSWORD_DELIMITER = ":";
	
	private static final String SCHEME_DELIMITER = " ";
	private static final String REALM = "Global ID Platform";
	private static final String WWWAuthenticate_VALUE = " realm=\"" + REALM + "\"";

	public static String getWWWAuthenticate(HttpAuthenticationScheme scheme) {
		return scheme.scheme() + WWWAuthenticate_VALUE;
	}
	
	public static String[] parseHeader(String headerValue) {
		if (StringUtils.isEmpty(headerValue))
			return null;
		
		String[] values;		
		try {
			values = headerValue.split(SCHEME_DELIMITER);			
			if (!isValidScheme(values[0]))
				return null;
			
			values[1] = SimpleCrypto.base64Decode(values[1]);
		}
		catch (Exception ex) {
			return null;
		}
		
		return values;
	}
}
