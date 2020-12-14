package com.example.meromall.model;

public class Category {
    private String categoryImageUrl;
    private String categoryName;

    public Category(String categoryImageUrl, String categoryName) {
        this.categoryImageUrl = categoryImageUrl;
        this.categoryName = categoryName;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
