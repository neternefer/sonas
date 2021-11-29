package com.sonas.gatewayservice.config;

import com.sonas.gatewayservice.filters.MyAuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/api/auth/**")
                        .uri("lb://AUTH-SERVICE"))
                .route(p -> p.path("/api/auth**")
                        .uri("lb://AUTH-SERVICE"))
                .route(p -> p.path("/api/users/**")
                        .filters(f -> f.filter(new MyAuthFilter().apply(new MyAuthFilter.Config())))
                        .uri("lb://USER-SERVICE"))
                .route(p -> p.path("/api/users**")
                        .filters(f -> f.filter(new MyAuthFilter().apply(new MyAuthFilter.Config())))
                        .uri("lb://USER-SERVICE"))
                .route(p -> p.path("/api/curriculums/**")
                        .filters(f -> f.filter(new MyAuthFilter().apply(new MyAuthFilter.Config())))
                        .uri("lb://CV-SERVICE"))
                .route(p -> p.path("/api/curriculums**")
                        .filters(f -> f.filter(new MyAuthFilter().apply(new MyAuthFilter.Config())))
                        .uri("lb://CV-SERVICE"))
                .route(p -> p.path("/api/portfolios/**")
                        .filters(f -> f.filter(new MyAuthFilter().apply(new MyAuthFilter.Config())))
                        .uri("lb://PORTFOLIO-SERVICE"))
                .route(p -> p.path("/api/portfolios**")
                        .filters(f -> f.filter(new MyAuthFilter().apply(new MyAuthFilter.Config())))
                        .uri("lb://PORTFOLIO-SERVICE"))
                .route(p -> p.path("/api/jobs/**")
                        .filters(f -> f.filter(new MyAuthFilter().apply(new MyAuthFilter.Config())))
                        .uri("lb://JOB-SERVICE"))
                .route(p -> p.path("/api/jobs**")
                        .filters(f -> f.filter(new MyAuthFilter().apply(new MyAuthFilter.Config())))
                        .uri("lb://JOB-SERVICE"))
                .build();
    }
}

