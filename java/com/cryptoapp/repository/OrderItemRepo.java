package com.cryptoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cryptoapp.entities.Order;
import com.cryptoapp.entities.OrderItem;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
    Order findByUserId(Long userId);

}
