package org.itqtask.orders_service.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NumberGeneratorServiceImpl implements NumberGeneratorService {

    @Override
    public String getGeneratedOrderNumber() {
        RestClient restClient = RestClient.create();
        return restClient.get().uri("http://localhost:3001/numbers").retrieve().body(String.class);
    }
}
