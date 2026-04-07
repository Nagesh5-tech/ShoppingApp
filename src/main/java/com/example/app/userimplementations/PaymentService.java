package com.example.app.userimplementations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.app.entities.CartItem;
import com.example.app.entities.Order;
import com.example.app.entities.OrderItem;
import com.example.app.entities.OrderStatus;
import com.example.app.userrepositories.CartRepository;
import com.example.app.userrepositories.OrderItemRepository;
import com.example.app.userrepositories.OrderRepository;
import com.example.app.userservices.PaymentServiceContract;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.transaction.Transactional;
@Service
public class PaymentService implements PaymentServiceContract{
	
	@Value("${razorpay.key_id}")
	private String razorpayKeyId;

	@Value("${razorpay.key_secret}")
	private String razorpayKeySecret;
	
	
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final CartRepository cartRepository;
	
	
	
	public PaymentService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
			CartRepository cartRepository) {
		super();
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.cartRepository = cartRepository;
	}

	@Override
	public String createOrder(int userId, BigDecimal totalAmount, List<OrderItem> cartltems) throws RazorpayException {
		// TODO Auto-generated method stub
		// Create Razorpay client
		RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

		// Prepare Razorpay order request
		var orderRequest = new JSONObject();
		orderRequest.put("amount", totalAmount.multiply(BigDecimal.valueOf(100)).intValue()); // Amount in paise
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

		// Create Razorpay order
		com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);

		// Save order details in the database
		Order order = new Order();
		order.setOrderId(razorpayOrder.get("id"));
		order.setUserId(userId);
		order.setTotalAmount(totalAmount);
		order.setStatus(OrderStatus.PENDING);
		order.setCreatedAt(LocalDateTime.now());
		orderRepository.save(order);

		return razorpayOrder.get("id");
	}

	@Override
	public boolean verifyPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature,int userId) {
		// TODO Auto-generated method stub
		 try {
		        // Prepare signature validation attributes
		        JSONObject attributes = new JSONObject();
		        attributes.put("razorpay_order_id", razorpayOrderId);
		        attributes.put("razorpay_payment_id", razorpayPaymentId);
		        attributes.put("razorpay_signature", razorpaySignature);

		        // Verify Razorpay signature
		        boolean isSignatureValid =
		                com.razorpay.Utils.verifyPaymentSignature(attributes, razorpayKeySecret);

		        if (isSignatureValid) {

		            // Fetch order
		            Order order = orderRepository.findById(razorpayOrderId)
		                    .orElseThrow(() -> new RuntimeException("Order not found"));

		            // Update order
		            order.setStatus(OrderStatus.SUCCESS);
		            order.setUpdatedAt(LocalDateTime.now());
		            orderRepository.save(order);

		            // Fetch cart items
		            List<CartItem> cartItems =
		                    cartRepository.findCartitemsWithProductDetails(userId);

		            // Save order items
		            for (CartItem cartItem : cartItems) {

		                OrderItem orderItem = new OrderItem();

		                orderItem.setOrder(order);
		                orderItem.setProductId(cartItem.getProduct().getProductId());
		                orderItem.setQuantity(cartItem.getQuantity());
		                orderItem.setPricePerUnit(cartItem.getProduct().getPrice());

		                BigDecimal total = cartItem.getProduct()
		                        .getPrice()
		                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

		                orderItem.setTotalPrice(total);

		                orderItemRepository.save(orderItem);
		            }

		            // Clear cart AFTER loop
		            cartRepository.deleteAllCartItemsByUserId(userId);

		            return true;

		        } else {
		            return false;
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		        return false;
		    }
	}
	
	@Transactional
	public void saveOrderItems(String orderId, List<OrderItem> items) {
	Order order = orderRepository.findById(orderId)
	.orElseThrow(() -> new RuntimeException("Order not found"));
	for (OrderItem item : items) {
	item.setOrder(order);
	orderItemRepository.save(item);
	}
	}
	

}
