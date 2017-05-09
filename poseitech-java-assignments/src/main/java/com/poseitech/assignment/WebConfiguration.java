package com.poseitech.assignment;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class WebConfiguration extends WebMvcAutoConfigurationAdapter {
	
	@Bean
	public FilterRegistrationBean hiddenHttpMethodFilter() {
		// HiddenHttpMethodFilter will treat request parameter with "_mehtod"
		// , that affact our URI like "students?_method=c"
		// disalbe HiddenHttpMethodFilter to avoid this issue 
		FilterRegistrationBean hiddenHttpMethodFilter = new FilterRegistrationBean(new HiddenHttpMethodFilter());
		hiddenHttpMethodFilter.setEnabled(false);
		return hiddenHttpMethodFilter;
	}
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
	    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
	    tomcat.addAdditionalTomcatConnectors(createSslConnector());
	    return tomcat;
	}

	private Connector createSslConnector() {
	    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	    Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
	    try {
	        File keystore = new ClassPathResource("spring.keystore").getFile();
	        connector.setScheme("https");
	        connector.setSecure(true);
	        connector.setPort(10443);
	        protocol.setSSLEnabled(true);
	        protocol.setKeystoreFile(keystore.getAbsolutePath());
	        protocol.setKeystorePass("tomcat");
	        protocol.setKeyAlias("tomcat");
	        return connector;
	    }
	    catch (IOException ex) {
	        throw new IllegalStateException("can't access keystore: [" + "keystore"
	                + "] or truststore: [" + "keystore" + "]", ex);
	    }
	}
}
