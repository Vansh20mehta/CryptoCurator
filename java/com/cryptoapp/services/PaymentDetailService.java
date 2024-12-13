package com.cryptoapp.services;

import com.cryptoapp.entities.PaymentDetails;
import com.cryptoapp.entities.User;

public interface PaymentDetailService {

    public PaymentDetails getPaymentDetails(User user);

    public PaymentDetails addPaymentDetails(
                    String accountNumber,
                    String accountHolderName,
                    String bankName,
                    String ifsc,
                    User user);


}
