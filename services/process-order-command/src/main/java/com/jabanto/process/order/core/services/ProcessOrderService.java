package com.jabanto.process.order.core.services;

import com.jabanto.process.order.core.ProcessOrderUseCase;
import com.jabanto.process.order.core.domain.Order;
import com.jabanto.process.order.core.domain.Product;
import com.jabanto.process.order.core.ports.OrderPersistencePort;
import com.jabanto.process.order.core.ports.ProductQueryPort;
import com.jabanto.process.order.infrastructure.exceptions.IntegrationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessOrderService implements ProcessOrderUseCase {

    private final ProductQueryPort productQueryPort;
    private final OrderPersistencePort orderPersistencePort;

    public ProcessOrderService(OrderPersistencePort orderPersistencePort, ProductQueryPort productQueryPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.productQueryPort = productQueryPort;
    }

    @Override
    public Mono<Void> handle(Order order) throws IntegrationException {
        return completeOrder(order)
                .then(orderPersistencePort.save(order))
                .doOnSubscribe(subscription -> System.out.println("Processing order: " + order))
                .doOnTerminate(() -> System.out.println("Order processed: " + order));
    }

    public Mono<Void> completeOrder(Order order) {
        // Obtenemos información de los productos
        List<String> productIds = order.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        return Mono.zip(productIds.stream()
                        .map(productId -> productQueryPort.Find(productId)
                                .map(product -> {
                                    order.getProducts().stream()
                                            .filter(p -> p.getId().equals(product.getId()))
                                            .forEach(p -> {
                                                p.setName(product.getName());
                                                p.setPrice(product.getPrice());
                                            });
                                    return product;
                                })
                                .subscribeOn(Schedulers.boundedElastic()))
                        .collect(Collectors.toList()),
                products -> {
                    double totalPrice = order.getProducts().stream()
                            .mapToDouble(p -> p.getPrice() * p.getQuantity())
                            .sum();
                    order.setTotal(totalPrice);
                    return Mono.empty();
                }).then();
    }
}
