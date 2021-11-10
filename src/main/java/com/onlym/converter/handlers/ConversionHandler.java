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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ConversionHandler {

    private final List<ConversionRateGetterClient> clients;
    private final Random rand = new Random();

    public ConversionHandler(ConversionRateGetterClientDotCom client0, ConversionRateGetterClientDotIo client1) {
        clients = List.of(client0, client1);
    }

    public Mono<ServerResponse> convert(ServerRequest request) {
        int clientIndex = rand.nextInt(2);
        Mono<ConversionRequest> conversionRequest = request.bodyToMono(ConversionRequest.class);
        Mono<ConversionResponse> conversionResponse = conversionRequest.flatMap(this.clients.get(1)::getConversion);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(conversionResponse, ConversionResponse.class);
    }

}
