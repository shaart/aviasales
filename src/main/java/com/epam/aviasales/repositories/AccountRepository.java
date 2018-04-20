package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Account;
import java.util.List;

public interface AccountRepository {

  Account getAccountById(Long id);

  List<Account> getAccountByLogin(String login);

  List<Account> getAccounts();

  List<Account> getAccounts(int page, int count);

  void addAccount(Account account);

  boolean isExist(String rowName, String rowValue);

  void updateAccount(Account account);

  void updateAccountPasswordById(Long id, String password);

  List<Account> getAccountsLike(Account seekingAccount, int page, int size);

  void updateAccount(Long id, Account receivedAccount);

  void deleteAccount(Long id);
}
