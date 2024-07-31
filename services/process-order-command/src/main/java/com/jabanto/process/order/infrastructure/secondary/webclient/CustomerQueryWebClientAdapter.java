package com.jabanto.process.order.infrastructure.secondary.webclient;

import com.jabanto.process.order.core.domain.Customer;
import com.jabanto.process.order.core.domain.Response;
import com.jabanto.process.order.core.ports.CustomerQueryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CustomerQueryWebClientAdapter implements CustomerQueryPort {

    private final WebClient webClient;

    public CustomerQueryWebClientAdapter(WebClient.Builder webClientBuilder, @Value("${api.customers.url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public Mono<Customer> Find(String id) {
        String query = "{ \"query\": \"{ customer(id: \\\"%s\\\") { id name address } }\" }";

        return webClient.post()
                .uri("/graphql")
                .header("Content-Type", "application/json")
                .bodyValue(String.format(query, id))
                .retrieve()
                .bodyToMono(Response.class)
                .map(response -> response.getData().getCustomer())
                .doOnSubscribe(subscription -> System.out.println("Fetching customer with ID: " + id))
                .doOnError(error -> System.err.println("Failed to fetch customer: " + error.getMessage()));
    }
}
