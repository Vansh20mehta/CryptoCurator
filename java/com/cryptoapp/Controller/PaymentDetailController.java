package com.cryptoapp.Controller;

import java.lang.module.ModuleDescriptor.Builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoapp.entities.PaymentDetails;
import com.cryptoapp.entities.User;
import com.cryptoapp.services.PaymentDetailService;
import com.cryptoapp.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/payment")
public class PaymentDetailController {
    @Autowired
    private PaymentDetailService paymentDetailService;

    @Autowired
    private UserService userService;

    @GetMapping("/details")
    public ResponseEntity<PaymentDetails> getUserPaymentDetail(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByjwt(jwt);
        PaymentDetails paymentDetails = paymentDetailService.getPaymentDetails(user);

       return new ResponseEntity<>(paymentDetails,HttpStatus.ACCEPTED);
        
    }

    @PostMapping("/add/details")
    public ResponseEntity<PaymentDetails> addUserPaymentDetail(@RequestHeader("Authorization") String jwt,@RequestBody PaymentDetails paymentDetails) {

      User user = userService.findUserProfileByjwt(jwt);
    
    String accountHolderName = paymentDetails.getAccountHolderName();
    String accountNumber = paymentDetails.getAccountNumber();
    String bankName = paymentDetails.getBankName();
    String ifsc = paymentDetails.getIfsc();

        PaymentDetails paymentDetails2 = paymentDetailService.addPaymentDetails(accountNumber, accountHolderName, bankName, ifsc, user);
        return  new ResponseEntity<>(paymentDetails2,HttpStatus.CREATED);
    }
    
    

}
