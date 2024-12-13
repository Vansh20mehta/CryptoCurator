package com.cryptoapp.servicesimpl;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cryptoapp.entities.PaymentMethod;
import com.cryptoapp.entities.PaymentOrder;
import com.cryptoapp.entities.PaymentStatus;
import com.cryptoapp.entities.User;
import com.cryptoapp.repository.PaymentOrderRepo;
import com.cryptoapp.req_res.PaymentResponse;
import com.cryptoapp.services.PaymentService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentOrderRepo paymentRepo;

    @Value("")
    private String API_KEY;

    @Value("")
    private String SECRET_KEY;

    @Override
    public PaymentOrder createPaymentOrder(Long amount, User user, PaymentMethod paymentMethod) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setAmount(amount);
        paymentOrder.setUser(user);
        paymentOrder.setPaymentMethod(paymentMethod);

        return paymentRepo.save(paymentOrder);
        

       
    }

    @Override
    public PaymentOrder getPaymentOrder(Long paymentOrderId) {
        PaymentOrder payment = paymentRepo.findById(paymentOrderId).orElse(null);    
        return payment;
    }

    @Override
    public Boolean paymentOrderProcess(PaymentOrder paymentOrder, String paymentOrderId) throws RazorpayException {
      if(paymentOrder.getPaymentOrderStatus().equals(PaymentStatus.PENDING)){
        if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
            RazorpayClient razorpayClient=new RazorpayClient(API_KEY, SECRET_KEY);
            Payment payment=razorpayClient.payments.fetch(paymentOrderId);

            Integer amount = payment.get("amount");
            String status = payment.get("status");
            if(status.equals("captured")){
                paymentOrder.setPaymentOrderStatus(PaymentStatus.SUCCESS);
                return true;
            }

            paymentOrder.setPaymentOrderStatus(PaymentStatus.FAILED);
            paymentRepo.save(paymentOrder);
            return false;
        }
        paymentOrder.setPaymentOrderStatus(PaymentStatus.SUCCESS);
        paymentRepo.save(paymentOrder);
        return true;
      }
      return false;
    }

    @Override
    public PaymentResponse createRazorpayLink(User user, Long amount) throws RazorpayException {
        Long Amount =amount*100;

        try {
            RazorpayClient razorpayClient=new RazorpayClient(API_KEY, SECRET_KEY);
            
            //payment request
            JSONObject paymentLinkReq=new JSONObject();
            paymentLinkReq.put("amount",amount);
            paymentLinkReq.put("currency", "INR");

            //customer data
            JSONObject customer=new JSONObject();
            customer.put("name", user.getName());
            customer.put("email", user.getEmail());
            paymentLinkReq.put("customer", customer);

            //notification
            JSONObject notify=new JSONObject();
            notify.put("email", true);
            paymentLinkReq.put("notify", notify);

            //reminder
            paymentLinkReq.put("reminder_enable", true);

            //callback url
            paymentLinkReq.put("callback_url", "http://localhost:5173/wallet");
            paymentLinkReq.put("callback_method", "get");

            //create PAymentLink
            PaymentLink paymentLink=razorpayClient.paymentLink.create(paymentLinkReq);

            String LinkId = paymentLink.get("id");
            String url = paymentLink.get("short_url");

            PaymentResponse response=new PaymentResponse();
            response.setUrl(url);

            return response;
        } catch (Exception e) {
           throw new RazorpayException(e.getMessage());
        }
       
    }

}
