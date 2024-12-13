package com.cryptoapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;

    private String accountNumber;

    private String accountHolderName;

    private String bankName;

    private String ifsc;
  
    @OneToOne
    private User user;

}
