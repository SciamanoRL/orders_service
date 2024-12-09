package org.itqtask.orders_service.repository;

import org.itqtask.orders_service.model.Order;
import org.springframework.data.repository.ListCrudRepository;


import java.sql.Date;
import java.util.List;


public interface OrderRepository extends ListCrudRepository<Order, Long> {

    List<Order> findByOrderDateAndTotalAmountGreaterThan(Date date, long amount);

    List<Order> findOrdersByOrderDateBetween(Date dateFrom, Date dateTo);

}