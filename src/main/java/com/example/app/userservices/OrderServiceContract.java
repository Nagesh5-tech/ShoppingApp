package com.example.app.userservices;

import java.util.Map;

import com.example.app.entities.User;

public interface OrderServiceContract {
	public Map<String, Object> getOrdersForUser(User user);		// Fetch all successful order items for the user

}
