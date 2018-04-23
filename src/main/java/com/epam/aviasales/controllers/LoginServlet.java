package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.services.LoginService;
import com.epam.aviasales.services.impl.LoginServiceImpl;

import com.epam.aviasales.util.ErrorHelper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j;

@Log4j
public class LoginServlet extends HttpServlet {

  private LoginService loginService;

  @Override
  public void init() {
    try {
      loginService = LoginServiceImpl.getInstance();
    } catch (Exception e) {
      log.error(e);
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      HttpSession session = request.getSession();
      Account account = (Account) session.getAttribute("account");
      if (account != null) {
        request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
      }
      request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    } catch (Exception e) {
      log.error(e);
      ErrorHelper.redirectToErrorPage(request, response, e, "/login");
      return;
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      String login = request.getParameter("inputLogin");
      String password = request.getParameter("inputPassword");

      List<String> errorMessages = loginService.validate(login, password);
      if (errorMessages.isEmpty()) {
        Account account = loginService.getAccount();
        HttpSession session = request.getSession(true);
        session.setAttribute("account", account);
        log.info("Login " + account);
        response.sendRedirect("/");
      } else {
        request.setAttribute("errorMessage", errorMessages.get(0));
        log.error("login failed " + errorMessages.get(0));
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
      }
    } catch (Exception e) {
      log.error(e);
      ErrorHelper.redirectToErrorPage(request, response, e, "/login");
      return;
    }
  }
}
