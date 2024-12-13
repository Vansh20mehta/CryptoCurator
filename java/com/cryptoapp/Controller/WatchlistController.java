package com.cryptoapp.Controller;

import java.net.CacheResponse;
import java.nio.file.WatchService;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cryptoapp.entities.Coin;
import com.cryptoapp.entities.User;
import com.cryptoapp.entities.Watchlist;
import com.cryptoapp.services.CoinService;
import com.cryptoapp.services.UserService;
import com.cryptoapp.services.WatchlistService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/watchlist")
@Slf4j
public class WatchlistController {

    @Autowired
    private UserService userService;

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private CoinService coinService;


    @GetMapping
    public ResponseEntity<Watchlist> getUserWatchlist(@RequestHeader("Authorization") String jwt) throws Exception {
       
        User user = userService.findUserProfileByjwt(jwt);
       Watchlist watchlist = watchlistService.getWatchlist(user.getUserId());

       return new ResponseEntity<>(watchlist,HttpStatus.ACCEPTED);
    }



    @GetMapping("/{watchlistId}")
    public ResponseEntity<Watchlist> getAllWatchlist(@PathVariable Long watchlistId) throws Exception {

        Watchlist watchlist = watchlistService.getById(watchlistId);
        return new ResponseEntity<>(watchlist, HttpStatus.ACCEPTED);
       
    }
    


    @PostMapping("/create")
    public ResponseEntity<Watchlist> createWatchlist(@RequestHeader("Authorization") String jwt) {
       
        User user = userService.findUserProfileByjwt(jwt);
        Watchlist watchlist = watchlistService.createWatchlist(user);
        return  ResponseEntity.status(HttpStatus.CREATED).body(watchlist);
    }
    

    @PatchMapping("/add/{coinId}")
    public ResponseEntity<Coin> addItemToWatchlist(
        @RequestHeader("Authorization") String jwt,
        @PathVariable String coinId 
    ) throws Exception{
        
        User user = userService.findUserProfileByjwt(jwt);

        Coin coin = coinService.findCoinById(coinId);
        
        
        
        Coin watchlistCoin = watchlistService.addItemToWatchlist(coin, user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(watchlistCoin);
        
    }

  
}
