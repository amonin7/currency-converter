package com.onlym.converter.handler;

import com.onlym.converter.model.ConversionRequest;
import com.onlym.converter.model.ConversionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient(timeout = "10000")
public class ConversionHandlerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void standardConversion() {
        Flux<ConversionResponse> conversionResponseMono = webTestClient
                .post()
                .uri("/currency/convert")
                .bodyValue(new ConversionRequest("EUR", "USD", 123.45))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(ConversionResponse.class)
                .getResponseBody();

        StepVerifier.create(conversionResponseMono)
                .expectSubscription()
                .expectNextMatches(conversionResponse ->
                        conversionResponse.getFrom().equals("EUR") &&
                                conversionResponse.getTo().equals("USD") &&
                                conversionResponse.getAmount().equals(123.45))
                .verifyComplete();
    }
}
