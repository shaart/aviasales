package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Role;
import com.epam.aviasales.util.AuthHelper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageServlet extends HttpServlet {

  private static final String SERVLET_ADDRESS = "/manage";
  private static final List<Role> ALLOWED_ROLES = Arrays.asList(Role.ADMIN, Role.MANAGER);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    boolean canAccess = AuthHelper.isAllowedUser(req, resp, ALLOWED_ROLES, SERVLET_ADDRESS);
    if (!canAccess) {
      return;
    }

    req.getRequestDispatcher("/WEB-INF/manage.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    boolean canAccess = AuthHelper.isAllowedUser(req, resp, ALLOWED_ROLES, SERVLET_ADDRESS);
    if (!canAccess) {
      return;
    }

    super.doPost(req, resp);
  }
}
