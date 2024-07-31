package com.jabanto.process.order.infrastructure.primary.subscriber;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderReceiveSubscriberAdapter {

    @KafkaListener(topics = "order-receive-topic", groupId = "order-group")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
}
