package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.jwt.JwtAuthenticationEnteryPoint;
import com.example.demo.jwt.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEnteryPoint point;
	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/students/**").authenticated()
						.requestMatchers("/api/courses/**").authenticated().requestMatchers("/auth/**").permitAll()
						.anyRequest().authenticated())
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
		httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}

}