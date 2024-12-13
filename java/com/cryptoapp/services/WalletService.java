package com.cryptoapp.services;

import java.util.List;
import java.util.Optional;

import com.cryptoapp.entities.Order;
import com.cryptoapp.entities.User;
import com.cryptoapp.entities.Wallet;

public interface WalletService {

  public  Wallet getUserWallet(User user);

  public  Wallet addBalance(Wallet wallet,Long amount);

  public Wallet getWalletById(Double walletId) throws Exception;

  public  Wallet walletTowalletTransfer(User sender,Wallet reciever,Long amount) throws Exception;

  public  Wallet payOrderPayment(Order order,User user) throws Exception;

   
    }


