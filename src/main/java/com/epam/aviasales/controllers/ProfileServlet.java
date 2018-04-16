package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.*;
import com.epam.aviasales.services.ProfileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProfileServlet extends HttpServlet {

  private ProfileService profileService;

  @Override
  public void init() {
    profileService = ProfileService.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    Account account = (Account) session.getAttribute("account");
    List<Ticket> tickets = profileService.getAccountTickets(account.getId());

    request.setAttribute("account", account);
    request.setAttribute("tickets", tickets);

    request.getRequestDispatcher("Profile.jsp").forward(request, response);
  }
}
