package com.cryptoapp.services;

import java.util.List;

import com.cryptoapp.entities.Coin;
import com.cryptoapp.entities.Order;
import com.cryptoapp.entities.OrderItem;
import com.cryptoapp.entities.OrderType;
import com.cryptoapp.entities.User;

public interface orderService {
  
    
    Order getOrderById(Long id) throws Exception;

    Order createOrder(User user,OrderItem orderItem,OrderType orderType);
    
    List<Order> getAllOrdersOfUser(Long userId,OrderType orderType);

    Order processOrder(Coin coin,double quantity,OrderType orderType,User user) throws Exception;
    


    
}
