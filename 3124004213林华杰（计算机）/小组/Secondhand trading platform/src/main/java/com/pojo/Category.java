package com.pojo;

import com.annotation.Column;
import com.annotation.Id;
import com.annotation.Table;

@Table("category")
public class Category {
    @Id
    @Column(value="category_id",isPrimaryKey=true,autoIncrement = true)
    private Integer categoryId ;

    @Column("name")
    private String name         ;

    //getter and setter


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}
