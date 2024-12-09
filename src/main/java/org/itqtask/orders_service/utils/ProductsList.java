package org.itqtask.orders_service.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ProductsList {

    private final List<Product> PRODUCT_LIST = new ArrayList<>();

    {
        PRODUCT_LIST.add(new Product("Зеркало 100х80мм", 9100322L, 3300));
        PRODUCT_LIST.add(new Product("Раковина 80х45мм", 7670154L, 5600));
        PRODUCT_LIST.add(new Product("Смеситель цвет хром", 1958148L, 1700));
    }

    public Product getProduct(Long article) {
        return PRODUCT_LIST.stream().filter(product -> Objects.equals(product.getArticle(), article)).findFirst().orElse(new Product());
    }

    public List<Product> getPRODUCT_LIST() {
        return PRODUCT_LIST;
    }
}
