package com.cryptoapp.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptoapp.entities.Coin;
import com.cryptoapp.entities.User;
import com.cryptoapp.entities.Watchlist;
import com.cryptoapp.repository.WatchlistRepo;
import com.cryptoapp.services.WatchlistService;

@Service
public class WatchlistImpl implements WatchlistService{

    @Autowired
    private WatchlistRepo watchlistRepo;


    @Override
    public Watchlist getWatchlist(Long userId) throws Exception {
           Watchlist watchlist = watchlistRepo.findByUserId(userId);
           if(watchlist==null){
           
            throw new Exception("watchlist not found");
           }
           return watchlist;
    }

    @Override
    public Watchlist createWatchlist(User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        return watchlistRepo.save(watchlist);
        
    }

    @Override
    public Watchlist getById(Long watchlistId) {
        return watchlistRepo.findById(watchlistId).orElse(null);
       
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) throws Exception {
        Watchlist watchlist =  getWatchlist(user.getUserId()); 
        if(watchlist==null){
            watchlist = createWatchlist(user);
            }
            watchlist.getCoins().add(coin);
             watchlistRepo.save(watchlist);

             return coin;
        
    }


}
