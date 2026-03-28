package com.example.app.DTOs;

public class CartRequest {
    private String username;
    private int productId;
    private int quantity;
	public CartRequest(String username, int productId, int quantity) {
		super();
		this.username = username;
		this.productId = productId;
		this.quantity = quantity;
	}
	
	// getters & setters
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
    
    
    

    
}
