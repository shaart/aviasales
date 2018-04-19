package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Flight;

import com.epam.aviasales.services.FlightService;
import com.epam.aviasales.services.impl.FlightServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FlightServlet extends HttpServlet {

  private FlightService flightsService;
  private Long idAirportFrom;
  private Long idAirportTo;
  private LocalDate date;

  @Override
  public void init() throws ServletException {
    flightsService = FlightServiceImpl.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (!req.getSession().getAttribute("from").equals("")) {
      idAirportFrom = Long.parseLong(req.getSession().getAttribute("from").toString());
      idAirportTo = Long.parseLong(req.getSession().getAttribute("to").toString());
      date = LocalDate.parse(req.getSession().getAttribute("date").toString());

      List<Flight> flightList = flightsService.getFlights(idAirportFrom, idAirportTo, date);
      req.setAttribute("flights", flightList);
    }

    req.getRequestDispatcher("index.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    if (req.getParameter("departure").equals("")) {
      date = LocalDate.now();
    } else {
      date = LocalDate.parse(req.getParameter("departure"));
    }
    idAirportFrom = Long.parseLong(req.getParameter("flight_from"));
    idAirportTo = Long.parseLong(req.getParameter("flight_to"));

    req.getSession().setAttribute("from", idAirportFrom);
    req.getSession().setAttribute("to", idAirportTo);
    req.getSession().setAttribute("date", date);

    List<Flight> flightList = flightsService.getFlights(idAirportFrom, idAirportTo, date);
    req.setAttribute("flights", flightList);
    req.getRequestDispatcher("index.jsp").forward(req, resp);
  }
}
