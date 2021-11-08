package com.onlym.converter.client;

import com.onlym.converter.model.ConversionRequest;
import com.onlym.converter.model.ConversionResponse;
import reactor.core.publisher.Mono;

public interface ConversionRateGetterClient {
    Mono<ConversionResponse> getConversion(ConversionRequest conversionRequest);
}
