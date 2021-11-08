package com.onlym.converter.handlers;

import com.onlym.converter.client.ConversionRateGetterClient;
import com.onlym.converter.client.ConversionRateGetterClientDotCom;
import com.onlym.converter.client.ConversionRateGetterClientDotIo;
import com.onlym.converter.model.ConversionRequest;
import com.onlym.converter.model.ConversionResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
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

    private final ConversionRateGetterClient client1;
    private final ConversionRateGetterClient client2;

    public ConversionHandler(ConversionRateGetterClientDotCom client1, ConversionRateGetterClientDotIo client2) {
        this.client1 = client1;
        this.client2 = client2;
    }

    public Mono<ServerResponse> convert(ServerRequest request) {
        Mono<ConversionRequest> conversionRequest = request.bodyToMono(ConversionRequest.class);
        Mono<ConversionResponse> conversionResponse = conversionRequest.flatMap(this.client1::getConversion);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(conversionResponse, ConversionResponse.class);
    }

}
