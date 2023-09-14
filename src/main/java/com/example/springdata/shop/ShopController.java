package com.example.springdata.shop;

import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    @PostMapping("/orders")
    public Order addOrder(@RequestParam List<String> productIds) throws ProductNotAvailableException {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId)
                    .orElseThrow(ProductNotAvailableException::new);
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, Instant.now());

        return orderRepo.addOrder(newOrder);
    }

    @GetMapping("/orders")
    public List<Order> findAllOrders(@RequestParam OrderStatus status) {
        return orderRepo.findAllOrders(status);
    }

    @PutMapping("/orders/{orderId}")
    public void updateOrder(@PathVariable String orderId, @RequestParam OrderStatus newStatus) {
        Order oldOrder = orderRepo.getOrderById(orderId);
        orderRepo.removeOrder(orderId);
        Order newOrder = oldOrder.withStatus(newStatus);
        orderRepo.addOrder(newOrder);
    }
}
