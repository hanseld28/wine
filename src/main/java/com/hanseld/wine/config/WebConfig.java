package com.hanseld.wine.config;

import java.util.Locale;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer, WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
	
	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		factory.addErrorPages(
				new ErrorPage(HttpStatus.NOT_FOUND, "/404"),
				new ErrorPage(HttpStatus.FORBIDDEN, "/403"),
				new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/vinhos");
	}
}
