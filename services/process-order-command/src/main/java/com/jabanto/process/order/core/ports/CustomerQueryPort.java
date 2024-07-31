package com.jabanto.process.order.core.ports;

import com.jabanto.process.order.core.domain.Customer;
import reactor.core.publisher.Mono;

public interface CustomerQueryPort {
    Mono<Customer> Find(String id);
}
