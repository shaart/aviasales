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
   * */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String login = request.getParameter("inputLogin");
    String password = request.getParameter("inputPassword");

    AccountService accountService = AccountService.getInstance();
    Account account = accountService.validate(login, password);
    /*if (isAccountValidated) {
      HttpSession session = request.getSession(true);
      session.setAttribute("account", );
      response.sendRedirect("/");
    } else {
      request.setAttribute("errorMessage", "Credentials are invalid");
      request.getRequestDispatcher("Login.jsp").forward(request, response);
    }*/
  }
}
