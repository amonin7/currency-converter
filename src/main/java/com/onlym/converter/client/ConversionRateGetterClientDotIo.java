package com.onlym.converter.client;

import com.onlym.converter.model.ExternalProviderResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ConversionRateGetterClientDotIo {

    private final WebClient client;

    private static final String API_KEY = "a784febef3c9af6d0d42250ea624674f";

    public ConversionRateGetterClientDotIo(WebClient.Builder builder) {
        this.client = builder.baseUrl("http://api.exchangeratesapi.io").build();
    }

    public Mono<ExternalProviderResponseEntity> getRates() {
        return this.client
                .get()
                .uri(uriBuilder -> uriBuilder.path("/latest")
                        .queryParam("access_key", API_KEY)
                        .queryParam("base", "EUR")
                        .build())
                .retrieve()
                .bodyToMono(ExternalProviderResponseEntity.class);
    }

}
