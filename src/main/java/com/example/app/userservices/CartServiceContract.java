package com.example.app.userservices;

import java.util.Map;

public interface CartServiceContract {
	
	public void addToCart(int userId, int productId, int quantity);
	public Map<String,Object> getCartItems(int userId);

}
