package com.example.app.usercontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.DTOs.CartRequest;
import com.example.app.DTOs.UpdateCartRequest;
import com.example.app.entities.User;
import com.example.app.userimplementations.CartService;
import com.example.app.userrepositories.UserRepository;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;



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
	public ResponseEntity<Void> addToCart(
	        @RequestBody Map<String, Object> request,
	        HttpServletRequest req) {

	    User user = (User) req.getAttribute("authenticatedUser");

	    // Safe extraction
	    int productId = ((Number) request.get("productId")).intValue();

	    // Ternary with safety
	    int quantity = request.containsKey("quantity")
	            ? ((Number) request.get("quantity")).intValue()
	            : 1;

	    cartService.addToCart(user, productId, quantity);

	    return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	
	// Fetch all cart items for the user (based on username)
	@GetMapping("/items")
	@CrossOrigin(origins = "http://localhost:5174", allowCredentials = "true")
	public ResponseEntity<Map<String, Object>> getCartItems(HttpServletRequest request) {
	// Fetch user by username to get the userId
	User user = (User) request.getAttribute("authenticatedUser");

	// Call the service to get cart items for the user
	Map<String, Object> response = cartService.getCartItems(user);
	return ResponseEntity.ok(response);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Void> updateCartItemQuantity(@RequestBody UpdateCartRequest request,HttpServletRequest req) {
		String username =request.getUsername();
	    User user = (User) req.getAttribute("authenticatedUser");

	    if (user == null) {
	        throw new RuntimeException("User not authenticated");
	    }

	    int productId = request.getProductId();
	    int quantity = request.getQuantity();
	    System.out.println("USER: " + user);
	    System.out.println("PRODUCT ID: " + productId);
	    System.out.println("QUANTITY: " + quantity);
	    cartService.updateCartItemQuantity(user, productId, quantity);

	    return ResponseEntity.ok().build();
	}
	
	
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteCartItem(@RequestBody Map<String, Object> request,HttpServletRequest req) {

	int productId = (int) request.get("productId");

	// Fetch the Authenticated user using user
	 User user = (User) req.getAttribute("authenticatedUser");

	// Delete the cart item
	cartService.deleteCartItem(user.getUserId(),productId);
	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	// Fetch userId from username coming from the filter and get cart item count
	@GetMapping("/items/count")
	public ResponseEntity<Integer> getCartItenCount(@RequestParam String username,HttpServletRequest req) {
	
		// Fetch the Authenticated user using user
		 User user = (User) req.getAttribute("authenticatedUser");
		

	// Call the service to get the total cart item count
	int count = cartService.getCartItemCount(user.getUserId());
	return ResponseEntity. ok(count);
	}
	
	
	
}
