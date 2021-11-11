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

import java.util.List;
import java.util.Random;

@Component
public class ConversionHandler {

    private final List<ConversionRateGetterClient> clients;
    private final Random rand = new Random();

    public ConversionHandler(ConversionRateGetterClientDotCom client0, ConversionRateGetterClientDotIo client1) {
        clients = List.of(client0, client1);
    }

    private Mono<ConversionResponse> getDefault() {
        return Mono.just(new ConversionResponse("s", "s", 3., 3.));
    }

    private Mono<ConversionResponse> getResponse(ConversionRateGetterClient client, Mono<ConversionRequest> request) {
        return request.flatMap(client::getConversion);
    }

    public Mono<ServerResponse> convert(ServerRequest request) {
        int clientIndex = rand.nextInt(2);
        Mono<ConversionRequest> conversionRequestMono = request.bodyToMono(ConversionRequest.class);
        Mono<ConversionResponse> conversionResponse = conversionRequestMono
                .flatMap(conversionRequest -> this.clients.get(1).getConversion(conversionRequest))
//                .onErrorResume(error -> this.clients.get(0).getConversion(conversionRequest))
                .onErrorContinue((e, conversionRequest) -> {
//                    if ()
//                        this.clients.get(0).getConversion(conversionRequest);
                    System.out.println(conversionRequest.getClass());
                });
//                .onErrorReturn(new ConversionResponse("s", "s", 3., 3.));


        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(conversionResponse, ConversionResponse.class);
    }

}
