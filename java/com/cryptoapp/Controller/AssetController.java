package com.cryptoapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoapp.entities.Asset;
import com.cryptoapp.entities.User;
import com.cryptoapp.services.AssetService;
import com.cryptoapp.services.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/asset")
@Slf4j
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private UserService userService;


    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId){
        Asset asset = assetService.getAssetById(assetId);
        return ResponseEntity.ok(asset);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Asset>> getAllAssets (@RequestHeader("Authorization") String jwt, @PathVariable Long userId){
        List<Asset> assets = assetService.getUserAssets(userId);
        
        return ResponseEntity.ok(assets);
        
    }

    @GetMapping("coin/{coinId}/user")
    public ResponseEntity<Asset> getAssetByUserAndCoin(@RequestHeader("Authorization") String jwt,@PathVariable String coinId) {
        User user = userService.findUserProfileByjwt(jwt);
        Asset asset = assetService.getAssetByUserIdAndCoinId(user.getUserId(),coinId);
        return ResponseEntity.ok().body(asset);
    }
    
    @GetMapping("/test")
    public String testing(){
        return "testing";
    }

  


     
        


}
