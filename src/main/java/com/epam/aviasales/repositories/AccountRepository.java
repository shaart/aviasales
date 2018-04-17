package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Account;
import java.util.List;

public interface AccountRepository {

  Account getAccountById(Long id);

  List getAccountByLogin(String login);

  List<Account> getAccounts();

  List<Account> getAccounts(int page, int count);

  void addAccount(Account account);

  boolean isExist(String rowValue, String rowName);
}
