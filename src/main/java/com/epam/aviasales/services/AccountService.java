package com.epam.aviasales.services;

import com.epam.aviasales.domain.Account;
import java.util.List;

public interface AccountService {

  List<Account> getAccounts();

  List<Account> getAccounts(int page, int count);

  Account getByName(String name);

  Account getById(Long id);

  Account getAccountByLogin(String login);

  void insert(Account account);
}
