package com.cryptoapp.services;

import com.cryptoapp.entities.PaymentMethod;
import com.cryptoapp.entities.PaymentOrder;
import com.cryptoapp.entities.User;
import com.cryptoapp.req_res.PaymentResponse;
import com.razorpay.RazorpayException;

public interface PaymentService {
   
PaymentOrder createPaymentOrder(Long amount,User user,PaymentMethod paymentMethod);
 
PaymentOrder getPaymentOrder(Long paymentOrderId);

Boolean paymentOrderProcess(PaymentOrder paymentOrder,String paymentOrderId) throws Exception;

PaymentResponse createRazorpayLink(User user,Long amount) throws RazorpayException;


}
