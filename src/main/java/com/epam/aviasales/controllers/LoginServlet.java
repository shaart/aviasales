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
    req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
  }

  /*ToDo: add code for remembeMe checkbox,
   * */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String login = request.getParameter("inputLogin");
    String password = request.getParameter("inputPassword");

    List<String> errorMessages = loginService.validate(login, password);
    if (errorMessages.isEmpty()) {
      Account account = loginService.getAccount();
      HttpSession session = request.getSession(true);
      session.setAttribute("account", account);
      response.sendRedirect("/");
    } else {
      request.setAttribute("errorMessage", errorMessages.get(0));
      request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
  }

  public void init() {
    loginService = LoginServiceImpl.getInstance();
  }
}
