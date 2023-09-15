package com.example.springdata.shop.order;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepo extends MongoRepository<Order, String> {

    Order getOrderById(String id);

    void removeOrderBy(String id);

    List<Order> findAllBy(OrderStatus status);
}
