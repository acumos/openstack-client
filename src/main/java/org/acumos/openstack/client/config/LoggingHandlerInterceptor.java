package org.acumos.openstack.client.config;


import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acumos.openstack.client.logging.ONAPLogAdapter;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
* Adds request details to the mapped diagnostic context (MDC) so they can be
* logged. <BR>
* http://www.devgrok.com/2017/04/adding-mdc-headers-to-every-spring-mvc.html
*/
@Component
public class LoggingHandlerInterceptor  extends HandlerInterceptorAdapter {	
	
	
	/**
	 * Invokes LogAdapter. Unfortunately ONAP use different conventions for key naming.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {	
		 ONAPLogAdapter logAdapter = new ONAPLogAdapter(LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()));
	 	 logAdapter.entering(request); 
		 return true;		
	}	
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		final ONAPLogAdapter adapter = new ONAPLogAdapter(LoggerFactory.getLogger((MethodHandles.lookup().lookupClass())));
		adapter.exiting();
	}
}	
