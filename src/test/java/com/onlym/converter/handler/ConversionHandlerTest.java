package com.onlym.converter.handler;

import com.onlym.converter.model.ConversionRequest;
import com.onlym.converter.model.ConversionResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ConversionHandlerTest {

    public static final String FROM = "EUR";
    public static final String TO = "USD";
    public static final double AMOUNT = 123.45;

    @ExtendWith(SpringExtension.class)
    @SpringBootTest
    @AutoConfigureWebTestClient(timeout = "10000")
    @Nested
    class StandardConversionTest {

        @Autowired
        WebTestClient webTestClient;

        @Test
        public void standardConversion() {
            webTestClient
                    .post()
                    .uri("/currency/convert")
                    .bodyValue(new ConversionRequest(FROM, TO, AMOUNT))
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(ConversionResponse.class)
                    .consumeWith((response) -> {
                        ConversionResponse conversionResponse = response.getResponseBody();
                        assertCorrectConversionResponse(conversionResponse);
                    });
        }
    }

    @ExtendWith(SpringExtension.class)
    @SpringBootTest(properties = {"dotioclient.access_key=wrong-access-key"})
    @AutoConfigureWebTestClient(timeout = "20000")
    @Nested
    class FailureConversionTest {

        @Autowired
        WebTestClient webTestClient;

        @Test
        public void standardConversion() {
            webTestClient
                    .post()
                    .uri("/currency/convert")
                    .bodyValue(new ConversionRequest(FROM, TO, AMOUNT))
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(ConversionResponse.class)
                    .consumeWith((response) -> {
                        ConversionResponse conversionResponse = response.getResponseBody();
                        assertCorrectConversionResponse(conversionResponse);
                    });
        }
    }

    private void assertCorrectConversionResponse(ConversionResponse conversionResponse) {
        assertNotNull(conversionResponse);
        assertEquals(FROM, conversionResponse.getFrom());
        assertEquals(TO, conversionResponse.getTo());
        assertEquals(AMOUNT, conversionResponse.getAmount().doubleValue());
    }
}
