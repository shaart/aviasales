package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.services.FlightsService;
import com.epam.aviasales.services.impl.FlightsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FlightsServlet extends HttpServlet {
    private FlightsService flightsService;

  @Override
  public void init() throws ServletException {
    flightsService = FlightsServiceImpl.getInstance();
  }

  @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
      req.getRequestDispatcher("/").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        Integer id_airport_from = Integer.parseInt(req.getParameter("flight_from"));
        Integer id_airport_to = Integer.parseInt(req.getParameter("flight_to"));
        LocalDate date = LocalDate.parse(req.getParameter("departure"));

        req.setAttribute("from", id_airport_from);
        req.setAttribute("to", id_airport_to);
        req.setAttribute("date", date);

        List<Flight> flightList = flightsService.getFlights();
        req.setAttribute("flights", flightList);
        req.getRequestDispatcher("/").forward(req,resp);
    }
}
