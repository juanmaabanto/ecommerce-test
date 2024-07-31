package com.jabanto.process.order.core.ports;

import com.jabanto.process.order.core.domain.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderPersistencePort {
    void save(Order order);
}
