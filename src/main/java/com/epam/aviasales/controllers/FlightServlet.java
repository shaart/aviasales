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

  @Override
  public void init() throws ServletException {
    flightsService = FlightServiceImpl.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    //TODO please use camelCase: airportFromId, airportToId
    Long id_airport_from = Long.parseLong(req.getParameter("flight_from"));
    Long id_airport_to = Long.parseLong(req.getParameter("flight_to"));
    LocalDate date = LocalDate.parse(req.getParameter("departure"));

    req.setAttribute("from", id_airport_from);
    req.setAttribute("to", id_airport_to);
    req.setAttribute("date", date);

    //TODO check style! there are no spaces between fields below. A good habit is pressing ctrl+alt+L - code formatting
    List<Flight> flightList = flightsService.getFlights(id_airport_from,id_airport_to,date);
    req.setAttribute("flights", flightList);
    req.getRequestDispatcher("/").forward(req, resp);
  }
}
