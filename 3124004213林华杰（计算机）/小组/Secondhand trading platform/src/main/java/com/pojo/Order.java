package com.pojo;

import com.annotation.Column;
import com.annotation.Id;
import com.annotation.Table;

import java.util.Date;

@Table("order")
public class Order {
    @Id
    @Column(value="order_id",isPrimaryKey=true,autoIncrement = true)
    private Integer orderId;

    @Column("buyer_id")
    private Integer buyer_id;

    @Column("product_id")
    private Integer productId;

    @Column("quantity")
    private Integer quantity;

    @Column("total_price")
    private Double totalPrice;

    @Column("status")
    private OrderStatus status;

    @Column("payment_method")
    private Paymentmethod paymentMethod;

    @Column("created_at")
    private Date createdAt;

    public enum OrderStatus {
        pending, paid, shipped, completed, canceled
    }

    public enum Paymentmethod {
        online, cod
    }

    //getter and setter


    public Integer getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(Integer buyer_id) {
        this.buyer_id = buyer_id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Paymentmethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Paymentmethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", buyer_id=" + buyer_id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", paymentMethod=" + paymentMethod +
                ", createdAt=" + createdAt +
                '}';
    }
}
