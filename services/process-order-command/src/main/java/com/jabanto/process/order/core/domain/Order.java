package com.jabanto.process.order.core.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collation = "orders")
public class Order {
    @Id
    private String id;
    private String date;
    private Double total;
    private Customer customer;
    private List<Product> products;
}
