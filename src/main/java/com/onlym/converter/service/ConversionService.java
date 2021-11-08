package com.onlym.converter.service;

import com.onlym.converter.model.ConversionRequest;
import com.onlym.converter.model.ConversionResponse;
import com.onlym.converter.model.ExternalProviderResponseEntity;
import reactor.core.publisher.Mono;

public class ConversionService {

    private static ConversionResponse convert(
            ConversionRequest request,
            ExternalProviderResponseEntity externalProviderResponse) {

        Double rate = externalProviderResponse.getRates().get(request.getTo());
        return ConversionResponse.from(request, rate);
    }

    public static Mono<ConversionResponse> convertFromMono(
            ConversionRequest request,
            Mono<ExternalProviderResponseEntity> externalProviderResponse
    ) {
        return externalProviderResponse.map(response -> convert(request, response));
    }
}
