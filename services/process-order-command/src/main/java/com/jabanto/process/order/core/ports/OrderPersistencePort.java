package com.jabanto.process.order.core.ports;

import com.jabanto.process.order.core.domain.Order;
import reactor.core.publisher.Mono;

public interface OrderPersistencePort {
    Mono<Void> save(Order order);
}
