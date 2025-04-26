package com.pojo;

import com.annotation.Column;
import com.annotation.Id;
import com.annotation.Table;

@Table("product")
public class Product {
    @Id
    @Column(value="product_id",isPrimaryKey=true,autoIncrement = true)
    private Integer productId;

    @Column("user_id")
    private Integer userId;

    @Column("category_id")
    private Integer categoryId;

    @Column("stock")
    private Integer stock;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("price")
    private double price;

    @Column("status")
    private ProductStatus status;

    public enum ProductStatus {
        available,sold,deleted,
        approved, rejected//审核状态
    }

    //getter and setter

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", stock=" + stock +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
