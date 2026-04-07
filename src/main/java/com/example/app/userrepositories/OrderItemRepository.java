package com.example.app.userrepositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.app.entities.OrderItem;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
@Query("SELECT oi FROM OrderItem oi WHERE oi.order.orderId = :orderId")
List<OrderItem> findByOrderId(String orderId);
@Query("SELECT oi FROM OrderItem oi WHERE oi.order.userId = :userId AND oi.order.status = 'SUCCESS'")
List<OrderItem> findSuccessfulOrderItemsByUserId(@Param("userId") int userId);
}
