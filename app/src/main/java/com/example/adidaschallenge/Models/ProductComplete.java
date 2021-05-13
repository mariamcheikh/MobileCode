package com.example.adidaschallenge.Models;

public class ProductComplete {
    private String id;
    private String name;
    private String description;
    private String currency;
    private int price;
    private String img;

    public ProductComplete(String id, String name, String description, String currency, int price, String img) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currency = currency;
        this.price = price;
        this.img = img;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
