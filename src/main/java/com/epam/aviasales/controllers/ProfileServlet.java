package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.*;
import com.epam.aviasales.services.ProfileService;

import com.epam.aviasales.services.impl.ProfileServiceImpl;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.IO;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

public class ProfileServlet extends HttpServlet {

  private ProfileService profileService;

  @Override
  public void init() {
    profileService = ProfileServiceImpl.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    /*Todo Localization for arrival and departure dates*/

    HttpSession session = request.getSession();
    Account account = (Account) session.getAttribute("account");
    List<Ticket> tickets = profileService.getAccountTickets(account.getId());
    List<PersonalData> personalDatas = profileService.getAccountPersonalDatas(account.getId());

    if (!tickets.isEmpty()) {
      session.setAttribute("tickets", tickets);
    }
    if (!personalDatas.isEmpty()) {
      session.setAttribute("personalDatas", personalDatas);
    }

    request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
  }

  private void changePassword(
      HttpServletRequest request, HttpServletResponse response, Account account)
      throws IOException, ServletException {
    String oldPassword = request.getParameter("inputOldPassword");
    String newPassword = request.getParameter("inputNewPassword");
    String confirmNewPassword = request.getParameter("inputConfirmNewPassword");
    if (profileService.validatePasswords(
        account.getId(), oldPassword, newPassword, confirmNewPassword)) {
      profileService.updateAccountPassword(account, newPassword);
      response.sendRedirect("/profile");
      return;
    } else {
      request.setAttribute("changePasswordError", "profile.error.wrongPassword");
      request.setAttribute("autoChangePassword", true);
    }
    request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
  }

  private void returnTicket(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String ticketId = request.getParameter("ticketId");
    profileService.deleteAccountTicketById(Long.valueOf(ticketId));
    request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
  }

  private void changeAccount(
      HttpServletRequest request,
      HttpServletResponse response,
      HttpSession session,
      Account account)
      throws IOException, ServletException {

    Account newAccount =
        Account.builder()
            .id(account.getId())
            .role(account.getRole())
            .name(request.getParameter("inputName"))
            .login(request.getParameter("inputLogin"))
            .password(null)
            .email(request.getParameter("inputEmail"))
            .phone(request.getParameter("inputPhone"))
            .build();

    List<String> errorMessages = profileService.updateAccount(newAccount);
    if (!errorMessages.isEmpty()) {
      request.setAttribute("changeAccountError", errorMessages.get(0));
      request.setAttribute("autoOpenEditAccount", true);
    } else {
      session.setAttribute("account", newAccount);
      response.sendRedirect("/profile");
      return;
    }
    request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
  }

  private void editPersonalData(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.setAttribute("autoOpenEditPersonalData", true);
    String personalDataId = request.getParameter("personalDataId");
    PersonalData personalData = profileService.getPersonalDataById(Long.valueOf(personalDataId));
    request.setAttribute("modalPersonalData", personalData);
    request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
  }

  private void savePersonalData(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    PersonalData personalData =
        PersonalData.builder()
            .id(Long.valueOf(request.getParameter("inputPersonalDataId")))
            .name(request.getParameter("inputPersonalDataName"))
            .passport(request.getParameter("inputPersonalDataPassport"))
            .dateOfBirth(LocalDate.parse(request.getParameter("inputPersonalDataDOB")))
            .build();

    List<String> errorMessages = profileService.updatePersonalData(personalData);
    if (!errorMessages.isEmpty()) {
      request.setAttribute("changePersonalDataError", errorMessages.get(0));
      request.setAttribute("autoOpenEditPersonalData", true);
      request.setAttribute("modalPersonalData", personalData);
    } else {
      /*ToDo Show information about successful purchase*/
      response.sendRedirect("/profile");
      return;
    }
    request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();
    Account account = (Account) session.getAttribute("account");

    if (request.getParameter("returnButton") != null) {
      returnTicket(request, response);
    }

    if (request.getParameter("changePasswordButton") != null) {
      request.setAttribute("autoChangePassword", true);
      request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
    }

    if (request.getParameter("savePassword") != null) {
      changePassword(request, response, account);
    }
    if (request.getParameter("editButton") != null) {
      request.setAttribute("autoOpenEditAccount", true);
      request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
    }

    if (request.getParameter("saveButton") != null) {
      changeAccount(request, response, session, account);
    }

    if (request.getParameter("editPersonalDataButton") != null) {
      editPersonalData(request, response);
    }

    if (request.getParameter("savePersonalDataButton") != null) {
      savePersonalData(request, response);
    }
  }
}
