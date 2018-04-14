package com.epam.aviasales.services;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.repositories.AccountRepository;

import java.util.ArrayList;
import java.util.List;

public class RegisterService {
  private static RegisterService instance;

  private RegisterService() {}

  public static synchronized RegisterService getInstance() {
    if (instance == null) {
      instance = new RegisterService();
    }
    return instance;
  }

  public List<String> addAccount(Account account) {
    List<String> errorMessages = new ArrayList<>();
    AccountRepository accountRepository = AccountRepository.getInstance();
    if (accountRepository.isExist(account.getLogin(), "login")) {
      errorMessages.add("register.error.login_exist");
    }
    if (accountRepository.isExist(account.getEmail(), "email")) {
      errorMessages.add("register.error.email_exist");
    }
    if (errorMessages.isEmpty()) {
      accountRepository.insert(account);
    }
    return errorMessages;
  }
}
