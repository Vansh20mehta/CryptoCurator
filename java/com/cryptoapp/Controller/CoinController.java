package com.cryptoapp.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoapp.entities.Coin;
import com.cryptoapp.services.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/coins")
public class CoinController {

        @Autowired
        private CoinService coinService;

        @Autowired
        private ObjectMapper objectMapper;

        @GetMapping
        public ResponseEntity<List<Coin>> getCoinList(@RequestParam(required = false,name = "page") int page) throws  Exception{
                 List<Coin> coins = coinService.getCoinList(page);
                 return new ResponseEntity<>(coins,HttpStatus.OK);
        }

        @GetMapping("/chart/{coinId}")
        public ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId,@RequestParam("days") int days) throws  Exception{
                String marketChart = coinService.getMarketChart(coinId, days);
                JsonNode jsonNode = objectMapper.readTree(marketChart);
                 return new ResponseEntity<>(jsonNode,HttpStatus.OK);
        }

        @GetMapping("/search")
        public ResponseEntity<JsonNode> search(@RequestParam("keyword") String Keyword) throws  Exception{
        String Searchcoin = coinService.searchCoin(Keyword);
                JsonNode jsonNode = objectMapper.readTree(Searchcoin);
                 return new ResponseEntity<>(jsonNode,HttpStatus.OK);

        }

        @GetMapping("/top50coins")
        public ResponseEntity<JsonNode> top50coins() throws  Exception{
        String top50 = coinService.getTop50CoinsByMarketCap();
                JsonNode jsonNode = objectMapper.readTree(top50);
                 return new ResponseEntity<>(jsonNode,HttpStatus.OK);

        }

        @GetMapping("/trending")
        public ResponseEntity<JsonNode> trending() throws  Exception{
        String trending = coinService.getTrendingCoins();
                JsonNode jsonNode = objectMapper.readTree(trending);
                 return new ResponseEntity<>(jsonNode,HttpStatus.OK);

        }

       
        @GetMapping("/coindetail/{coinId}")
        public ResponseEntity<JsonNode> coindetails(@PathVariable String coinId) throws  Exception{
        String coindetail = coinService.getCoinDetails(coinId);
                JsonNode jsonNode = objectMapper.readTree(coindetail);
                 return new ResponseEntity<>(jsonNode,HttpStatus.OK);

        }

       


        
}
