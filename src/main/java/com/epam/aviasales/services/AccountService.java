package com.epam.aviasales.services;

import com.epam.aviasales.domain.Account;
import java.util.List;

public interface AccountService {

  List<Account> getAccounts();

  List<Account> getAccounts(int page, int count);

  Account getAccountById(Long id);

  Account getAccountByLogin(String login);

  void addAccount(Account account);
}
