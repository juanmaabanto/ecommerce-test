package com.jabanto.process.order.infrastructure.primary.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jabanto.process.order.core.ProcessOrderUseCase;
import com.jabanto.process.order.core.domain.Customer;
import com.jabanto.process.order.core.domain.Order;
import com.jabanto.process.order.core.domain.Product;
import com.jabanto.process.order.infrastructure.primary.subscriber.dto.OrderDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderReceiveSubscriberAdapter {

    private final ProcessOrderUseCase processOrderUseCase;
    private final ObjectMapper objectMapper;

    public OrderReceiveSubscriberAdapter(ProcessOrderUseCase processOrderUseCase, ObjectMapper objectMapper) {
        this.processOrderUseCase = processOrderUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "order-receive-topic", groupId = "order-group")
    public void listen(String message) {
        try {
            Order order = deserializeMessage(message);

            Mono<Void> processingResult = processOrderUseCase.handle(order);

            processingResult.subscribe(
                    (Void) -> System.out.println("Order processed successfully."),
                    error -> {
                        System.err.println("Failed to process order: " + error.getMessage());
                        error.printStackTrace();
                    }
            );
        } catch (Exception e) {
            System.err.println("Failed to process order: " + e.getMessage());
        }
    }

    private Order deserializeMessage(String message) throws JsonProcessingException {
        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);

        Order order = new Order();
        order.setDate(orderDto.getDate());

        Customer customer = new Customer();
        customer.setId(orderDto.getCustomerID());
        order.setCustomer(customer);

        List<Product> products = orderDto.getProducts().stream().map(dto -> {
            Product product = new Product();
            product.setId(dto.getId());
            product.setQuantity(dto.getQuantity());
            return product;
        }).collect(Collectors.toList());
        order.setProducts(products);

        return order;
    }
}
