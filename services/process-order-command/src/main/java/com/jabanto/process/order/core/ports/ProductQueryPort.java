package com.jabanto.process.order.core.ports;

import com.jabanto.process.order.core.domain.Product;
import reactor.core.publisher.Mono;

public interface ProductQueryPort {
    Mono<Product> Find(String id);
}
