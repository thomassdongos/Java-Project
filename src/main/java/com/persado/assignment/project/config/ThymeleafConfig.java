package com.persado.assignment.project.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@EnableAutoConfiguration
@Configuration
public class ThymeleafConfig {
	
	 private static final String VIEW_RESOLVER_PREFIX = "templates/";
	    private static final String VIEW_RESOLVER_SUFFIX = ".html";
	    private static final String VIEW_RESOLVER_MODE = "HTML5";
	    private static final String VIEW_RESOLVER_ENCODING = "UTF-8";
	
	// Thymeleaf configuration
    @Bean
	public ViewResolver viewResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setTemplateMode(VIEW_RESOLVER_MODE);
		templateResolver.setPrefix(VIEW_RESOLVER_PREFIX);
		templateResolver.setSuffix(VIEW_RESOLVER_SUFFIX);
		templateResolver.setCharacterEncoding(VIEW_RESOLVER_ENCODING);
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver);

		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(engine);
		return viewResolver;
	}
}
