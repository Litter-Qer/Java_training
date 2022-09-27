package spring3.service;

import spring3.domain.Account;

import java.util.List;

public interface AccountService {

    void save(Account account);
    void update(Account account);
    void delete(Integer id);
    Account findById(Integer id);
    List<Account> findAll();
}
