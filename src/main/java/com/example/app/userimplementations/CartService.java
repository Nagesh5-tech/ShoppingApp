package com.example.app.userimplementations;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.app.entities.CartItem;
import com.example.app.entities.Product;
import com.example.app.entities.User;
import com.example.app.userrepositories.CartRepository;
import com.example.app.userrepositories.ProductRepository;
import com.example.app.userrepositories.UserRepository;
import com.example.app.userservices.CartServiceContract;
@Service
public class CartService implements CartServiceContract{
	
	public UserRepository userRepository;
	public CartRepository cartRepository;
	public ProductRepository productRepository;

	

	public CartService(UserRepository userRepository, CartRepository cartRepository,
			ProductRepository productRepository) {
		super();
		this.userRepository = userRepository;
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
	}



	@Override
			public void addToCart(int userId, int productId, int quantity) {
				// TODO Auto-generated method stub
				User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

						Product product = productRepository.findById(productId)
						.orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

						// Fetch cart item for this userld and productld
						Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(userId, productId);

						if (existingItem.isPresent()) {
						CartItem cartitem = existingItem.get();
						cartitem.setQuantity(cartitem.getQuantity() + quantity);
						cartRepository.save(cartitem);
						} else {
						CartItem newItem = new CartItem(user, product, quantity);
						cartRepository.save(newItem);
						}
				
			}
			
}