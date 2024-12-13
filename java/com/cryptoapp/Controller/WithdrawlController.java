package com.cryptoapp.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoapp.entities.User;
import com.cryptoapp.entities.Wallet;
import com.cryptoapp.entities.Withdrawl;
import com.cryptoapp.services.UserService;
import com.cryptoapp.services.WalletService;
import com.cryptoapp.services.WithdrawlService;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawlController {

    @Autowired
    private WithdrawlService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;


    @PostMapping("/{amount}")
    public ResponseEntity<?> withdrawalRequest(
        @RequestHeader("Authorization") String jwt,
        @PathVariable Long amount

    ) {
        // Check if user is authenticated
        User user = userService.findUserProfileByjwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);
      
        Withdrawl withdrawl = withdrawalService.requestWithdrawl(amount, user);

        walletService.addBalance(userWallet, -withdrawl.getAmount() );

        return ResponseEntity.ok("Withdrawal request sent");
    }


   @PatchMapping("admin/{id}/amount")
   public ResponseEntity<Withdrawl> proceedWithdrawal(
    @RequestHeader("Authorization") String jwt,
    @PathVariable Long id,
    @PathVariable boolean accept
    ) throws Exception {
      User user = userService.findUserProfileByjwt(jwt);

       
      Withdrawl withdrawl = withdrawalService.proceedWithdrawl(id, accept);

      Wallet userWallet = walletService.getUserWallet(user);

      if (!accept) {
        walletService.addBalance(userWallet, withdrawl.getAmount());        
      }

      return new ResponseEntity<>(withdrawl,HttpStatus.OK);
   }

   @GetMapping("/history")
   public ResponseEntity<List<Withdrawl>> getWithdrawalHistory(
    @RequestHeader("Authorization") String jwt

   ) throws Exception{
    User user = userService.findUserProfileByjwt(jwt);

    List<Withdrawl> withdrawlHistory = withdrawalService.getUserWithdrawlHistory(user);

       return new ResponseEntity<>(withdrawlHistory,HttpStatus.OK) ;
   }
   
   @GetMapping
   public ResponseEntity<List<Withdrawl>> getAllWithdrawalRequest(
    @RequestHeader("Authorization") String jwt
  ) throws Exception{
    User user = userService.findUserProfileByjwt(jwt);

    List<Withdrawl> Allwithdrawl = withdrawalService.getAllWithdrawlRequest();

       return new ResponseEntity<>(Allwithdrawl,HttpStatus.OK) ;
   }
   





}
