package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.services.AccountService;
import com.epam.aviasales.services.FlightService;
import com.epam.aviasales.services.PersonalDataService;
import com.epam.aviasales.services.TicketService;
import com.epam.aviasales.services.ParserService;
import com.epam.aviasales.services.impl.AccountServiceImpl;
import com.epam.aviasales.services.impl.FlightServiceImpl;
import com.epam.aviasales.services.impl.PersonalDataServiceImpl;
import com.epam.aviasales.services.impl.TicketServiceImpl;
import com.epam.aviasales.services.impl.ParserServiceImpl;
import com.epam.aviasales.util.Action;
import com.epam.aviasales.util.ErrorHelper;
import com.epam.aviasales.util.ParseRequestHelper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;

@Log4j
public class ManageTicketsServlet extends HttpServlet {

  private TicketService ticketService;
  private AccountService accountService;
  private PersonalDataService personalDataService;
  private FlightService flightService;
  private ParserService parserService;
  private static final String SERVLET_ADDRESS = "/manage/tickets";

  @Override
  public void init() throws ServletException {
    try {
      ticketService = TicketServiceImpl.getInstance();
      parserService = ParserServiceImpl.getInstance();
      accountService = AccountServiceImpl.getInstance();
      personalDataService = PersonalDataServiceImpl.getInstance();
      flightService = FlightServiceImpl.getInstance();
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

      String[] parameters = {"id", "personalDataPassport", "flightId", "accountName", "price",
          "isBusiness"};
      ParseRequestHelper.setExistingParametersAsAttributes(req, parameters);
      ParseRequestHelper.setPagingURLAttributes(req, page);

      Ticket seekingTicket = parserService.parseTicket(req);
      List<Ticket> tickets = ticketService.getTicketsLike(seekingTicket, page, size);
      req.setAttribute("tickets", tickets);

      List<Account> accounts = accountService.getAccounts();
      req.setAttribute("accounts", accounts);

      List<Flight> flights = flightService.getFlights();
      req.setAttribute("flights", flights);

      List<PersonalData> personalDatas = personalDataService.getPersonalDatas();
      req.setAttribute("personalDatas", personalDatas);

      req.getRequestDispatcher("/WEB-INF/manageTickets.jsp").forward(req, resp);
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
      Ticket receivedTicket = parserService.parseTicket(req);

      switch (action) {
        case ADD:
          ticketService.addTicket(receivedTicket);
          log.info("Added " + receivedTicket);
          break;
        case SAVE:
          ticketService.updateTicket(receivedTicket.getId(), receivedTicket);
          log.info("Updated " + receivedTicket);
          break;
        case DELETE:
          ticketService.deleteTicket(receivedTicket.getId());
          log.info("Removed " + receivedTicket);
          break;
        default:
          log.error("ManageTicketsServlet received unknown action to \"doPost()\"");
          break;
      }
    } catch (Exception e) {
      ErrorHelper.redirectToErrorPage(req, resp, e, SERVLET_ADDRESS);
      return;
    }
    try {
      resp.sendRedirect("/manage/tickets");
    } catch (IOException e) {
      ErrorHelper.redirectToErrorPage(req, resp, e, SERVLET_ADDRESS);
      return;
    }
  }
}
