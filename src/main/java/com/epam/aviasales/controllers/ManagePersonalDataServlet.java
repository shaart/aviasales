package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.domain.Role;
import com.epam.aviasales.services.PersonalDataService;
import com.epam.aviasales.services.ParserService;
import com.epam.aviasales.services.impl.PersonalDataServiceImpl;
import com.epam.aviasales.services.impl.ParserServiceImpl;
import com.epam.aviasales.util.Action;
import com.epam.aviasales.util.AuthHelper;
import com.epam.aviasales.util.ErrorHelper;
import com.epam.aviasales.util.ParseRequestHelper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;

@Log4j
public class ManagePersonalDataServlet extends HttpServlet {

  private ParserService parserService;
  private PersonalDataService personalDataService;
  private static final String SERVLET_ADDRESS = "/manage/personals";
  private static final List<Role> ALLOWED_ROLES = Arrays.asList(Role.ADMIN, Role.MANAGER);

  @Override
  public void init() throws ServletException {
    try {
      personalDataService = PersonalDataServiceImpl.getInstance();
      parserService = ParserServiceImpl.getInstance();
    } catch (Exception e) {
      log.error(e);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    boolean canAccess = AuthHelper.isAllowedUser(req, resp, ALLOWED_ROLES, SERVLET_ADDRESS);
    if (!canAccess) {
      return;
    }

    try {
      final int DEFAULT_PAGE_SIZE = 15;
      final int DEFAULT_PAGE = 1;

      int page = ParseRequestHelper.getParameterOrDefaultValue(req, "page", DEFAULT_PAGE);
      int size = ParseRequestHelper.getParameterOrDefaultValue(req, "size", DEFAULT_PAGE_SIZE);
      req.setAttribute("page", page);
      req.setAttribute("size", size);

      String[] parameters = {"id", "name", "passport", "dateOfBirth"};
      ParseRequestHelper.setExistingParametersAsAttributes(req, parameters);
      ParseRequestHelper.setPagingURLAttributes(req, page);

      PersonalData seekingPersonalData = parserService.parsePersonalData(req);
      List<PersonalData> personalDatas = personalDataService
          .getPersonalDatasLike(seekingPersonalData, page, size);
      req.setAttribute("personaldatas", personalDatas);

      req.getRequestDispatcher("/WEB-INF/managePersonalData.jsp").forward(req, resp);
    } catch (Exception e) {
      ErrorHelper.redirectToErrorPage(req, resp, e, SERVLET_ADDRESS);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    boolean canAccess = AuthHelper.isAllowedUser(req, resp, ALLOWED_ROLES, SERVLET_ADDRESS);
    if (!canAccess) {
      return;
    }

    Action action = ParseRequestHelper.getRequestAction(req);
    try {
      PersonalData receivedPersonalData = parserService.parsePersonalData(req);

      switch (action) {
        case ADD:
          personalDataService.addPersonalData(receivedPersonalData);
          log.info("Added " + receivedPersonalData);
          break;
        case SAVE:
          personalDataService
              .updatePersonalData(receivedPersonalData.getId(), receivedPersonalData);
          log.info("Updated " + receivedPersonalData);
          break;
        case DELETE:
          personalDataService.deletePersonalData(receivedPersonalData.getId());
          log.info("Removed " + receivedPersonalData);
          break;
        default:
          log.error("ManagePersonalDataServlet received unknown action to \"doPost()\"");
          break;
      }
    } catch (Exception e) {
      ErrorHelper.redirectToErrorPage(req, resp, e, SERVLET_ADDRESS);
      return;
    }
    try {
      resp.sendRedirect(SERVLET_ADDRESS);
    } catch (IOException e) {
      ErrorHelper.redirectToErrorPage(req, resp, e, SERVLET_ADDRESS);
      return;
    }
  }
}
