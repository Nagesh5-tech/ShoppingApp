package com.example.app.usercontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.entities.User;
import com.example.app.userimplementations.CartService;
import com.example.app.userrepositories.UserRepository;



@RestController
@CrossOrigin(origins = "http://localhost:5174", allowCredentials = "true")
@RequestMapping("/api")
public class CartController {
	
	@Autowired
	public UserRepository userRepository;
	@Autowired
	public CartService cartService;
	
	
	
	
	public CartController(UserRepository userRepository, CartService cartService) {
		super();
		this.userRepository = userRepository;
		this.cartService = cartService;
	}




	@PostMapping("/add")
	@CrossOrigin(origins = "http://localhost:5174", allowCredentials = "true")
	public ResponseEntity<Void> addToCart(@RequestBody Map<String, Object> request) {
	String username = (String) request.get("username");
	int productId = (int) request.get("productId");

	// Handle quantity: Default to 1 if not provided
	int quantity = request.containsKey("quantity") ? (int) request.get("quantity") : 1;

	// Fetch the user using username
	User user = userRepository.findByUsername(username)
	.orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

	// Add the product to the cart
	cartService.addToCart(user.getUserId(), productId, quantity);
	return ResponseEntity.status(HttpStatus.CREATED).build();

}
	
}
