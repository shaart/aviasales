package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.Role;
import com.epam.aviasales.services.RegisterService;
import com.epam.aviasales.services.impl.RegisterServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterServlet extends HttpServlet {
  private RegisterService registerService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("Registration.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Account account =
        Account.builder()
            .login(request.getParameter("inputLogin"))
            .role(Role.USER)
            .name(request.getParameter("inputName"))
            .password(DigestUtils.sha256Hex(request.getParameter("inputPassword")))
            .email(request.getParameter("inputEmail"))
            .phone(request.getParameter("inputPhone"))
            .build();

    //TODO it is not obvious what this method does. If I hadn't looked at `addAccount` implementation, I would have thought that it just adds an account,
    //TODO then the following `if` check is useless. So, call methods more understandable.
    List<String> errorMessages = registerService.addAccount(account);

    if (errorMessages.isEmpty()) {
      account.setPassword(null);
      HttpSession session = request.getSession(true);
      session.setAttribute("account", account);
      response.sendRedirect("/");
    } else {
      request.setAttribute("errorMessage", errorMessages.get(0));
      request.getRequestDispatcher("Registration.jsp").forward(request, response);
    }
  }

  public void init() {
    registerService = RegisterServiceImpl.getInstance();
  }
}
