package com.jabanto.process.order.core.services;

import com.jabanto.process.order.core.ProcessOrderUseCase;
import com.jabanto.process.order.core.domain.Customer;
import com.jabanto.process.order.core.domain.Order;
import com.jabanto.process.order.core.domain.Product;
import com.jabanto.process.order.core.ports.CustomerQueryPort;
import com.jabanto.process.order.core.ports.OrderPersistencePort;
import com.jabanto.process.order.core.ports.ProductQueryPort;
import com.jabanto.process.order.infrastructure.exceptions.IntegrationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessOrderService implements ProcessOrderUseCase {

    private final CustomerQueryPort customerQueryPort;
    private final ProductQueryPort productQueryPort;
    private final OrderPersistencePort orderPersistencePort;

    public ProcessOrderService(CustomerQueryPort customerQueryPort,
                               OrderPersistencePort orderPersistencePort,
                               ProductQueryPort productQueryPort) {
        this.customerQueryPort = customerQueryPort;
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

    // completa la orden obteniendo información de los productos para calcular el total de la orden,
    // tambien obtiene el nombre y dirección del cliente
    public Mono<Void> completeOrder(Order order) {
        // Obtenemos los id's de productos
        List<String> productIds = order.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        //Obtenemos información del cliente
        Mono<Customer> customerMono = customerQueryPort.Find(order.getCustomer().getId());

        // Obtener la información de los productos
        Flux<Product> productsFlux = Flux.fromIterable(productIds)
                .flatMap(productId -> productQueryPort.Find(productId)
                        .doOnNext(product -> order.getProducts().stream()
                                .filter(p -> p.getId().equals(product.getId()))
                                .forEach(p -> {
                                    p.setName(product.getName());
                                    p.setPrice(product.getPrice());
                                }))
                        .subscribeOn(Schedulers.boundedElastic())
                );

        return Mono.zip(
                customerMono,
                productsFlux.collectList()
        )
                .map(tuple -> {
                    Customer customer = tuple.getT1();
                    List<Product> products = tuple.getT2();
                    order.setCustomer(customer);

                    double totalPrice = order.getProducts().stream()
                            .mapToDouble(p -> p.getPrice() * p.getQuantity())
                            .sum();
                    order.setTotal(totalPrice);

                    return Mono.empty();
                })
                .then();
    }
}
