package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.services.AccountService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("Login.jsp").forward(req, resp);
  }
  /*ToDo: add code for remembeMe checkbox, add Account to attributes?
   * ToDo: Change pageToRedirect to /profile*/
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String login = req.getParameter("inputLogin");
    String password = req.getParameter("inputPassword");
    // проверка логина и пароля
    // если все верно, то добавляем в сеанс id пользователя или Account
    // и редиректим туда, откуда он там пришел

    AccountService accountService = AccountService.getInstance();
    boolean isAccountValidated = accountService.validate(login, password);
    if (isAccountValidated) {
      HttpSession session = req.getSession(true);
      session.setAttribute("login", login);

      String pageToRedirect = (String) session.getAttribute("prevPage");
      pageToRedirect = pageToRedirect != null ? pageToRedirect : "/";
      resp.sendRedirect(pageToRedirect);
    } else {
      req.setAttribute("errorMessage", "Credentials are invalid");
      req.getRequestDispatcher("Login.jsp").forward(req, resp);
    }
  }
}
