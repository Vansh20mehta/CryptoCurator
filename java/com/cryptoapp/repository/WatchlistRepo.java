package com.cryptoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cryptoapp.entities.User;
import com.cryptoapp.entities.Watchlist;

@Repository
public interface WatchlistRepo extends JpaRepository<Watchlist, Long> {

    @Query("SELECT w FROM Watchlist w WHERE w.user.userId = :userId")
    Watchlist findByUserId(Long userId);
}
