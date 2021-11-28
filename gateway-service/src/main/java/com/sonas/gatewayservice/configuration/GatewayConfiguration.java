package com.sonas.gatewayservice.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/api/users/**")
                        .uri("lb://USER-SERVICE"))
                .route(p -> p.path("/api/users**")
                        .uri("lb://USER-SERVICE"))
                .route(p -> p.path("/api/curriculums/**")
                        .uri("lb://CV-SERVICE"))
                .route(p -> p.path("/api/curriculums**")
                        .uri("lb://CV-SERVICE"))
                .route(p -> p.path("/api/portfolios/**")
                        .uri("lb://PORTFOLIO-SERVICE"))
                .route(p -> p.path("/api/portfolios**")
                        .uri("lb://PORTFOLIO-SERVICE"))
                .route(p -> p.path("/api/jobs/**")
                        .uri("lb://JOB-SERVICE"))
                .route(p -> p.path("/api/jobs**")
                        .uri("lb://JOB-SERVICE"))
                .build();
    }
}
