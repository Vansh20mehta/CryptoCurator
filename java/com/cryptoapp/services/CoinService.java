package com.cryptoapp.services;

import java.util.List;
import java.util.Optional;

import com.cryptoapp.entities.Coin;

public interface CoinService {
   
    public List<Coin> getCoinList(int page) throws Exception;

    public Coin  findCoinById(String coinId) throws Exception;

    public String searchCoin(String keyword) throws Exception;

    public String getTop50CoinsByMarketCap() throws Exception;

    public String getTrendingCoins() throws Exception;

    public String getMarketChart(String coinId, int days) throws Exception;

    public String getCoinDetails(String coinId) throws Exception;
}
