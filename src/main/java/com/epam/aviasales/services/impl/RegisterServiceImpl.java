package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.repositories.AccountRepository;
import com.epam.aviasales.repositories.impl.AccountRepositoryImpl;
import com.epam.aviasales.services.RegisterService;

import java.util.ArrayList;
import java.util.List;

public class RegisterServiceImpl implements RegisterService {

  private static volatile RegisterServiceImpl instance;
  private static final AccountRepository accountRepository =
      AccountRepositoryImpl.getInstance();

  private RegisterServiceImpl() {
  }

  public static RegisterServiceImpl getInstance() {
    RegisterServiceImpl localInstance = instance;
    if (localInstance == null) {
      synchronized (RegisterServiceImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new RegisterServiceImpl();
        }
      }
    }
    return localInstance;
  }

  public List<String> addAccount(Account account) {
    List<String> errorMessages = new ArrayList<>();

    if (accountRepository.isExist("login", account.getLogin())) {
      errorMessages.add("register.error.login_exist");
    }
    if (accountRepository.isExist("email", account.getEmail())) {
      errorMessages.add("register.error.email_exist");
    }
    if (errorMessages.isEmpty()) {
      accountRepository.addAccount(account);
    }
    return errorMessages;
  }
}
