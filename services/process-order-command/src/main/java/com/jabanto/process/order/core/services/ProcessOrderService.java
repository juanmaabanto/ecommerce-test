package com.jabanto.process.order.core.services;

import com.jabanto.process.order.core.ProcessOrderUseCase;
import com.jabanto.process.order.core.domain.Order;
import com.jabanto.process.order.core.ports.OrderPersistencePort;
import com.jabanto.process.order.infrastructure.exceptions.IntegrationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProcessOrderService implements ProcessOrderUseCase {

    private final OrderPersistencePort orderPersistencePort;

    public ProcessOrderService(OrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public Mono<Void> handle(Order order) throws IntegrationException {
        return completeOrder(order)
                .then(orderPersistencePort.save(order))
                .doOnSubscribe(subscription -> System.out.println("Processing order: " + order))
                .doOnTerminate(() -> System.out.println("Order processed: " + order));
    }

    public Mono<Void> completeOrder(Order order) {
        // Aqui hay q conectarse concurrentemente a las apis de producto y customer para completar la informacion

        return Mono.empty();
    }
}
