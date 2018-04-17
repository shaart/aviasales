package com.epam.aviasales.services;

import com.epam.aviasales.domain.Account;

import java.util.List;

public interface RegisterService {

  List<String> addAccount(Account account);
}
