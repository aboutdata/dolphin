package com.sabsari.dolphin.core.web.security.blockip;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.sabsari.dolphin.core.web.util.WebUtils;

@Component
public class BlockIpFilter extends GenericFilterBean {

	private final static Logger logger = LoggerFactory.getLogger(BlockIpFilter.class);
		
	@Autowired
	private BlockIpContainer blockIpContainer;
		
	@Override
	protected void initFilterBean() throws ServletException {}
		
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String ip = WebUtils.getRemoteIp(request);
				
		// check block ip
		if (blockIpContainer.isBlockIp(ip)) {
			logger.debug("block ip filtering! " + ip);
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
			return;
		}
		
		chain.doFilter(request, response);
	}
	
//	private boolean isError(ServletResponse response) {
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
//	    int status = httpResponse.getStatus();
//	    if (400 <= status && status < 500) {		// client error
//	        return true;
//	    }
//	    return false;
//	}
}
