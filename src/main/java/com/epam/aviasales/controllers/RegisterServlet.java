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
  /*ToDo: add code for remembeMe checkbox,
   * */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String login = request.getParameter("inputLogin");
    String fullName = request.getParameter("inputName");
    String password = request.getParameter("inputPassword");
    String email = request.getParameter("inputEmail");
    String phone = request.getParameter("inputPhone");

    Account account =
        Account.builder()
            .login(login)
            .role(Role.USER)
            .name(fullName)
            .password(DigestUtils.sha256Hex(password))
            .email(email)
            .phone(phone)
            .build();

    List<String> errorMessages = registerService.addAccount(account);
    System.out.println(errorMessages.isEmpty());
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
