package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Account;
import java.util.List;

public interface AccountRepository {

  Account getAccountById(Long id);

  List<Account> getAccountByLogin(String login);

  List<Account> getAccounts();
public interface AccountRepository {
  void insert(Account account);

  List<Account> getAccounts(int page, int count);
  List<Account> getAccountByLogin(String login);

  void addAccount(Account account);

  boolean isExist(String rowValue, String rowName);
}
