package com.cryptoapp.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptoapp.entities.Asset;
import com.cryptoapp.entities.Coin;
import com.cryptoapp.entities.User;
import com.cryptoapp.repository.AssetRepo;
import com.cryptoapp.services.AssetService;

@Service
public class AssetServiceImpl implements AssetService{
    @Autowired
    private AssetRepo assetRepo;

    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        Asset asset = new Asset();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());

        return assetRepo.save(asset);
        
    }

    @Override
    public Asset getAssetById(Long assetId) {
        return assetRepo.findById(assetId).orElse(null);
        
    }

    @Override
    public Asset getAssetByUserIdAndAssetId(String userId, Long assetId) {
        return null;
        
    }

   

    @Override
    public Asset updateAsset(Long assetId, double quantity) {
        Asset asset = getAssetById(assetId);
        asset.setQuantity(quantity+asset.getQuantity());
        return assetRepo.save(asset);
        
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetRepo.deleteById(assetId);
        
    }

    @Override
    public List<Asset> getUserAssets(Long userId) {
        return assetRepo.findByUserId(userId);
      
    }

    @Override
    public Asset getAssetByUserIdAndCoinId(Long userId,String coinId) {
      return assetRepo.findByUserIdAndCoinId(userId,coinId);
    }

        
}
