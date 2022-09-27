package com.jon.service.impl;

import com.jon.dao.AccountDao;
import com.jon.domain.Account;
import com.jon.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public void transfer(String out, String in, Double money) {
        accountDao.outMoney(out, money);
        accountDao.inMoney(in, money);
    }

    @Override
    public Account findById(Integer id) {
        return accountDao.findById(id);
    }
}
