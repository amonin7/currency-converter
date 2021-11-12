package com.onlym.converter.client;

import com.onlym.converter.exceptions.InvalidClientException;
import com.onlym.converter.model.ConversionRequest;
import com.onlym.converter.model.ConversionResponse;
import com.onlym.converter.model.ExternalProviderResponseEntity;
import com.onlym.converter.service.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ConversionRateGetterClientDotCom implements ConversionRateGetterClient {

    private final WebClient client;

    public ConversionRateGetterClientDotCom(WebClient.Builder builder) {
        this.client = builder.baseUrl("https://open.er-api.com").build();
    }

    public Mono<ConversionResponse> getConversion(ConversionRequest conversionRequest) {
        Mono<ExternalProviderResponseEntity> externalProviderResponseEntity = this.client
                .get()
                .uri(uriBuilder -> uriBuilder.path("/v6/latest/" + conversionRequest.getFrom())
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError,
                        clientResponse -> Mono.error(new InvalidClientException()))
                .bodyToMono(ExternalProviderResponseEntity.class)
                .flatMap(entity -> {
                    if (!entity.getResult().equals("success")) {
                        return Mono.error(new InvalidClientException());
                    } else {
                        return Mono.just(entity);
                    }
                });

        return ConversionService.convertFromMono(conversionRequest, externalProviderResponseEntity);
    }

}
