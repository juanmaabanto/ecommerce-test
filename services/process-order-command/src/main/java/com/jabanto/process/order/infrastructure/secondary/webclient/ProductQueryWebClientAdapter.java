package com.jabanto.process.order.infrastructure.secondary.webclient;

import com.jabanto.process.order.core.domain.Product;
import com.jabanto.process.order.core.ports.ProductQueryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductQueryWebClientAdapter implements ProductQueryPort {

    private final WebClient webClient;

    public ProductQueryWebClientAdapter(WebClient.Builder webClientBuilder, @Value("${api.products.url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public Mono<Product> Find(String id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnSubscribe(subscription -> System.out.println("Fetching product with ID: " + id))
                .doOnError(error -> System.err.println("Failed to fetch product: " + error.getMessage()));
    }
}
