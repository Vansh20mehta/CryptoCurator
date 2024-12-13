package com.cryptoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cryptoapp.entities.PaymentDetails;

public interface PaymentDetailRepo extends JpaRepository<PaymentDetails,Long> {

    @Query("SELECT p FROM PaymentDetails p WHERE p.user.userId = :userId")
    PaymentDetails findByUserId(Long userId);

}
