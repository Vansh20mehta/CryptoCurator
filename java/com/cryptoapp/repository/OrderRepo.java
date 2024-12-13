package com.cryptoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cryptoapp.entities.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long>{
    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
    List<Order> findByUserId(Long userId);

}
