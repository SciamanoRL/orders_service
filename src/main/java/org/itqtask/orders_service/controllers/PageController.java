package org.itqtask.orders_service.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Resource> index() {
        Resource resource = new ClassPathResource("templates/index.html");
        return ResponseEntity.ok(resource);
    }

    @GetMapping(value = "/new_order", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Resource> newOrder() {
        Resource resource = new ClassPathResource("templates/new_order.html");
        return ResponseEntity.ok(resource);
    }

    @GetMapping(value = "/find_order", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Resource> findOrder() {
        Resource resource = new ClassPathResource("templates/find_orders.html");
        return ResponseEntity.ok(resource);
    }
}
