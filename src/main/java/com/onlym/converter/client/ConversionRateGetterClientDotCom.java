package com.onlym.converter.client;

import com.onlym.converter.model.ExternalProviderResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ConversionRateGetterClientDotCom {

    private final WebClient client;

    public ConversionRateGetterClientDotCom(WebClient.Builder builder) {
        this.client = builder.baseUrl("https://open.er-api.com").build();
    }

    public Mono<ExternalProviderResponseEntity> getRates() {
        return this.client
                .get()
                .uri(uriBuilder -> uriBuilder.path("/v6/latest/EUR")
                        .build())
                .retrieve()
                .bodyToMono(ExternalProviderResponseEntity.class);
    }

}
