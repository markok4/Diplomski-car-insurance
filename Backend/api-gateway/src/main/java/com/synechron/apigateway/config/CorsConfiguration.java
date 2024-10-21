package com.synechron.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfiguration extends org.springframework.web.cors.CorsConfiguration {
    @Bean
    public CorsWebFilter corsFilter() {
        org.springframework.web.cors.CorsConfiguration corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://127.0.0.1/:5173"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD","PATCH"));
        corsConfiguration.addAllowedHeader("origin");
        corsConfiguration.addAllowedHeader("content-type");
        corsConfiguration.addAllowedHeader("accept");
        corsConfiguration.addAllowedHeader("authorization");
        corsConfiguration.addAllowedHeader("cookie");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }
}
