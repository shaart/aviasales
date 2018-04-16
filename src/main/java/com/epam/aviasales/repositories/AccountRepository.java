package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Account;
import java.util.List;

public interface AccountRepository {

  Account getByName(String name);

  Account getById(Long id);

  Account getAccountByLogin(String login);

  List<Account> getAccounts();

  List<Account> getAccounts(int page, int count);

  void insert(Account account);
}
