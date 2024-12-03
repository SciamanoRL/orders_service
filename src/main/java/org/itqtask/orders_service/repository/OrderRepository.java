package org.itqtask.orders_service.repository;

import org.itqtask.orders_service.model.DetailOrder;
import org.itqtask.orders_service.model.Order;
import org.springframework.data.repository.ListCrudRepository;


import java.sql.Date;
import java.util.List;

//public interface OrderRepository {
//
//    Order createOrder(Order order);
//
//    Order findById(Long id);
//
//    List<Order> findByDateAndSumMoreThanTotalAmount(Date date, int totalAmount);
//
//    List<Order> findByExcludeProductAndTimeInterval(long article, Date dateFrom, Date dateTo);
//
//
//}

public interface OrderRepository extends ListCrudRepository<Order, Long> {

    List<Order> findByOrderDateAndTotalAmountGreaterThan(Date date, long amount);


}