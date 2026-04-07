package com.example.app.userrepositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.app.entities.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
// Custom query methods can be added here if needed
	@Query("SELECT o FROM Order o WHERE FUNCTION('MONTH', o.createdAt) = :month AND FUNCTION('YEAR', o.createdAt) = :year AND o.status = 'SUCCESS'")
	List<Order> findSuccessfulOrdersByMonthAndYear(@Param("month") int month, @Param("year") int year);

	@Query("SELECT o FROM Order o WHERE FUNCTION('DATE', o.createdAt) = :date AND o.status = 'SUCCESS'")
	List<Order> findSuccessfulOrdersByDate(@Param("date") LocalDate date);

	@Query("SELECT o FROM Order o WHERE FUNCTION('YEAR', o.createdAt) = :year AND o.status = 'SUCCESS'")
	List<Order> findSuccessfulOrdersByYear(@Param("year") int year);

	@Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'SUCCESS'")
	BigDecimal calculateOverallBusiness();

	@Query("SELECT o FROM Order o WHERE o.status = :status")
	List<Order> findAllByStatus(@Param("status") String status);
	
	@Query("SELECT o FROM Order o WHERE o.status ='SUCCESS'")
	List<Order> findAllByStatusOverallBusiness();
}