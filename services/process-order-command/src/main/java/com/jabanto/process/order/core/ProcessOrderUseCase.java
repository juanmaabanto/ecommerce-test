package com.jabanto.process.order.core;

import com.jabanto.process.order.core.domain.Order;
import com.jabanto.process.order.infrastructure.exceptions.IntegrationException;
import reactor.core.publisher.Mono;

public interface ProcessOrderUseCase {
    Mono<Void> handle(Order order) throws IntegrationException;
}
