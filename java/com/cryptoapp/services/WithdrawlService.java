package com.cryptoapp.services;

import java.util.List;

import com.cryptoapp.entities.User;
import com.cryptoapp.entities.Withdrawl;

public interface WithdrawlService {
    public List<Withdrawl> getAllWithdrawlRequest();

    public Withdrawl getWithdrawlById(Long id);

    public Withdrawl requestWithdrawl(Long amount,User user);

    public Withdrawl proceedWithdrawl(Long withdrawlId,boolean accept) throws Exception;

    public List<Withdrawl> getUserWithdrawlHistory(User user);

}
