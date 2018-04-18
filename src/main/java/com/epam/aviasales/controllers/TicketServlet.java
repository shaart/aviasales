package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.services.PersonalDataService;
import com.epam.aviasales.services.TicketService;
import com.epam.aviasales.services.FlightService;
import com.epam.aviasales.services.impl.FlightServiceImpl;
import com.epam.aviasales.services.impl.PersonalDataServiceImpl;
import com.epam.aviasales.services.impl.TicketServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class TicketServlet extends HttpServlet {

  private static final int BUSINESS_PRICE_AS_CENTS = 140;
  private static final int ECONOMY_PRICE_AS_CENTS = 100;
  private TicketService ticketService;
  private FlightService flightsService;
  private PersonalDataService personalDataService;

  @Override
  public void init() throws ServletException {
    ticketService = TicketServiceImpl.getInstance();
    flightsService = FlightServiceImpl.getInstance();
    personalDataService = PersonalDataServiceImpl.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getParameter("account") == null) {
      resp.sendError(403);
    } else {
      doPost(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getParameter("selected_flight") != null) {
      Long numberOfFlight = Long.parseLong(req.getParameter("selected_flight"));
      Flight flight = flightsService.getFlightById(numberOfFlight);
      req.setAttribute("flight", flight);
      req.getSession().setAttribute("flight", flight);
    } else if (req.getSession().getAttribute("flight") != null) {
      req.setAttribute("flight", req.getSession().getAttribute("flight"));
      req.setAttribute("error", "error.type_personal_data");
    }

    if (req.getParameter("first_name") != null) {
      String firstName = req.getParameter("first_name").trim();
      String lastName = req.getParameter("last_name").trim();
      String passport = req.getParameter("passport").trim();
      if (firstName.equals("") || lastName.equals("") || passport.equals("") ||
          req.getParameter("birthday").trim().equals("")) {
        req.getRequestDispatcher("ticket.jsp").forward(req, resp);
      }
      LocalDate birthday = LocalDate.parse(req.getParameter("birthday"));
      if(birthday.toString().compareTo(LocalDate.now().toString()) >= 0){
        req.setAttribute("error", "error.birthday_after_now");
        req.getRequestDispatcher("ticket.jsp").forward(req, resp);
      }
      Boolean isBusiness = Boolean.parseBoolean(req.getParameter("isBusiness"));
      PersonalData personalData = new PersonalData(1L, firstName + " " + lastName, passport,
          birthday);
      try {
        personalDataService.addPersonalData(personalData);
      } catch (UnsupportedOperationException e) {
        req.setAttribute("error", "error.such_person_is_exist");
        req.getRequestDispatcher("ticket.jsp").forward(req, resp);
      }
      personalData = personalDataService.getPersonalDataByPassport(passport);
      Flight flight = (Flight) req.getSession().getAttribute("flight");
      Integer price =
          isBusiness ? flight.getBaseTicketPrice() * ECONOMY_PRICE_AS_CENTS
              : flight.getBaseTicketPrice() * BUSINESS_PRICE_AS_CENTS;
      Account account = (Account) req.getSession().getAttribute("account");
      Ticket ticket = new Ticket(1L, personalData, flight,
          account, price, isBusiness);
      ticketService.addTicket(ticket);
      req.getSession().setAttribute("ticket", ticket);
      req.getRequestDispatcher("/confirm").forward(req, resp);
    }
    req.getRequestDispatcher("ticket.jsp").forward(req, resp);
  }
}
