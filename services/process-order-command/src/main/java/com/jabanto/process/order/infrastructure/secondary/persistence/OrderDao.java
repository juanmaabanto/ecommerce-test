package com.jabanto.process.order.infrastructure.secondary.persistence;

import com.jabanto.process.order.core.domain.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderDao extends ReactiveMongoRepository<Order, String> {
}
