package com.example.th2;

import java.io.Serializable;

public class Product implements Serializable {
    private String productId;
    private String name;
    private String category; // Áo, Quần, Phụ kiện
    private double price;
    private int stockQuantity;
    private float rating;

    public Product(String productId, String name, String category, double price, int stockQuantity, float rating) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.rating = rating;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getImageResource() {
        switch (category) {
            case "Áo":
                return R.drawable.ao;
            case "Quần":
                return R.drawable.quan;
            case "Phụ kiện":
                return R.drawable.thatlung;
            default:
                return R.drawable.ao;
        }
    }

    public static String generateProductId(String category, int count) {
        String prefix;
        switch (category) {
            case "Áo":
                prefix = "A";
                break;
            case "Quần":
                prefix = "Q";
                break;
            case "Phụ kiện":
                prefix = "P";
                break;
            default:
                prefix = "A";
        }
        return prefix + String.format("%03d", count + 1);
    }
}

