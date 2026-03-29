package com.example.app.DTOs;

import java.util.Objects;

public class CartRequest {

    private String username;
    private int productId;
    private int quantity;

    // Default constructor (VERY IMPORTANT for Spring Boot / JSON)
    public CartRequest() {
    }

    // Parameterized constructor
    public CartRequest(String username, int productId, int quantity) {
        this.username = username;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Optional but good practice
    @Override
    public int hashCode() {
        return Objects.hash(username, productId, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartRequest that = (CartRequest) o;
        return productId == that.productId &&
               quantity == that.quantity &&
               Objects.equals(username, that.username);
    }
}