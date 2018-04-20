package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.*;
import com.epam.aviasales.services.ProfileService;

import com.epam.aviasales.services.impl.ProfileServiceImpl;
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

    request.getRequestDispatcher("Profile.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();
    Account account = (Account) session.getAttribute("account");

    if (request.getParameter("returnButton") != null) {
      String ticketId = request.getParameter("ticketId");
      profileService.deleteAccountTicketById(Long.valueOf(ticketId));
    }
    if (request.getParameter("changePasswordButton") != null) {
      request.setAttribute("autoChangePassword", true);
    }
    if (request.getParameter("savePassword") != null) {
      String oldPassword = request.getParameter("inputOldPassword");
      String newPassword = request.getParameter("inputNewPassword");
      String confirmNewPassword = request.getParameter("inputConfirmNewPassword");
      if (profileService.validatePasswords(account.getId(), oldPassword, newPassword, confirmNewPassword)) {
        profileService.updateAccountPassword(account, newPassword);
        response.sendRedirect("/profile");
        return;
      }
      else{
        request.setAttribute("errorMessage3", "profile.error.wrongPassword");
        request.setAttribute("autoChangePassword", true);
      }
    }
    if (request.getParameter("editButton") != null) {
      request.setAttribute("autoOpenEditAccount", true);
    }

    if (request.getParameter("saveButton") != null) {
      List<Ticket> tickets = (List<Ticket>) session.getAttribute("tickets");

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
        request.setAttribute("errorMessage", errorMessages.get(0));
        request.setAttribute("autoOpenEditAccount", true);
      } else {
        session.setAttribute("account", newAccount);
        response.sendRedirect("/profile");
        return;
      }
    }

    if (request.getParameter("editPersonalDataButton") != null) {
      request.setAttribute("autoOpenEditPersonalData", true);
      String personalDataId = request.getParameter("personalDataId");
      PersonalData personalData = profileService.getPersonalDataById(Long.valueOf(personalDataId));
      request.setAttribute("modalPersonalData", personalData);
    }

    // ToDo onsave personal data button*/
    String personalDataName = request.getParameter("inputPersonalDataName");
    if (personalDataName != null) {
      String inputPersonalDataPassport = request.getParameter("inputPersonalDataPassport");
      String inputPersonalDataId = request.getParameter("inputPersonalDataId");
      LocalDate inputPersonalDataDOB =
          LocalDate.parse(request.getParameter("inputPersonalDataDOB"));
      PersonalData personalData =
          PersonalData.builder()
              .id(Long.valueOf(inputPersonalDataId))
              .name(personalDataName)
              .passport(inputPersonalDataPassport)
              .dateOfBirth(inputPersonalDataDOB)
              .build();

      List<String> errorMessages = profileService.updatePersonalData(personalData);
      if (!errorMessages.isEmpty()) {
        request.setAttribute("errorMessage2", errorMessages.get(0));
        request.setAttribute("autoOpenEditPersonalData", true);
        request.setAttribute("modalPersonalData", personalData);
      } else {
        /*ToDo Show information about successful purchase*/
        response.sendRedirect("/profile");
        return;
      }
    }
    request.getRequestDispatcher("Profile.jsp").forward(request, response);
  }
}
