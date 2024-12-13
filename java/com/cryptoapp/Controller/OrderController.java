package com.cryptoapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoapp.entities.Coin;
import com.cryptoapp.entities.Order;
import com.cryptoapp.entities.OrderType;
import com.cryptoapp.entities.User;
import com.cryptoapp.req_res.OrderReq;
import com.cryptoapp.services.CoinService;
import com.cryptoapp.services.UserService;
import com.cryptoapp.services.orderService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private orderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;
    
    
    @PostMapping("/payment")
    public ResponseEntity<Order> payOrderPayment(
        @RequestHeader("Authorization") String jwt,
        @RequestBody OrderReq req

    ) throws Exception{
        User user=userService.findUserProfileByjwt(jwt);
        Coin coin = coinService.findCoinById(req.getCoinId());

        Order processOrder = orderService.processOrder(coin, req.getQuantity(), req.getOrderType(), user);

        return ResponseEntity.ok(processOrder);
       
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(
        @RequestHeader("Authorization") String jwt,
        @PathVariable Long orderId

    ) throws Exception{
        User user=userService.findUserProfileByjwt(jwt);
        Order order = orderService.getOrderById(orderId);

        if(order.getUser().getUserId().equals(user.getUserId())){
            return ResponseEntity.ok(order);

        }

        else{
            throw new Exception("Invalid User");
        }  
    }

    @GetMapping("/allorders")
    public ResponseEntity<List<Order>> getAllOrders(
        @RequestHeader("Authorization") String jwt,
        @RequestParam OrderType orderType
        ) {
            User user=userService.findUserProfileByjwt(jwt);
           

            List<Order> allOrdersOfUser = orderService.getAllOrdersOfUser(user.getUserId(), orderType);
            return ResponseEntity.ok(allOrdersOfUser);
        
    }
    
    

}
