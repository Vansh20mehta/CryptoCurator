package com.cryptoapp.servicesimpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cryptoapp.entities.Asset;
import com.cryptoapp.entities.Coin;
import com.cryptoapp.entities.Order;
import com.cryptoapp.entities.OrderItem;
import com.cryptoapp.entities.OrderStaus;
import com.cryptoapp.entities.OrderType;
import com.cryptoapp.entities.User;
import com.cryptoapp.repository.OrderItemRepo;
import com.cryptoapp.repository.OrderRepo;
import com.cryptoapp.services.AssetService;
import com.cryptoapp.services.WalletService;
import com.cryptoapp.services.orderService;

@Service
public class OrderServiceImpl implements orderService{

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private WalletService walletService;

    @Autowired
    private AssetService assetService;

    @Override
    public Order getOrderById(Long id) throws Exception {
            return orderRepo.findById(id).orElseThrow(()->new Exception("order not found"));
    }

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price=orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();

        Order order=new Order();
        order.setOrderItem(orderItem);
        order.setUser(user);
        order.setOrderStaus(OrderStaus.PENDING);
        order.setOrderType(orderType);
        order.setLdt(LocalDateTime.now());
        order.setPrice(BigDecimal.valueOf(price));

          return orderRepo.save(order);
    }
   

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType) {
        return  orderRepo.findByUserId(userId);
        
    }

    private OrderItem createOrderItem(Coin coin,double quantity,double buyPrice,double sellPrice){
        OrderItem orderItem=new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setQuantity(quantity);
        orderItem.setSellPrice(sellPrice);
         
        return orderItemRepo.save(orderItem);
    }

    @Transactional
    public Order buyAsset(Coin coin,double quantity,User user) throws Exception{
        double buyPrice=coin.getCurrentPrice();
        OrderItem orderItem=createOrderItem(coin, quantity,buyPrice ,0);

        Order order=createOrder(user, orderItem,OrderType.BUY);
        orderItem.setOrder(order);
        
        walletService.payOrderPayment(order, user);

        order.setOrderStaus(OrderStaus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order saveOrder = orderRepo.save(order);


        //create asset
        Asset oldAsset = assetService.getAssetByUserIdAndCoinId(order.getUser().getUserId(),order.getOrderItem().getCoin().getCoinId());

        if (oldAsset==null) {
            assetService.createAsset(user,orderItem.getCoin(), orderItem.getQuantity());
        }
        else{
            assetService.updateAsset(oldAsset.getAssetId(), quantity);
        }

        return saveOrder;


    }


    @Transactional
    public Order sellAsset(Coin coin,double quantity,User user) throws Exception{
        double sellPrice=coin.getCurrentPrice();

        Asset assetTosell=assetService.getAssetByUserIdAndCoinId(user.getUserId(),coin.getCoinId());
        
        double buyPrice=assetTosell.getBuyPrice();   
        if(assetTosell!=null){
          
              
        OrderItem orderItem=createOrderItem(coin, quantity,buyPrice,sellPrice);
     

        Order order=createOrder(user, orderItem,OrderType.SELL);
        orderItem.setOrder(order);
        
        
        walletService.payOrderPayment(order, user);
        order.setOrderStaus(OrderStaus.SUCCESS);
        order.setOrderType(OrderType.SELL);
        Order saveOrder = orderRepo.save(order);

        //update asset
        Asset updatedAsset = assetService.updateAsset(assetTosell.getAssetId(), -quantity);
        if (updatedAsset.getQuantity()*coin.getCurrentPrice()<=1) {
            assetService.deleteAsset(updatedAsset.getAssetId());
            
        }

        return saveOrder;

        }
       throw new Exception("indufficent quantity to sell");
    }

    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
     if (orderType.equals(OrderType.BUY)) {
        return buyAsset(coin, quantity, user);
     }
     else if (orderType.equals(OrderType.SELL)) {
        return sellAsset(coin, quantity, user);
     }
    throw new Exception("invalid order type!");
    }



  

}
