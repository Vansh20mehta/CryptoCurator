package com.cryptoapp.Controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoapp.entities.Order;
import com.cryptoapp.entities.PaymentOrder;
import com.cryptoapp.entities.User;
import com.cryptoapp.entities.Wallet;
import com.cryptoapp.entities.WalletTransaction;
import com.cryptoapp.services.PaymentService;
import com.cryptoapp.services.UserService;
import com.cryptoapp.services.WalletService;
import com.cryptoapp.services.orderService;
import com.cryptoapp.servicesimpl.JwtService;


@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

   @Autowired
   private PaymentService paymentService;

    @Autowired
    private orderService orderService;

    
   

    @GetMapping("/userwallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByjwt(jwt);

        Wallet userWallet = walletService.getUserWallet(user);
        

        return new ResponseEntity<>(userWallet,HttpStatus.ACCEPTED);
    }


    @PutMapping("/{walletId}/transfer")
    public ResponseEntity<Wallet> walletTowalletTransfer(
        @RequestHeader("Authorization") String jwt,
        @PathVariable Double walletId,
        @RequestBody WalletTransaction request
    ) throws Exception {
        User senderUser = userService.findUserProfileByjwt(jwt);

        Wallet reciverWallet=walletService.getWalletById(walletId);

        Wallet walletTowalletTransfer = walletService.walletTowalletTransfer(senderUser,reciverWallet,request.getAmount());

        return new ResponseEntity<>(walletTowalletTransfer,HttpStatus.ACCEPTED);
       
       
    }

    @PutMapping("order/{orderId}/payment")
    public ResponseEntity<Wallet> payOrderPayment(
        @RequestHeader("Authorization") String jwt,
        @PathVariable Long orderId
       
    ) throws Exception {
        User senderUser = userService.findUserProfileByjwt(jwt);
        Order order = orderService.getOrderById(orderId);

        Wallet payOrderPayment = walletService.payOrderPayment(order, senderUser);
        return new ResponseEntity<>(payOrderPayment,HttpStatus.ACCEPTED);
       
    }

    @PutMapping("/add/money/{paymentId}/{orderId}")
    public ResponseEntity<Wallet> addMoneyToWallet(
        @RequestHeader("Authorization") String jwt,
        @PathVariable Long orderId,
        @PathVariable String paymentId
       
    ) throws Exception {
        User senderUser = userService.findUserProfileByjwt(jwt);

        Wallet userWallet = walletService.getUserWallet(senderUser);

        PaymentOrder paymentOrder = paymentService.getPaymentOrder(orderId);

        Boolean status = paymentService.paymentOrderProcess(paymentOrder,paymentId);

        if(status){
            userWallet = walletService.addBalance(userWallet, paymentOrder.getAmount());
        }

        return new ResponseEntity<>(userWallet,HttpStatus.ACCEPTED);
       
    }






}
