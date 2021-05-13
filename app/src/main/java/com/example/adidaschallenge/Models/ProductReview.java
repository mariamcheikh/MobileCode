package com.example.adidaschallenge.Models;

public class ProductReview {
    private String productId;
    private String locale;
    private int rating;
    private String text;


    public ProductReview(String productId, String locale, int rating, String text) {
        this.productId = productId;
        this.locale = locale;
        this.rating = rating;
        this.text = text;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
