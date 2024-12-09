package org.itqtask.orders_service.controllers;

import org.itqtask.orders_service.model.Order;
//import org.itqtask.orders_service.repository.JdbcOrderRepository;
import org.itqtask.orders_service.services.NumberGeneratorService;
import org.itqtask.orders_service.services.OrderServiceImpl;

import org.itqtask.orders_service.utils.Product;
import org.itqtask.orders_service.utils.ProductsList;
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

    @Autowired
    private NumberGeneratorService numberGeneratorService;

    @Autowired
    private ProductsList productsList;

    @RequestMapping(value = "/orders", params = {"date", "amount"}, method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getOrdersByDateAndTotalAmount(@RequestParam("date") String date,
                                                                     @RequestParam("amount") int amount) {

        List<Order> orders = orderService.findByOrderDateAndTotalAmountGreaterThan(Date.valueOf(date), amount);

        if (!orders.isEmpty()) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/orders", params = {"date_from", "date_to", "article"}, method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getOrdersExcludeProductFromToDate(@RequestParam("date_from") String dateFrom,
                                                                         @RequestParam("date_to") String dateTo,
                                                                         @RequestParam("article") Long article) {
        List<Order> orders = orderService.findOrdersByOrderDateBetween(Date.valueOf(dateFrom), Date.valueOf(dateTo), article);
        if (!orders.isEmpty()) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productsList.getPRODUCT_LIST(), HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") long id) {
        Order order = orderService.getOrder(id).orElseThrow();

        return new ResponseEntity<>(order, HttpStatus.OK);
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
            return numberGeneratorService.getGeneratedOrderNumber();
        } catch (Exception e) {
            return "not_generated";
        }
    }


}
