package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.repositories.AccountRepository;
import com.epam.aviasales.repositories.impl.AccountRepositoryImpl;
import com.epam.aviasales.services.AccountService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountServiceImpl implements AccountService {

  private static volatile AccountService instance;

  private static final AccountRepository accountRepository = AccountRepositoryImpl.getInstance();

  public static AccountService getInstance() {
    AccountService localInstance = instance;
    if (localInstance == null) {
      synchronized (AccountServiceImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new AccountServiceImpl();
        }
      }
    }

    return localInstance;
  }

  @Override
  public List<Account> getAccounts() {
    return accountRepository.getAccounts(1, 20);
  }

  @Override
  public List<Account> getAccounts(int page, int count) {
    return accountRepository.getAccounts(page, count);
  }

  @Override
  public Account getAccountById(Long id) {
    return accountRepository.getAccountById(id);
  }

  @Override
  public Account getAccountByLogin(String login) {
    List<Account> accounts = accountRepository.getAccountByLogin(login);

    //TODO if you want, you can use `?` operator. return (accounts == null || accounts.isEmpty()) ? null : accounts.get(0);
    if (accounts == null || accounts.isEmpty()) {
      return null;
    }
    return accounts.get(0);
  }

  @Override
  public void addAccount(Account account) {
    accountRepository.addAccount(account);
  }
}
