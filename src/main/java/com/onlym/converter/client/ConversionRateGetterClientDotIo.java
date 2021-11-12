package com.onlym.converter.client;

import com.onlym.converter.exceptions.InvalidClientException;
import com.onlym.converter.model.ConversionRequest;
import com.onlym.converter.model.ConversionResponse;
import com.onlym.converter.model.ExternalProviderResponseEntity;
import com.onlym.converter.service.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ConversionRateGetterClientDotIo implements ConversionRateGetterClient {

    private final WebClient client;

//    private static final String API_KEY = "a784febef3c9af6d0d42250ea624674f";
    private static final String API_KEY = "";

    public ConversionRateGetterClientDotIo(WebClient.Builder builder) {
        this.client = builder.baseUrl("http://api.exchangeratesapi.io").build();
    }

    public Mono<ConversionResponse> getConversion(ConversionRequest conversionRequest) throws InvalidClientException {
        Mono<ExternalProviderResponseEntity> externalProviderResponseEntity = this.client
                .get()
                .uri(uriBuilder -> uriBuilder.path("/latest")
                        .queryParam("access_key", API_KEY)
                        .queryParam("base", "EUR")
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError,
                        clientResponse -> Mono.error(new InvalidClientException()))
                .bodyToMono(ExternalProviderResponseEntity.class)
                .flatMap(entity -> {
                    if (!entity.isSuccess()) {
                        return Mono.error(new InvalidClientException());
                    } else {
                        return Mono.just(entity);
                    }
                });

        return ConversionService.convertFromMono(conversionRequest, externalProviderResponseEntity);
    }

}
