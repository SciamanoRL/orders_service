package org.itqtask.orders_service.services;

import org.itqtask.orders_service.model.DetailOrder;
import org.itqtask.orders_service.model.Order;
import org.itqtask.orders_service.repository.OrderRepository;
import org.itqtask.orders_service.utils.ProductsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final NumberGeneratorService numberGeneratorService;
    private final ProductsList productsList;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, NumberGeneratorService numberGeneratorService, ProductsList productsList) {
        this.orderRepository = orderRepository;
        this.numberGeneratorService = numberGeneratorService;
        this.productsList = productsList;
    }

    @Override
    public Order createOrder(Order order) {
        order.setOrderNumber(numberGeneratorService.getGeneratedOrderNumber());
        order.setOrderDate(Date.valueOf(LocalDate.now()));
        for (DetailOrder detailOrder : order.getDetailOrders()) {
            detailOrder.setProductPrice(productsList.getProduct(detailOrder.getProductArticle()).getPrice());
            detailOrder.setProductName(productsList.getProduct(detailOrder.getProductArticle()).getName());
        }
        order.setTotalAmount(order.getDetailOrders().stream().mapToLong(detail -> (long) detail.getProductPrice() * detail.getProductAmount()).sum());
        return orderRepository.save(order);
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
    public List<Order> findOrdersByOrderDateBetween(Date dateFrom, Date dateTo, Long article) {
        List<Order> orders = orderRepository.findOrdersByOrderDateBetween(dateFrom, dateTo);
        return orders.stream().
                filter(order -> order.getDetailOrders().stream()
                        .noneMatch(detailOrder -> Objects.equals(detailOrder.getProductArticle(), article)))
                .toList();
    }


}
