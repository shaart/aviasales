package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.services.AirportService;
import com.epam.aviasales.services.impl.AirportServiceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("")
public class MainServlet extends HttpServlet {

  private AirportService airportService;
  private LocalDate currentDate;

  @Override
  public void init() throws ServletException {
    airportService = AirportServiceImpl.getInstance();
    currentDate = LocalDate.now();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    List<Airport> airportList = airportService.getAirports();
    req.setAttribute("airports", airportList);
    req.setAttribute("currentDate", currentDate);

    req.getRequestDispatcher("index.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
  }
}
