package org.itqtask.orders_service.controller;

import org.itqtask.orders_service.model.Order;
//import org.itqtask.orders_service.repository.JdbcOrderRepository;
import org.itqtask.orders_service.services.OrderServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;


@RestController
@RequestMapping("/api")
public class OrderController {

//    @Autowired
//    JdbcOrderRepository jdbcOrderRepository;
//
    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrdersByDateAndTotalAmount(@RequestParam("date") String date,
                                                                     @RequestParam("amount") int amount) {
        List<Order> orders = orderService.findByOrderDateAndTotalAmountGreaterThan(Date.valueOf(date), amount);

        if (!orders.isEmpty()) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    public ResponseEntity<List<Order>> getOrdersExcludeProductFromToDate(@RequestParam("article") long article,
//                                                                         @RequestParam("date_from") Date dateFrom,
//                                                                         @RequestParam("date_to") Date dateTo) {
//        List<Order> orders = jdbcOrderRepository.findByExcludeProductAndTimeInterval(article, dateFrom, dateTo);
//        if (!orders.isEmpty()) {
//            return new ResponseEntity<>(orders, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") long id) {
        Order order = orderService.getOrder(id).orElseThrow();

        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            Order response = orderService.createOrder(order);
            System.out.println(response);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/numbers")
    public String getGeneratedOrderNumber() {
        try {
            return orderService.getGeneratedOrderNumber();
        } catch (Exception e) {
            return "not_generated";
        }
    }


}
