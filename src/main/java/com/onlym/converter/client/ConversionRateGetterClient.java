package com.onlym.converter.client;

import com.onlym.converter.model.ConversionRequest;
import com.onlym.converter.model.ConversionResponse;
import com.onlym.converter.model.ExternalProviderResponseEntity;
import reactor.core.publisher.Mono;

public interface ConversionRateGetterClient {
    Mono<ConversionResponse> getConversion(ConversionRequest conversionRequest);
    Mono<ExternalProviderResponseEntity> validateEntity(ExternalProviderResponseEntity entity);
}
