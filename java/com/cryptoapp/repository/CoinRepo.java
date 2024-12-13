package com.cryptoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptoapp.entities.Coin;

public interface CoinRepo extends JpaRepository<Coin,String>{

}
