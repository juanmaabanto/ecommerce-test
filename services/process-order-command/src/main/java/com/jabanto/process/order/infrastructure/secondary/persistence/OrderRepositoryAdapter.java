package com.jabanto.process.order.infrastructure.secondary.persistence;

import com.jabanto.process.order.core.domain.Order;
import com.jabanto.process.order.core.ports.OrderPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryAdapter implements OrderPersistencePort {

    private final OrderDao orderDao;

    @Autowired
    public OrderRepositoryAdapter(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void save(Order order) {
        this.orderDao.save(order).subscribe();
    }
}
