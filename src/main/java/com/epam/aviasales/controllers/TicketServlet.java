package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.services.FlightsService;
import com.epam.aviasales.services.TicketService;
import com.epam.aviasales.services.impl.FlightsServiceImpl;
import com.epam.aviasales.services.impl.TicketServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TicketServlet extends HttpServlet {
    private TicketService ticketService;
    private FlightsService flightsService;

  @Override
  public void init() throws ServletException {
    ticketService = TicketServiceImpl.getInstance();
    flightsService = FlightsServiceImpl.getInstance();
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
      Flight flight = flightsService.getFlight(numberOfFlight);
      req.setAttribute("flight", flight);

      req.getRequestDispatcher("ticket.jsp").forward(req,resp);
    }
}
