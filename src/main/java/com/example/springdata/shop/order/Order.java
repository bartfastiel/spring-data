package com.example.springdata.shop.order;

import com.example.springdata.shop.product.Product;
import lombok.With;

import java.time.Instant;
import java.util.List;

@With
public record Order(
        String id,
        List<Product> products,
        OrderStatus status,
        Instant orderTime
) {
}
