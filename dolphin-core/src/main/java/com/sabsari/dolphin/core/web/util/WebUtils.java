package com.sabsari.dolphin.core.web.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class WebUtils {

	public static String getRemoteIp(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String remoteIp = httpRequest.getHeader("X-Forwarded-For");
		if (remoteIp == null) {
			remoteIp = httpRequest.getRemoteAddr();
		}
		return remoteIp;
	}
	
	public static String getRequestUri(HttpServletRequest request) {
		return request.getRequestURI().substring(request.getContextPath().length());
	}
}