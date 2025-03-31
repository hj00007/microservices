package com.edureka.ecomm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edureka.ecomm.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

	@Query("SELECT o FROM Orders o WHERE o.customer_id = :customerId")
	List<Orders> findOrdersByCustomerId(@Param("customerId") String customerId);

}
