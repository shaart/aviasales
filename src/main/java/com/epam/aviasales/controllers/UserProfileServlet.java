package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.exceptions.AccountValidateException;
import com.epam.aviasales.repositories.AccountRepository;
import com.epam.aviasales.services.LoginService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserProfileServlet extends HttpServlet {
  /*Deside what ToDo with exception*/
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    LoginService loginService = LoginService.getInstance();
    /*AccountRepository accountRepository = AccountRepository.getInstance();//убрать
    Account account1 = new Account();
    account1.setLogin("janedoe");
    account1.setType(0);
    account1.setPassword(DigestUtils.sha256Hex("1"));
    account1.setName("Jane Doe");
    account1.setEmail("janedoe@mail.com");
    account1.setPhone("7633826423438");
    accountRepository.insert(account1);*/

    Account account = loginService.getAccount();
    request.setAttribute("type", account.getType());
    request.setAttribute("name", account.getName());
    request.setAttribute("login", account.getLogin());
    request.setAttribute("email", account.getEmail());
    request.setAttribute("phone", account.getPhone());

    request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
  }
}
