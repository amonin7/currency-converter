package com.onlym.converter.service;

import com.onlym.converter.model.ConversionRequest;
import com.onlym.converter.model.ConversionResponse;
import com.onlym.converter.model.ExternalProviderResponseEntity;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConversionService {

    private static ConversionResponse convert(
            ConversionRequest request,
            ExternalProviderResponseEntity externalProviderResponse) {

        Double rate = externalProviderResponse.getRates().get(request.getTo());
        return ConversionResponse.from(request, getConverted(request.getAmount(), rate));
    }

    private static double getConverted(Double amount, Double rate) {
        BigDecimal bd = BigDecimal.valueOf(amount * rate);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }

    public static Mono<ConversionResponse> convertFromMono(
            ConversionRequest request,
            Mono<ExternalProviderResponseEntity> externalProviderResponse
    ) {
        return externalProviderResponse.map(response -> convert(request, response));
    }


}
