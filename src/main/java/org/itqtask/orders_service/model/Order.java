package org.itqtask.orders_service.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;
import java.util.Set;

@Table(name = "orders")
public class Order {

    @Id
    private Long id;

    @Column(value = "order_number")
    private String orderNumber;

    @Column(value = "total_amount")
    private Long totalAmount;

    @Column(value = "order_date")
    private Date orderDate;

    @Column(value = "customer_name")
    private String customerName;

    @Column(value = "address")
    private String deliveryAddress;

    @Column(value = "payment_type")
    private String paymentType;

    @Column(value = "delivery_type")
    private String deliveryType;

    @MappedCollection(idColumn = "order_no")
    private Set<DetailOrder> detailOrders;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNo='" + orderNumber + '\'' +
                ", totalAmount=" + totalAmount +
                ", orderDate='" + orderDate + '\'' +
                ", customerName='" + customerName + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", deliveryType='" + deliveryType + '\'' +
                ", detailOrders=" + detailOrders +
                '}';
    }

    public Order(Long id, String orderNumber, Long totalAmount, Date orderDate, String customerName, String deliveryAddress, String paymentType, String deliveryType, Set<DetailOrder> detailOrders) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
        this.paymentType = paymentType;
        this.deliveryType = deliveryType;
        this.detailOrders = detailOrders;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Set<DetailOrder> getDetailOrders() {
        return detailOrders;
    }

    public void setDetailOrders(Set<DetailOrder> detailOrders) {
        this.detailOrders = detailOrders;
    }

    public void addDetailOrder(DetailOrder detailOrder) {
        this.detailOrders.add(detailOrder);
    }

}
