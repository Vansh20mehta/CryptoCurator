package com.cryptoapp.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity 
public class Withdrawl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private WithdrawlStatus withdrawlStatus;

    private Long amount;

    private LocalDateTime date=LocalDateTime.now();

    @ManyToOne
    private User user;



}
