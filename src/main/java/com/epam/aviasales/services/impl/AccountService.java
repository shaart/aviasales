package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.repositories.AccountRepository;

public class AccountService {
    private static AccountService instance;
    private AccountService(){

    }
    public Account getAccount(String login){
        AccountRepository accountRepository = AccountRepository.getInstance();
        return accountRepository.getAccount(login);
    }
    public static synchronized AccountService getInstance(){
        if(instance == null){
            instance = new AccountService();
        }
        return instance;
    }

}
