package com.cryptoapp.servicesimpl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptoapp.entities.Order;
import com.cryptoapp.entities.OrderType;
import com.cryptoapp.entities.User;
import com.cryptoapp.entities.Wallet;
import com.cryptoapp.repository.WalletRepo;
import com.cryptoapp.services.WalletService;

@Service
public class WalletServiceImple implements WalletService{
    @Autowired
    private WalletRepo walletRepo;

    @Override
    public Wallet getUserWallet(User user) {
            Wallet wallet = walletRepo.findByUserId(user.getUserId());
            if(wallet==null){
                wallet = new Wallet();
                wallet.setUser(user);
            }
            return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long amount) {
        BigDecimal balance = wallet.getBalance();
        balance = balance.add(BigDecimal.valueOf(amount));
        wallet.setBalance(balance);
        return walletRepo.save(wallet);
    }

    @Override
    public Wallet getWalletById(Double walletId) throws Exception {
        return walletRepo.findById(walletId).orElse(null);
    }

    @Override
    public Wallet walletTowalletTransfer(User sender, Wallet reciever, Long amount) throws Exception {
        Wallet senderWallet = getUserWallet(sender);
      if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount))<0){
            throw new Exception("Insufficent balance");
      }
      senderWallet.setBalance(senderWallet.getBalance().subtract(BigDecimal.valueOf(amount)));
      walletRepo.save(senderWallet);

        reciever.setBalance(reciever.getBalance().add(BigDecimal.valueOf(amount)));
       walletRepo.save(reciever);

       return senderWallet;
  
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet = getUserWallet(user);
               if(order.getOrderType().equals(OrderType.BUY)){
                    BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
                    if(newBalance.compareTo(order.getPrice())<0){

                        throw new Exception("Insufficent balance");
                    }
                    wallet.setBalance(newBalance);
    
               }
               else if(order.getOrderType().equals(OrderType.SELL)){
                BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
                wallet.setBalance(newBalance);
               }
               walletRepo.save(wallet);





            return wallet;
    }
    


   

}
