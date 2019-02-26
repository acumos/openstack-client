package org.acumos.openstack.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Interceptor Configuration
 */
@Configuration
public class HandlerInterceptorConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	LoggingHandlerInterceptor loggingHandlerInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggingHandlerInterceptor);
	}
}
