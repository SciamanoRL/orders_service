package org.itqtask.orders_service.services;

import org.itqtask.orders_service.model.Order;
import org.itqtask.orders_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        try {
            order.setOrderNumber(getGeneratedOrderNumber());
            order.setOrderDate(Date.valueOf(LocalDate.now()));
            order.setTotalAmount(order.getDetailOrders().stream().mapToLong(detail -> detail.getProductPrice() * detail.getProductAmount()).sum());
            System.out.println(orderRepository.save(order));
            return orderRepository.save(order);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Optional<Order> getOrder(long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findByOrderDateAndTotalAmountGreaterThan(Date date, long amount) {
        return orderRepository.findByOrderDateAndTotalAmountGreaterThan(date, amount);
    }

    @Override
    public String getGeneratedOrderNumber() {
        RestClient restClient = RestClient.create();
        return restClient.get().uri("http://localhost:3001/numbers").retrieve().body(String.class);

    }


}
