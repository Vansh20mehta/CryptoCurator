package com.cryptoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cryptoapp.entities.Withdrawl;
import java.util.List;



@Repository
public interface WithdrawlRepo extends JpaRepository<Withdrawl, Long> {
    @Query("SELECT w FROM Withdrawl w WHERE w.user.userId = :userId")
    List<Withdrawl> findByUserId(Long userId );
}
