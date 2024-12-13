package com.cryptoapp.servicesimpl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.cryptoapp.entities.Coin;
import com.cryptoapp.repository.CoinRepo;
import com.cryptoapp.services.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CoinServiceimpl implements CoinService{
    @Autowired
    private  CoinRepo coinRepo;

    @Autowired
    private ObjectMapper objectMapper;
   

    @Override
    public List<Coin> getCoinList(int page) throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&page_size="+page;
        RestTemplate restTemplate=new RestTemplate();
        try {
            HttpHeaders headers=new HttpHeaders();

            HttpEntity<String> httpEntity=new HttpEntity<String>("parameters",headers);
        
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            List<Coin> coinlist=objectMapper.readValue(response.getBody(), new TypeReference<List<Coin>>(){});
           
            return coinlist;
            
        } catch (HttpClientErrorException | HttpServerErrorException | JsonProcessingException e) {
           throw new Exception(e.getMessage());
        }
    }


    @Override
    public Coin findCoinById(String coinId) throws Exception {
       
    Optional<Coin> coin=coinRepo.findById(coinId);
    if(coin.isEmpty())throw new Exception("coin is not present");
        return coin.get();
    }


    @Override
    public String searchCoin(String keyword) throws Exception {
        String url="https://api.coingecko.com/api/v3/search?query="+keyword;
        RestTemplate restTemplate=new RestTemplate();
        try {
            HttpHeaders headers=new HttpHeaders();

            HttpEntity<String> httpEntity=new HttpEntity<String>("parameters",headers);
        
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
           
            return response.getBody();
            
        } catch (HttpClientErrorException | HttpServerErrorException e) {
          throw new Exception(e.getMessage());
        }
     }


    @Override
    public String getTop50CoinsByMarketCap() throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&page_size=50&page=1";
        RestTemplate restTemplate=new RestTemplate();
        try {
            HttpHeaders headers=new HttpHeaders();

            HttpEntity<String> httpEntity=new HttpEntity<String>("parameters",headers);
        
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
           
            return response.getBody();
            
        } catch (HttpClientErrorException | HttpServerErrorException e) {
          throw new Exception(e.getMessage());
        }
    }


    @Override
    public String getTrendingCoins() throws Exception {
        String url="https://api.coingecko.com/api/v3/search/trending";
        RestTemplate restTemplate=new RestTemplate();
        try {
            HttpHeaders headers=new HttpHeaders();

            HttpEntity<String> httpEntity=new HttpEntity<String>("parameters",headers);
        
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
           
            return response.getBody();
            
        } catch (HttpClientErrorException | HttpServerErrorException e) {
          throw new Exception(e.getMessage());
        }  
    }



    @Override
    public String getMarketChart(String coinId, int days) throws Exception{
        String url="https://api.coingecko.com/api/v3/coins/"+coinId+"/market_chart?vs_currency=usd&days="+days;
        RestTemplate restTemplate=new RestTemplate();
        try {
            HttpHeaders headers=new HttpHeaders();

            HttpEntity<String> httpEntity=new HttpEntity<String>("parameters",headers);
        
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
           
            return response.getBody();
            
        } catch (HttpClientErrorException | HttpServerErrorException e) {
          throw new Exception(e.getMessage());
        }

      }


    @Override
    public String getCoinDetails(String coinId) throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/"+coinId;
        RestTemplate restTemplate=new RestTemplate();
        try {
            HttpHeaders headers=new HttpHeaders();

            HttpEntity<String> httpEntity=new HttpEntity<String>("parameters",headers);
        
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            JsonNode jsonNode=objectMapper.readTree(response.getBody());
            Coin coin=new Coin();
            coin.setCoinId(jsonNode.get("id").asText());
            coin.setName(jsonNode.get("name").asText());
            coin.setSymbol(jsonNode.get("symbol").asText());
            coin.setImage(jsonNode.get("image").get("large").asText());

            JsonNode marketdata=jsonNode.get("market_data");
            coin.setCurrentPrice(marketdata.get("current_price").get("usd").asDouble());
            coin.setMarketCap(marketdata.get("market_cap").get("usd").asDouble());
            coin.setTotalsupply(marketdata.get("total_supply").asDouble());
            coin.setHigh24h(marketdata.get("high_24h").get("usd").asDouble());
            coin.setLow24h(marketdata.get("low_24h").get("usd").asDouble());
            coin.setPriceChange24h(marketdata.get("price_change_24h").asDouble());
            coin.setPriceChangePercentage24h(marketdata.get("price_change_percentage_24h").asDouble());
            coin.setMarketCapRank(marketdata.get("market_cap_rank").asInt());
            coin.setMarketCapChange24h(marketdata.get("market_cap_change_24h").asDouble());
            coin.setMarketCapChangePercentage24h(marketdata.get("market_cap_change_percentage_24h").asDouble());
            coin.setTotalsupply(marketdata.get("total_supply").asDouble());

            
            coinRepo.save(coin);
           
            return response.getBody();
            
        } catch (HttpClientErrorException | HttpServerErrorException e) {
          throw new Exception(e.getMessage());
        }   }

}
