package com.onlym.converter.client;

import com.onlym.converter.exceptions.InvalidClientException;
import com.onlym.converter.model.ConversionRequest;
import com.onlym.converter.model.ConversionResponse;
import com.onlym.converter.model.ExternalProviderResponseEntity;
import com.onlym.converter.service.ConversionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ConversionRateGetterClientDotIo implements ConversionRateGetterClient {

    public static final String BASE_URL = "http://api.exchangeratesapi.io";
    private final WebClient client;

    @Value("${dotioclient.access_key}")
    private String API_KEY;

    public ConversionRateGetterClientDotIo() {
        this.client = WebClient.create(BASE_URL);
    }

    public Mono<ConversionResponse> getConversion(ConversionRequest conversionRequest) throws InvalidClientException {
        Mono<ExternalProviderResponseEntity> externalProviderResponseEntity = this.client
                .get()
                .uri(uriBuilder -> uriBuilder.path("/latest")
                        .queryParam("access_key", API_KEY)
                        .queryParam("base", conversionRequest.getFrom())
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError,
                        clientResponse -> Mono.error(new InvalidClientException()))
                .bodyToMono(ExternalProviderResponseEntity.class)
                .flatMap(this::validateEntity);

        return ConversionService.convertFromMono(conversionRequest, externalProviderResponseEntity);
    }

    public Mono<ExternalProviderResponseEntity> validateEntity(ExternalProviderResponseEntity entity) {
        if (!entity.isSuccess()) {
            return Mono.error(new InvalidClientException());
        } else {
            return Mono.just(entity);
        }
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }

}
