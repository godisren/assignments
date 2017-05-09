package com.poseitech.assignment;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

//@Configuration
public class WebConfiguration extends WebMvcAutoConfigurationAdapter{

	@Bean
    public FilterRegistrationBean hiddenHttpMethodFilter(){
        FilterRegistrationBean hiddenHttpMethodFilter = new FilterRegistrationBean(new HiddenHttpMethodFilter(), dispatcherServlet());
        hiddenHttpMethodFilter.addUrlPatterns("/test/*");
        return hiddenHttpMethodFilter;
    }
	
	@Bean
    public ServletRegistrationBean dispatcherServlet(){
        ServletRegistrationBean dispatcherServlet = new ServletRegistrationBean(new DispatcherServlet());
        dispatcherServlet.setName("dispatcherServlet");
        dispatcherServlet.addUrlMappings("/dispatcher/*");
        return dispatcherServlet;
    }
}
