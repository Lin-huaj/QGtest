package com.pojo;

import com.annotation.Column;
import com.annotation.Id;
import com.annotation.Table;

import java.util.Date;

@Table("review")
public class Review {
    @Id
    @Column(value="review_id",isPrimaryKey=true,autoIncrement = true)
    private Integer reviewId  ;

    @Column("order_id")
    private Integer orderId        ;

    @Column("product_id")
    private Integer productId    ;

    @Column("rating")
    private Integer rating     ;

    @Column("comment")
    private String comment     ;

    @Column("created_at")
    private Date createdAt    ;

    //getter and setter


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
