//package org.itqtask.orders_service;
//
//import org.itqtask.orders_service.model.DetailOrder;
//import org.itqtask.orders_service.model.Order;
//import org.itqtask.orders_service.services.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.sql.Date;
//import java.util.List;
//
//@Component("actionDemo")
//public class Action implements CommandLineRunner {
//    @Autowired
//    OrderService orderService;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        orderService.createOrder(
//                new Order(null,
//                        "gene",
//                        12000L,
//                        Date.valueOf("2012-12-12"),
//                        "Andrew Kram",
//                        "st peter",
//                        "nal",
//                        "sam",
//                        List.of(
//                                new DetailOrder(null,
//                                        12151L,
//                                        "Morojja", 4, 1200,
//                                        null),
//                                new DetailOrder(null,
//                                        12151L,
//                                        "Kell", 4, 1200,
//                                        null),
//                                new DetailOrder(null,
//                                        12151L,
//                                        "Molla", 4, 1200,
//                                        null)
//                        )
//                )
//        );
//    }
//}
