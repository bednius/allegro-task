package com.allegro.task.config;

import com.allegro.task.exception.GitApiException;
import com.allegro.task.gitapi.response.GitApiErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(@Value("${gitapi.url}") String gitApiUrl) {
        return WebClient.builder()
                .baseUrl(gitApiUrl)
                .filter(ExchangeFilterFunction.ofResponseProcessor(this::renderApiErrorResponse))
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(1024 * 1024 * 16))
                        .build())
                .build();
    }

    private Mono<ClientResponse> renderApiErrorResponse(ClientResponse clientResponse) {
        if (clientResponse.statusCode().isError()) {
            return clientResponse.bodyToMono(GitApiErrorResponse.class)
                    .flatMap(apiErrorResponse -> Mono.error(new GitApiException(
                            clientResponse.statusCode(),
                            apiErrorResponse.getMessage()
                    )));
        }
        return Mono.just(clientResponse);
    }
}

