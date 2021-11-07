package com.onlym.converter.config;

import com.onlym.converter.handlers.ConversionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class MainRouter {

    @Bean
    public RouterFunction<ServerResponse> route(ConversionHandler conversionHandler) {
        RequestPredicate route = GET("/rate").and(accept(MediaType.TEXT_PLAIN));

        return RouterFunctions.route(route, conversionHandler::conversion);
    }
}