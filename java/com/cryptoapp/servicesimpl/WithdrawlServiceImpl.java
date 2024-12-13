package com.cryptoapp.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptoapp.entities.User;
import com.cryptoapp.entities.Withdrawl;
import com.cryptoapp.entities.WithdrawlStatus;
import com.cryptoapp.repository.WithdrawlRepo;
import com.cryptoapp.services.WithdrawlService;



@Service
public class WithdrawlServiceImpl implements WithdrawlService{

    @Autowired
    private WithdrawlRepo withdrawlRepo;

    @Override
    public List<Withdrawl> getAllWithdrawlRequest() {
        return withdrawlRepo.findAll();
    }

    @Override
    public Withdrawl getWithdrawlById(Long id) {
        return withdrawlRepo.findById(id).orElse(null);
        
    }

    @Override
    public Withdrawl requestWithdrawl(Long amount, User user) {
        Withdrawl withdrawl = new Withdrawl();
        withdrawl.setAmount(amount);
        withdrawl.setUser(user);
        withdrawl.setWithdrawlStatus(WithdrawlStatus.PENDING);
        return withdrawlRepo.save(withdrawl);
      
    }

    @Override
    public Withdrawl proceedWithdrawl(Long withdrawlId, boolean accept) throws Exception {
        Optional<Withdrawl> withdrawl = withdrawlRepo.findById(withdrawlId);
        if (withdrawl.isEmpty()) {
            throw new Exception("withdwal not found!");
            
        }
        Withdrawl  withdrawl1 = withdrawl.get();
        if (accept) {
            withdrawl1.setWithdrawlStatus(WithdrawlStatus.SUCCESS);
        }
        else {
            withdrawl1.setWithdrawlStatus(WithdrawlStatus.FAILED);
        }
        return withdrawlRepo.save(withdrawl1);
        
    }

    @Override
    public List<Withdrawl> getUserWithdrawlHistory(User user) {
        return withdrawlRepo.findByUserId(user.getUserId());
        
    }

}
