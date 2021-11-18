package com.onlym.converter.service;

import com.onlym.converter.model.ConversionRequest;
import com.onlym.converter.model.ConversionResponse;
import com.onlym.converter.model.ExternalProviderResponseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ConversionServiceTest {

    public static final String FROM = "EUR";
    public static final String TO = "USD";
    public static final double AMOUNT = 123.45;

    private ConversionRequest request;
    private Mono<ExternalProviderResponseEntity> externalProviderResponse;

    @BeforeEach
    void setUp() {
        request = new ConversionRequest(FROM, TO, AMOUNT);
        HashMap<String, Double> rates = new HashMap<>() {{
            put("EUR", 1.);
            put("USD", 1.1);
        }};
        externalProviderResponse = Mono.just(new ExternalProviderResponseEntity(
                FROM,
                LocalDate.now().toString(),
                rates,
                "success",
                true
        ));
    }

    @Test
    void convertFromMono() {
        Mono<ConversionResponse> conversionResponseMono =
                ConversionService.convertFromMono(request, externalProviderResponse);
        StepVerifier.create(conversionResponseMono)
                .consumeNextWith(conversionResponse -> {
                    assertNotNull(conversionResponse);
                    assertEquals(FROM, conversionResponse.getFrom());
                    assertEquals(TO, conversionResponse.getTo());
                    assertEquals(AMOUNT, conversionResponse.getAmount().doubleValue());
                    assertEquals(135.8, conversionResponse.getConverted().doubleValue());
                })
                .verifyComplete();

    }
}