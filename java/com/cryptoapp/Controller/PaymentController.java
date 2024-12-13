package com.cryptoapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoapp.entities.PaymentMethod;
import com.cryptoapp.entities.PaymentOrder;
import com.cryptoapp.entities.User;
import com.cryptoapp.req_res.PaymentResponse;
import com.cryptoapp.services.PaymentService;
import com.cryptoapp.services.UserService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @PostMapping("/{amount}")
    public ResponseEntity<PaymentResponse> makePayment( 
        @RequestHeader("Authorization") String jwt,
         @PathVariable Long amount
            ) throws Exception{
            // Get user from JWT token
            User user = userService.findUserProfileByjwt(jwt);
            // Make payment using payment service
            PaymentOrder paymentOrder = paymentService.createPaymentOrder(amount, user, PaymentMethod.RAZORPAY);
            // Return payment response
            PaymentResponse razorpayLink = paymentService.createRazorpayLink(user, amount);
            return ResponseEntity.ok(razorpayLink);
       
        

    }
    
}
