package com.onlym.converter.handlers;

import com.onlym.converter.client.ConversionRateGetterClientDotCom;
import com.onlym.converter.client.ConversionRateGetterClientDotIo;
import com.onlym.converter.model.ExternalProviderResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ConversionHandler {

//    private final ConversionRateGetterClientDotIo client;
//
//    public ConversionHandler(ConversionRateGetterClientDotIo client) {
//        this.client = client;
//    }

    private final ConversionRateGetterClientDotCom client;

    public ConversionHandler(ConversionRateGetterClientDotCom client) {
        this.client = client;
    }

    public Mono<ServerResponse> conversion(ServerRequest request) {
        Mono<ExternalProviderResponseEntity> rates = this.client.getRates();
        rates.subscribe(entity -> System.out.println(entity.getRates().get("AED")));
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Rate is "));

    }

}
