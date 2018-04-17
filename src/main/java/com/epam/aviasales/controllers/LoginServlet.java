package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.services.LoginService;
import com.epam.aviasales.services.impl.LoginServiceImpl;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

  private LoginService loginService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    //TODO JSP pages should be named in lowercase.
    req.getRequestDispatcher("Login.jsp").forward(req, resp);
  }

  //TODO: add code for rememberMe checkbox,
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String login = request.getParameter("inputLogin");
    String password = request.getParameter("inputPassword");

    List<String> errorMessages = loginService.validate(login, password);
    if (errorMessages.isEmpty()) {
      //TODO: session.setAttribute("account", loginService.getAccount());
      Account account = loginService.getAccount();
      HttpSession session = request.getSession(true);
      session.setAttribute("account", account);
      response.sendRedirect("/");
    } else {
      request.setAttribute("errorMessage", errorMessages.get(0));
      request.getRequestDispatcher("Login.jsp").forward(request, response);
    }
  }

  public void init() {
    loginService = LoginServiceImpl.getInstance();
  }
}
