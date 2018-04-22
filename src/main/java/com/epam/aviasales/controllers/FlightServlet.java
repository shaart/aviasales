package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Airport;
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
    if (req.getSession().getAttribute("from") == null ||
        req.getSession().getAttribute("from").equals("")) {
      resp.sendRedirect("/");
      return;
    }
    idAirportFrom = Long.valueOf(req.getSession().getAttribute("from").toString());
    idAirportTo = Long.valueOf(req.getSession().getAttribute("to").toString());
    date = LocalDate.parse(req.getSession().getAttribute("date").toString());

    List<Flight> flightList = (List<Flight>)req.getSession().getAttribute("flights");

    req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    if(req.getSession().getAttribute("airports") == null ||
        ((List<Airport>)req.getSession().getAttribute("airports")).size() == 0){
      resp.sendRedirect("/");
      return;
    }

    if (req.getParameter("flight_from") == null ||
        req.getParameter("flight_from").equals("")) {
      resp.sendRedirect("/");
      return;
    }
    if(req.getParameter("date").equals("")){
      date=LocalDate.now();
    } else {
      date = LocalDate.parse(req.getParameter("date"));
    }

    idAirportFrom = Long.valueOf(req.getParameter("flight_from"));
    idAirportTo = Long.valueOf(req.getParameter("flight_to"));

    req.getSession().setAttribute("from", idAirportFrom);
    req.getSession().setAttribute("to", idAirportTo);
    req.getSession().setAttribute("date", date);

    List<Flight> flightList = flightsService.getFlights(idAirportFrom, idAirportTo, date);
    if (flightList.size() == 0) {
      req.setAttribute("error", "error.no_flights_today");
    }
    if (idAirportFrom == idAirportTo) {
      req.setAttribute("error", "error.the_same_airports");
    }
    req.getSession().setAttribute("flights", flightList);
    req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
  }
}
