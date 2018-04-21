package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.Role;
import com.epam.aviasales.services.AccountService;
import com.epam.aviasales.services.ParserService;
import com.epam.aviasales.services.impl.AccountServiceImpl;
import com.epam.aviasales.services.impl.ParserServiceImpl;
import com.epam.aviasales.util.Action;
import com.epam.aviasales.util.ErrorHelper;
import com.epam.aviasales.util.ParseRequestHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;

@Log4j
public class ManageAccountsServlet extends HttpServlet {

  private ParserService parserService;
  private AccountService accountService;
  private static final String SERVLET_ADDRESS = "/manage/accounts";

  @Override
  public void init() throws ServletException {
    try {
      accountService = AccountServiceImpl.getInstance();
      parserService = ParserServiceImpl.getInstance();
    } catch (Exception e) {
      log.error(e);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      final int DEFAULT_PAGE_SIZE = 15;
      final int DEFAULT_PAGE = 1;

      int page = ParseRequestHelper.getParameterOrDefaultValue(req, "page", DEFAULT_PAGE);
      int size = ParseRequestHelper.getParameterOrDefaultValue(req, "size", DEFAULT_PAGE_SIZE);
      req.setAttribute("page", page);
      req.setAttribute("size", size);

      String[] parameters = {"id", "role", "name", "login", "email", "phone"};
      ParseRequestHelper.setExistingParametersAsAttributes(req, parameters);
      ParseRequestHelper.setPagingURLAttributes(req, page);

      Account seekingAccount = parserService.parseAccount(req);
      List<Account> accounts = accountService.getAccountsLike(seekingAccount, page, size);
      req.setAttribute("accounts", accounts);

      List<String> roles = new ArrayList<>();
      for (Role role : Role.values()) {
        roles.add(role.name());
      }
      req.setAttribute("roles", roles);

      req.getRequestDispatcher("/WEB-INF/manageAccounts.jsp").forward(req, resp);
    } catch (Exception e) {
      ErrorHelper.redirectToErrorPage(req, resp, e, SERVLET_ADDRESS);
      return;
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Action action = ParseRequestHelper.getRequestAction(req);

    try {
      Account receivedAccount = parserService.parseAccount(req);

      switch (action) {
        case ADD:
          accountService.addAccount(receivedAccount);
          log.info("Added " + receivedAccount);
          break;
        case SAVE:
          accountService.updateAccount(receivedAccount.getId(), receivedAccount);
          log.info("Updated " + receivedAccount);
          break;
        case DELETE:
          accountService.deleteAccount(receivedAccount.getId());
          log.info("Removed " + receivedAccount);
          break;
        default:
          log.error("ManageAccountsServlet received unknown action to \"doPost()\"");
          break;
      }
    } catch (Exception e) {
      ErrorHelper.redirectToErrorPage(req, resp, e, SERVLET_ADDRESS);
      return;
    }
    try {
      resp.sendRedirect(SERVLET_ADDRESS);
    } catch (IOException e) {
      log.error(e);
    }
  }
}
