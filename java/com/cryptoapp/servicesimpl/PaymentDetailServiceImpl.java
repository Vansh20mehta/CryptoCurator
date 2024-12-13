package com.cryptoapp.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptoapp.entities.PaymentDetails;
import com.cryptoapp.entities.User;
import com.cryptoapp.repository.PaymentDetailRepo;
import com.cryptoapp.services.PaymentDetailService;

@Service
public class PaymentDetailServiceImpl implements PaymentDetailService{

    @Autowired
    private PaymentDetailRepo paymentDetailRepo;

    @Override
    public PaymentDetails getPaymentDetails(User user) {
        return paymentDetailRepo.findByUserId(user.getUserId());


      

       
    }

    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String bankName,
            String ifsc, User user) {
                PaymentDetails paymentDetails = new PaymentDetails();
                paymentDetails.setAccountNumber(accountNumber);
                paymentDetails.setAccountHolderName(accountHolderName);
                paymentDetails.setBankName(bankName);
                paymentDetails.setIfsc(ifsc);
                paymentDetails.setUser(user);
                paymentDetails = paymentDetailRepo.save(paymentDetails);
                return paymentDetails;

        }

}
