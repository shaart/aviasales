package com.epam.aviasales.services;

import com.epam.aviasales.domain.Account;
import java.util.List;

public interface AccountService {

  List<Account> getAccounts();

  List<Account> getAccounts(int page, int count);

  List<Account> getAccountById(Long id);

  Account getAccountByLogin(String login);

  void addAccount(Account account);

  List<Account> getAccountsLike(Account seekingAccount, int page, int size);

  void updateAccount(Long id, Account receivedAccount);

  void deleteAccount(Long id);
}
