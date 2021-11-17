package com.onlym.converter.client;

import com.onlym.converter.exceptions.InvalidClientException;
import com.onlym.converter.model.ConversionRequest;
import com.onlym.converter.model.ConversionResponse;
import com.onlym.converter.model.ExternalProviderResponseEntity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ConversionRateGetterClientDotIoTest {

    public static final String FROM = "EUR";
    public static final String TO = "USD";
    public static final double AMOUNT = 123.45;
    @Autowired
    private ConversionRateGetterClientDotIo TOT;

    @Nested
    class ValidateEntityTest {

        @Test
        public void validateSuccessTest() {
            assertNotNull(TOT);
            ExternalProviderResponseEntity entity =
                    new ExternalProviderResponseEntity("base", "date", new HashMap<>(), null, true);
            Mono<ExternalProviderResponseEntity> validatedMono = TOT.validateEntity(entity);
            StepVerifier.create(validatedMono)
                    .consumeNextWith((validatedEntity) -> assertEquals(entity, validatedEntity))
                    .verifyComplete();
        }

        @Test
        public void validateUnSuccessTest() {
            ExternalProviderResponseEntity entity =
                    new ExternalProviderResponseEntity("base", "date", new HashMap<>(), null, false);
            Mono<ExternalProviderResponseEntity> validatedMono = TOT.validateEntity(entity);
            verifyErrorMono(validatedMono);
        }
    }

    @Nested
    class GetConversionTest {

        @Nested
        class SimpleConversionTest {
            @Test
            public void correctWorkTest() {
                Mono<ConversionResponse> responseMono = TOT.getConversion(new ConversionRequest(FROM, TO, AMOUNT));
                StepVerifier.create(responseMono)
                        .consumeNextWith(conversionResponse -> {
                            assertEquals(FROM, conversionResponse.getFrom());
                            assertEquals(TO, conversionResponse.getTo());
                            assertEquals(AMOUNT, conversionResponse.getAmount().doubleValue());
                        })
                        .verifyComplete();
            }
        }

        @Nested
        class WrongAuthTest {
            @Test
            public void wrongAuthErrorCatching() {
                TOT.setAPI_KEY("wrong_api_key");
                Mono<ConversionResponse> responseMono = TOT.getConversion(new ConversionRequest(FROM, TO, AMOUNT));
                verifyErrorMono(responseMono);
            }
        }

        @Nested
        class WrongCurrencyTest {
            @Test
            public void wrongCurrencyErrorCatching() {
                Mono<ConversionResponse> responseMono = TOT.getConversion(new ConversionRequest(FROM, TO, AMOUNT));
                verifyErrorMono(responseMono);
            }
        }

    }

    private void verifyErrorMono(Mono<?> responseMono) {
        StepVerifier.create(responseMono)
                .expectError(InvalidClientException.class)
                .verify();
    }
}
