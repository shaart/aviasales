package com.epam.aviasales.services;

import com.epam.aviasales.exceptions.AccountValidateException;
import com.epam.aviasales.domain.Account;
import com.epam.aviasales.exceptions.IncorrectLoginException;
import com.epam.aviasales.exceptions.IncorrectPasswordException;
import com.epam.aviasales.exceptions.LoginNotExistException;
import com.epam.aviasales.repositories.AccountRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

public class LoginService {
  private static LoginService instance;
  private Account account;

  private LoginService() {}

  public Account getAccount() {
    return account;
  }

  public static synchronized LoginService getInstance() {
    if (instance == null) {
      instance = new LoginService();
    }
    return instance;
  }
  /*ToDo add validation code
   * Validate login and password Strings before sending it to repository
   * */
  public List<String> validate(String login, String password) {
    List<String> errorMessages = new ArrayList<>();
    AccountRepository accountRepository = AccountRepository.getInstance();

    Account account = accountRepository.get(login);
    if (account == null) {
      errorMessages.add("doesNotExistMessage");
      return errorMessages;
    }

    String sha256hex = DigestUtils.sha256Hex(password);
    if (sha256hex.equals(account.getPassword())) {
      account.setPassword(null);
      this.account = account;
    } else {
      this.account = null;
      errorMessages.add("wrongPassword");
    }
    return errorMessages;
  }
}
