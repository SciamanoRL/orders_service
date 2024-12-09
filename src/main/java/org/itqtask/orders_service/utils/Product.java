package org.itqtask.orders_service.utils;


public class Product {

    private String name;
    private Long article;
    private int price;

    public Product(String name, Long article, int price) {
        this.name = name;
        this.article = article;
        this.price = price;
    }

    public Product() {

    }

    public String getName() {
        return name;
    }

    public Long getArticle() {
        return article;
    }

    public int getPrice() {
        return price;
    }
}
