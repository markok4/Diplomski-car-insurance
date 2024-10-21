package com.synechron.apigateway;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Car Insurance API", version = "1.0", description = "Documentation Car Insurance API v1.0"))
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/car-service/v3/api-docs").uri("lb://car-service"))
                .route(r -> r.path("/claim-management-service/v3/api-docs").uri("lb://claim-management-service"))
                .route(r -> r.path("/payment-service/v3/api-docs").uri("lb://payment-service"))
                .route(r -> r.path("/policy-creation-service/v3/api-docs").uri("lb://policy-creation-service"))
                .route(r -> r.path("/user-management-service/v3/api-docs").uri("lb://user-management-service"))
                .build();
    }
}
