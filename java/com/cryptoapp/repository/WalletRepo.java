package com.cryptoapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cryptoapp.entities.Wallet;

public interface WalletRepo  extends JpaRepository<Wallet,Double> {
        @Query("SELECT w FROM Wallet w WHERE w.user.userId = :userId")
        Wallet findByUserId(Long userId);

 


     

       
}
