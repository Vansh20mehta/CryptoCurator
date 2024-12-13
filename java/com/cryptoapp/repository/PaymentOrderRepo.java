package com.cryptoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptoapp.entities.PaymentOrder;

public interface PaymentOrderRepo extends JpaRepository<PaymentOrder,Long> {

    
}
