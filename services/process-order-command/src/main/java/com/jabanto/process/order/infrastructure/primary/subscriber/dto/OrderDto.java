package com.jabanto.process.order.infrastructure.primary.subscriber.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private String date;
    private String customerID;
    private List<ProductDto> products;

    @Data
    public static class ProductDto {
        private String id;
        private Integer quantity;
    }
}
