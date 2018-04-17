package com.epam.aviasales.services;

import com.epam.aviasales.domain.Account;

import java.util.List;

public interface LoginService {

  Account getAccount();

  List<String> validate(String login, String password);
}
