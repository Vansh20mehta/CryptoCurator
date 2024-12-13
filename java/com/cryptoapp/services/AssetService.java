package com.cryptoapp.services;

import java.util.List;

import com.cryptoapp.entities.Asset;
import com.cryptoapp.entities.Coin;
import com.cryptoapp.entities.User;

public interface AssetService {
    
    Asset createAsset(User user,Coin coin,double quantity);

    Asset getAssetById(Long assetId);

    Asset getAssetByUserIdAndAssetId(String userId,Long assetId);
    
     Asset getAssetByUserIdAndCoinId(Long userId ,String coinId);

    Asset updateAsset(Long assetId,double quantity);

    void deleteAsset(Long assetId);

    List<Asset> getUserAssets(Long userId);


}
