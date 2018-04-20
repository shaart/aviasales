package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.repositories.AccountRepository;
import com.epam.aviasales.repositories.impl.AccountRepositoryImpl;
import com.epam.aviasales.services.LoginService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginServiceImpl implements LoginService {

  private static volatile LoginServiceImpl instance;
  private final AccountRepository accountRepository = AccountRepositoryImpl.getInstance();
  private Account account;

  public Account getAccount() {
    return account;
  }

  public static LoginServiceImpl getInstance() {
    LoginServiceImpl localInstance = instance;
    if (localInstance == null) {
      synchronized (LoginServiceImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new LoginServiceImpl();
        }
      }
    }

    return localInstance;
  }

  /*ToDo add validation code
   * Validate login and password Strings before sending it to repository
   * */
  public List<String> validate(String login, String password) {
    List<String> errorMessages = new ArrayList<>();

    List<Account> accounts = accountRepository.getAccountByLogin(login);
    if (accounts.isEmpty()) {
      errorMessages.add("login.error.doesNotExistMessage");
      return errorMessages;
    }

    this.account = accounts.get(0);

    String sha256hex = DigestUtils.sha256Hex(password);
    if (sha256hex.equals(account.getPassword())) {
      account.setPassword("");
    } else {
      this.account = null;
      errorMessages.add("login.error.wrongPassword");
    }
    return errorMessages;
  }
}
