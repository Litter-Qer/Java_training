package com.jon.service;


import com.jon.domain.Account;
import org.springframework.transaction.annotation.Transactional;

public interface AccountService {
    @Transactional
    public void transfer(String out, String in, Double money);

    Account findById(Integer id);
}
