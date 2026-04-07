package com.example.app.userimplementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.app.entities.OrderItem;
import com.example.app.entities.Product;
import com.example.app.entities.ProductImage;
import com.example.app.entities.User;
import com.example.app.userrepositories.OrderItemRepository;
import com.example.app.userrepositories.ProductImageRepository;
import com.example.app.userrepositories.ProductRepository;
import com.example.app.userservices.OrderServiceContract;
@Service
public class OrderService implements OrderServiceContract{

	
	
	private OrderItemRepository orderItemRepository;

	
	private ProductRepository productRepository;

	
	private ProductImageRepository productImageRepository;


	public OrderService(OrderItemRepository orderItemRepository, ProductRepository productRepository,
			ProductImageRepository productImageRepository) {
		super();
		this.orderItemRepository = orderItemRepository;
		this.productRepository = productRepository;
		this.productImageRepository = productImageRepository;
	}
	
	/**
	 * Fetches all successful orders for a given user and returns the required response format.
	 *
	 * @param user The authenticated user object.
	 * @return A map containing the user's role, username, and ordered products.
	 */
	public Map<String, Object> getOrdersForUser(User user) {

	    // Fetch all successful order items for the user
	    List<OrderItem> orderItems =
	            orderItemRepository.findSuccessfulOrderItemsByUserId(user.getUserId());

	    // Prepare response
	    Map<String, Object> response = new HashMap<>();
	    response.put("username", user.getUsername());
	    response.put("role", user.getRole());

	    List<Map<String, Object>> products = new ArrayList<>();

	    for (OrderItem item : orderItems) {

	        Product product = productRepository
	                .findById(item.getProductId())
	                .orElse(null);

	        if (product == null) {
	            continue;
	        }

	        // Fetch product images
	        List<ProductImage> images =
	                productImageRepository.findByProduct_ProductId(product.getProductId());

	        String imageUrl = images.isEmpty()
	                ? null
	                : images.get(0).getImageUrl();

	        // Build product details
	        Map<String, Object> productDetails = new HashMap<>();
	        productDetails.put("order_id", item.getOrder().getOrderId());
	        productDetails.put("quantity", item.getQuantity());
	        productDetails.put("total_price", item.getTotalPrice());
	        productDetails.put("image_url", imageUrl);
	        productDetails.put("product_id", product.getProductId());
	        productDetails.put("name", product.getName());
	        productDetails.put("description", product.getDescription());
	        productDetails.put("price_per_unit", item.getPricePerUnit());

	        products.add(productDetails);
	    }

	    // Add products list to response
	    response.put("products", products);

	    return response;
	}
	
}
