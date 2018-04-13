package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.services.FlightsService;
import com.epam.aviasales.services.impl.FlightsServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageFlightsServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

//    String paramId = req.getParameter("id");
//    if (paramId == null || paramId.isEmpty()) {
//
//    }

    FlightsService flightsService = FlightsServiceImpl.getInstance();
    List<Flight> flights = flightsService.getFlights();
    req.setAttribute("flights", flights);

    req.getRequestDispatcher("manageFlights.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    PrintWriter response = resp.getWriter();
    response.println("ID: " + req.getParameter("id"));
    response.println("Name: " + req.getParameter("name"));
    response.println("Action: " + req.getParameter("action"));
  }
}
