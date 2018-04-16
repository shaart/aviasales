package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Account;

import java.util.List;

public interface AccountRepository {

  void insert(Account account);

  List<Account> getAccountByLogin(String login);

  boolean isExist(String parameter, String row);
}
