package com.cryptoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cryptoapp.entities.Asset;

@Repository
@EnableJpaRepositories
public interface AssetRepo extends JpaRepository<Asset,Long> {

     @Query("SELECT a FROM Asset a WHERE a.user.userId = :userId")
    List<Asset> findByUserId(@Param("userId") Long userId);


    @Query("SELECT a FROM Asset a WHERE a.coin.coinId = :coinId and a.user.userId= :userId")
    Asset findByUserIdAndCoinId(@Param("userId") Long userId,@Param("coinId") String coinId);

}
