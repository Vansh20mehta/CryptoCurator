package com.cryptoapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentOrderId;

    private Long amount;

    private PaymentStatus paymentOrderStatus;

    private PaymentMethod paymentMethod;
    
    @ManyToOne
    private User user;





}
