package com.onlym.converter.handlers;

import com.onlym.converter.client.ConversionRateGetterClient;
import com.onlym.converter.client.ConversionRateGetterClientDotCom;
import com.onlym.converter.client.ConversionRateGetterClientDotIo;
import com.onlym.converter.model.ConversionRequest;
import com.onlym.converter.model.ConversionResponse;
import com.onlym.converter.model.ErrorConversionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

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
        Mono<ConversionRequest> conversionRequestMono = request.bodyToMono(ConversionRequest.class);
        Mono<ConversionResponse> responseMono = conversionRequestMono
                .flatMap(conversionRequest -> this.clients.get(1).getConversion(conversionRequest)
                        .onErrorResume(error -> Mono.empty())
                        .switchIfEmpty(Mono.defer(() -> this.clients.get(0).getConversion(conversionRequest)
                                .onErrorResume(error -> Mono.empty())
                                .defaultIfEmpty(new ConversionResponse("invalidOne", null, null, null)))));

        return responseMono.flatMap(conversionResponse -> {
            if (conversionResponse != null && !conversionResponse.getFrom().equals("invalidOne")) {
                return ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(conversionResponse);
            } else {
                return ServerResponse
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(
                                new ErrorConversionResponse("unsuccessful",
                                        "there are no providers available"));
            }
        });
    }

}
