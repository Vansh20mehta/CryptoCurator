package com.cryptoapp.services;

import java.util.List;

import com.cryptoapp.entities.Coin;
import com.cryptoapp.entities.User;
import com.cryptoapp.entities.Watchlist;

public interface WatchlistService {
    Watchlist getWatchlist(Long userId) throws Exception;

    Watchlist createWatchlist(User user);

    Watchlist getById(Long watchlistId);

    Coin addItemToWatchlist(Coin coin,User user) throws Exception;
    

}
