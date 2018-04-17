package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.services.TicketService;
import com.epam.aviasales.services.FlightService;
import com.epam.aviasales.services.impl.FlightServiceImpl;
import com.epam.aviasales.services.impl.TicketServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TicketServlet extends HttpServlet {

  private TicketService ticketService;
  private FlightService flightsService;

  @Override
  public void init() throws ServletException {
    ticketService = TicketServiceImpl.getInstance();
    flightsService = FlightServiceImpl.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Long numberOfFlight = Long.parseLong(req.getParameter("selected_flight"));
    //TODO why do you like to write redundant lines? :)
    //TODO req.setAttribute("flight", flightsService.getFlightById(numberOfFlight));
    Flight flight = flightsService.getFlightById(numberOfFlight);
    req.setAttribute("flight", flight);

    req.getRequestDispatcher("ticket.jsp").forward(req, resp);
  }
}
