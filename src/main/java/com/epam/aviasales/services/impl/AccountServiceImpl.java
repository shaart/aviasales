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
    return accountRepository.getAccounts();
  }

  @Override
  public List<Account> getAccounts(int page, int count) {
    return accountRepository.getAccounts(page, count);
  }

  @Override
  public List<Account> getAccountById(Long id) {
    return accountRepository.getAccountById(id);
  }

  @Override
  public Account getAccountByLogin(String login) {
    List<Account> accounts = accountRepository.getAccountByLogin(login);
    if (accounts == null || accounts.isEmpty()) {
      return null;
    }
    return accounts.get(0);
  }

  @Override
  public void addAccount(Account account) {
    accountRepository.addAccount(account);
  }

  @Override
  public List<Account> getAccountsLike(Account seekingAccount, int page, int size) {
    return accountRepository.getAccountsLike(seekingAccount, page, size);
  }

  @Override
  public void updateAccount(Long id, Account receivedAccount) {
    accountRepository.updateAccount(id, receivedAccount);
  }

  @Override
  public void deleteAccount(Long id) {
    accountRepository.deleteAccount(id);
  }
}
