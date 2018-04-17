package com.epam.aviasales.services.impMock;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.repositories.AccountRepository;
import com.epam.aviasales.repositories.implMock.AccountRepositoryImplMock;
import com.epam.aviasales.services.LoginService;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginServiceImplMock implements LoginService {

  private static volatile LoginServiceImplMock instance;
  private final AccountRepository accountRepository = AccountRepositoryImplMock.getInstance();
  private Account account;

  public Account getAccount() {
    return account;
  }

  public static LoginServiceImplMock getInstance() {
    LoginServiceImplMock localInstance = instance;
    if (localInstance == null) {
      synchronized (LoginServiceImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new LoginServiceImplMock();
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
      account.setPassword(null);
    } else {
      this.account = null;
      errorMessages.add("login.error.wrongPassword");
    }
    return errorMessages;
  }
}
