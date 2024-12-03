package org.itqtask.orders_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;



@Table(name = "products")
public class DetailOrder {

    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "product_article")
    private Long productArticle;

    @Column(value = "product_name")
    private String productName;

    @Column(value = "product_amount")
    private int productAmount;

    @Column(value = "product_price")
    private int productPrice;

    @Column("order_id")
    private Long orderId;

    @Override
    public String toString() {
        return "DetailOrder{" +
                "id=" + id +
                ", productArticle=" + productArticle +
                ", productName='" + productName + '\'' +
                ", productAmount=" + productAmount +
                ", productPrice=" + productPrice +
                ", orderId=" + orderId +
                '}';
    }


    public DetailOrder(Long id, Long productArticle, String productName, int productAmount, int productPrice, Long orderId) {
        this.id = id;
        this.productArticle = productArticle;
        this.productName = productName;
        this.productAmount = productAmount;
        this.productPrice = productPrice;
        this.orderId = orderId;
    }

    public DetailOrder() {

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductArticle() {
        return productArticle;
    }

    public void setProductArticle(Long productArticle) {
        this.productArticle = productArticle;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
