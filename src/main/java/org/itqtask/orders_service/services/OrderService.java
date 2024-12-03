package org.itqtask.orders_service.services;

import org.itqtask.orders_service.model.Order;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order createOrder(Order order);
    Optional<Order> getOrder(long id);
    List<Order> findAll();
    List<Order> findByOrderDateAndTotalAmountGreaterThan(Date date, long amount);
    String getGeneratedOrderNumber();
}
