package com.example.app.userimplementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.app.entities.CartItem;
import com.example.app.entities.Product;
import com.example.app.entities.ProductImage;
import com.example.app.entities.User;
import com.example.app.userrepositories.CartRepository;
import com.example.app.userrepositories.ProductImageRepository;
import com.example.app.userrepositories.ProductRepository;
import com.example.app.userrepositories.UserRepository;
import com.example.app.userservices.CartServiceContract;
@Service
public class CartService implements CartServiceContract{
	
	 private final UserRepository userRepository;
	    private final CartRepository cartRepository;
	    private final ProductRepository productRepository;
	    private final ProductImageRepository productImageRepository;

	    public CartService(UserRepository userRepository,
	                       CartRepository cartRepository,
	                       ProductRepository productRepository,
	                       ProductImageRepository productImageRepository) {

	        this.userRepository = userRepository;
	        this.cartRepository = cartRepository;
	        this.productRepository = productRepository;
	        this.productImageRepository = productImageRepository;
	    }



	    public void addToCart(User user, int productId, int quantity) {
	    	Product product = productRepository.findById(productId)
	    	.orElseThrow(()-> new IllegalArgumentException("Product not found with ID:" + productId));
	    	int userId;
	    	// Fetch cart item for this userId and productId
	    	Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(user.getUserId(), productId);
	    	if (existingItem.isPresent()) {
	    		CartItem cartItem = existingItem.get();
	    	cartItem.setQuantity(cartItem.getQuantity() + quantity);
	    	cartRepository.save(cartItem);
	    	} else {
	    		CartItem newItem = new CartItem(user, product, quantity);
	    	cartRepository.save(newItem);
	    	}

	    	}
	
	
	@Override
	// Get Cart Items for a User
	public Map<String, Object> getCartItems(User authenticatedUser) {
		// Fetch the cart items for the user with product details
	List<CartItem> cartItems = cartRepository.findCartitemsWithProductDetails(authenticatedUser.getUserId());
	// Create a response map to hold the cart details
	Map<String, Object> response = new HashMap<>();

	response.put("username", authenticatedUser.getUsername());
	response.put("role", authenticatedUser.getRole().toString());

	// List to hold the product details
	List<Map<String, Object>> products = new ArrayList<>();
	int overallTotalPrice = 0;

	for (CartItem cartItem : cartItems) {
	Map<String, Object> productDetails = new HashMap<>();

	// Get product details
	Product product = cartItem.getProduct();

	// Fetch product images
	List<ProductImage> productlmages = productImageRepository.findByProduct_ProductId(product.getProductId());
	String imageUrl = (productlmages != null && !productlmages.isEmpty())
	? productlmages.get(0).getImageUrl()
	: "default-image-url";

	// Populate product details
	productDetails.put("product_id", product.getProductId());
	productDetails.put("image_url", imageUrl);
	productDetails.put("name", product.getName());
	productDetails.put("description", product.getDescription());
	productDetails.put("price_per_unit", product.getPrice());
	productDetails.put("quantity", cartItem.getQuantity());
	productDetails.put("total_price", cartItem.getQuantity() * product.getPrice().doubleValue());
	
	// Add to products list
	products.add(productDetails);

	// Update overall total price
	overallTotalPrice += cartItem.getQuantity() * product.getPrice().doubleValue();
	}
	// Prepare the final cart response
	Map<String, Object> cart = new HashMap<>();
	cart.put("products", products);
	cart.put("overall_total_price", overallTotalPrice);

	response.put("cart", cart);

	return response;
	}
	
	
	
	@Override
	public void updateCartItemQuantity(User authenticateduser, int productId, int quantity) {
	// TODO Auto-generated method stub
		User user=userRepository.findById(authenticateduser.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));

		Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));

		// Fetch cart item for this userld and productld
		Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(authenticateduser.getUserId(), productId);

		if (existingItem.isPresent()) {
		CartItem cartItem = existingItem.get();
		if (quantity == 0) {
		deleteCartItem(authenticateduser.getUserId(), productId);
		} else {
		cartItem.setQuantity(quantity);
		cartRepository.save(cartItem);
		}
		}
		else
		{
			throw new RuntimeException("Cart Item not found associated with product and user");
		}
		
	}
	
	public void deleteCartItem(int userId, int productId) {

		Product product = productRepository.findById(productId)
		.orElseThrow(() -> new IllegalArgumentException("Product not found"));

		cartRepository.deleteCartItem(userId, productId);
	}



	@Override
	public int getCartItemCount(int userId) {
		int count=cartRepository.countTotalItems(userId);
		return count;
	}
	
	
}