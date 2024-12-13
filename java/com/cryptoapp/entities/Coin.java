package com.cryptoapp.entities;



import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Coin {

    @Id
    @JsonProperty("id")
    private String coinId ;

    private String symbol ;
    private String name ;
    private String image ;

    
    @Column(name = "current_price")
    private Double currentPrice;
    
    @Column(name = "market_cap")
    private Double marketCap;
    
    @Column(name = "market_cap_rank")
    private Integer marketCapRank;
    
    @Column(name = "fully_diluted_valuation")
    private Double fullyDilutedValuation;
    
    @Column(name = "total_volume")
    private Double totalVolume;
    
    @Column(name = "high_24h")
    private Double high24h;
    
    @Column(name = "low_24h")
    private Double low24h;
    
    @Column(name = "price_change_24h")
    private Double priceChange24h;
    
    @Column(name = "price_change_percentage_24h")
    private Double priceChangePercentage24h;
    
    @Column(name = "market_cap_change_24h")
    private Double marketCapChange24h;
    
    @Column(name = "market_cap_change_percentage_24h")
    private Double marketCapChangePercentage24h;
    
    @Column(name = "circulating_supply")
    private Double circulatingSupply;
    
    @Column(name = "total_supply")
    private Double totalsupply;
    @Column(name = "max_supply")
    private Double maxSupply;
    
    @Column(name = "ath")
    private Double ath;
    
    @Column(name = "ath_change_percentage")
    private Double athChangePercentage;
    
    @Column(name = "ath_date")
    private LocalDateTime athDate;
    
    @Column(name = "atl")
    private Double atl;
    
    @Column(name = "atl_change_percentage")
    private Double atlChangePercentage;
    
    @Column(name = "atl_date")
    private LocalDateTime atlDate;
    
    @Column(name = "roi")
    @JsonIgnore
    private String roi;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    
}

