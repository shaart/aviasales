package com.epam.aviasales.repositories.implMock;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.Role;
import com.epam.aviasales.repositories.AccountRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountRepositoryImplMock implements AccountRepository {

  private static volatile AccountRepository instance;

  public static AccountRepository getInstance() {
    AccountRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (AccountRepositoryImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new AccountRepositoryImplMock();
        }
      }
    }

    return localInstance;
  }

  private static final Map<Long, Account> ACCOUNTS_CACHE = new HashMap<>();

  static {
    final int CACHE_COUNT = 100;

    for (int i = 1; i < CACHE_COUNT; i++) {
      ACCOUNTS_CACHE.put(Long.valueOf(i),
          Account.builder().id(Long.valueOf(i))
              .role(i % 30 == 0 ? Role.ADMIN : (i % 15 == 0 ? Role.MANAGER : Role.USER))
              .name(i == 15 ? "Bob Marley" : "BOB-" + i).login("smartbob" + i)
              .password(DigestUtils.sha256Hex("SHA256-" + i))
              .email("bob" + i + "@bobworld.com")
              .phone("123456789")
              .build());
    }
  }

  @Override
  public List<Account> getAccounts() {
    return getAccounts(1, Integer.MAX_VALUE);
  }

  @Override
  public List<Account> getAccounts(int page, int count) {
    List<Account> accountList = new ArrayList<>();
    if (page <= 0 || count <= 0) {
      return accountList;
    }

    final int startI = (page - 1) * count;
    for (int i = startI; i < startI + count; i++) {
      if (i >= ACCOUNTS_CACHE.size()) {
        break;
      }
      accountList.add(ACCOUNTS_CACHE.get(Long.valueOf(i)));
    }
    return accountList;
  }

  @Override
  public Account getAccountByName(String name) {
    for (Account account : ACCOUNTS_CACHE.values()) {
      if (account.getName().equals(name)) {
        return account;
      }
    }
    return null;
  }

  @Override
  public Account getAccountById(Long id) {
    return ACCOUNTS_CACHE.get(id);
  }

  @Override
  public void addAccount(Account account) {
    ACCOUNTS_CACHE.put(account.getId(), account);
  }

  @Override
  public boolean isExist(String rowValue, String rowName) {
    return false;
  }

  @Override
  public List<Account> getAccountByLogin(String login) {
    List<Account> accounts = new ArrayList<>();
    for (Account account : ACCOUNTS_CACHE.values()) {
      if (account.getLogin().equals(login)) {
        accounts.add(account);
        return accounts;
      }
    }
    return accounts;
  }
}
