package com.sonas.gatewayservice.filters;

import com.sonas.gatewayservice.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ResponseStatusException;

@Component
public class MyAuthFilter extends AbstractGatewayFilterFactory<MyAuthFilter.Config> {

    @Autowired
    JwtUtils jwtUtils;

    public MyAuthFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if(!exchange.getRequest()
                    .getHeaders()
                    .containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have permission to access this resource.");
            }
            String fullToken = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String tokenWithoutPrefix = fullToken.substring(7);
            if(!jwtUtils.validateToken(tokenWithoutPrefix)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have permission to access this resource.");
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {

    }
}
