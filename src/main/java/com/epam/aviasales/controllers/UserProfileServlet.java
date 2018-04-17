package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.services.AccountService;

import com.epam.aviasales.services.impl.AccountServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserProfileServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    AccountService accountService = AccountServiceImpl.getInstance();
    Account account1 = accountService.getAccountByLogin("smartbob1");

    req.setAttribute("role", account1.getRole());
    req.setAttribute("name", account1.getName());
    req.setAttribute("login", account1.getLogin());
    req.setAttribute("email", account1.getEmail());
    req.setAttribute("phone", account1.getPhone());

    req.getRequestDispatcher("UserProfile.jsp").forward(req, resp);

  }
}
