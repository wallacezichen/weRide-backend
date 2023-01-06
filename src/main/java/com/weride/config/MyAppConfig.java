package com.weride.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@Configuration
public class MyAppConfig implements WebMvcConfigurer {
	@Value("${allowed.origins}")
	private String[] allowedOrigins;

	@Value("${spring.data.rest.base-path}")
	private String basePath;

	@Override
	public void addCorsMappings(CorsRegistry cors) {
		// set up cors mapping
		cors.addMapping(basePath + "/**").allowedOrigins(allowedOrigins).allowedHeaders("*").allowedMethods("*");
//		cors.addMapping(basePath + "/**").allowedOrigins(allowedOrigins);
	}
}

//@Configuration
//public class MyAppConfig implements WebMvcConfigurer {
//	@Bean
//	public WebMvcConfigurer corsConfigurer(){
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//				   .allowedOrigins(ALL)
//				   .allowedMethods(ALL)
//				   .allowedHeaders(ALL)
//				   .allowCredentials(true);
//			}
//		};
//	}
//}
