package com.cryptoapp.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private WalletTransactionType transactionType;

    @ManyToOne
    private Wallet wallet;

    private LocalDate date;
    private Long amount;
     private String transferid;
     private String purpose;




}
