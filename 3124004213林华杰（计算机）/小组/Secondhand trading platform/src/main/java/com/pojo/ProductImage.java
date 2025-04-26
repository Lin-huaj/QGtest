package com.pojo;

import com.annotation.Column;
import com.annotation.Id;
import com.annotation.Table;

@Table("product_image")
public class ProductImage {
    @Id
    @Column(value="image_id",isPrimaryKey=true,autoIncrement = true)
    private Integer imageId   ;

    @Column("product_id")
    private Integer productId    ;

    @Column("image_url")
    private String imageUrl   ;

    //getter and setter


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "imageId=" + imageId +
                ", productId=" + productId +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
