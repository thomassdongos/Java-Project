package com.persado.assignment.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    
 	@Override
 	protected void configure(HttpSecurity security) throws Exception {
 		
 		// Used to enable access to H2 database through http://localhost:8080/h2-console
 		security.csrf().disable().headers().frameOptions().disable();
 		// Disable username and password in login page
 		security.formLogin().disable();
 	}
 	
}
