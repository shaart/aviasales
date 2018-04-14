package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.services.AirplaneService;
import com.epam.aviasales.services.AirportService;
import com.epam.aviasales.services.FlightService;
import com.epam.aviasales.services.impl.AirplaneServiceImpl;
import com.epam.aviasales.services.impl.AirportServiceImpl;
import com.epam.aviasales.services.impl.FlightServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;

@Log4j
public class ManageFlightsServlet extends HttpServlet {

  private FlightService flightService;
  private AirportService airportsService;
  private AirplaneService airplaneService;

  @Override
  public void init() throws ServletException {
    try {
      flightService = FlightServiceImpl.getInstance();
      airportsService = AirportServiceImpl.getInstance();
      airplaneService = AirplaneServiceImpl.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      List<Flight> flights = flightService.getFlights();
      req.setAttribute("flights", flights);
      List<Airport> airports = airportsService.getAirports();
      req.setAttribute("airports", airports);

      req.getRequestDispatcher("manageFlights.jsp").forward(req, resp);
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("error", e.toString());
      req.getRequestDispatcher("../error.jsp").forward(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    PrintWriter responseOut = resp.getWriter();

    String actionSave = req.getParameter("actionSave");
    String actionDelete = req.getParameter("actionDelete");
    String action = actionSave != null ? actionSave : actionDelete;
    responseOut.println("Action: " + action);
    try {
      Long id = Long.valueOf(req.getParameter("id"));

      if (action == null) {
        return;
      }
      switch (action.toLowerCase()) {
        case "save":
          Airport fromAirport = airportsService.getByName(req.getParameter("fromAirport"));
          Airport toAirport = airportsService.getByName(req.getParameter("toAirport"));
          Airplane airplane = airplaneService.getByName(req.getParameter("airplane"));
          LocalDateTime departureTime = LocalDateTime.parse(req.getParameter("departureTime"));
          LocalDateTime arrivalTime = LocalDateTime.parse(req.getParameter("arrivalTime"));
          Integer baseTicketPrice = Integer.parseInt(req.getParameter("baseTicketPrice"));
          Integer extraBaggagePrice = Integer.parseInt(req.getParameter("extraBaggagePrice"));
          Integer freeSeatEconomy = Integer.parseInt(req.getParameter("freeSeatEconomy"));
          Integer freeSeatBusiness = Integer.parseInt(req.getParameter("freeSeatBusiness"));

          Flight reqFlight = new Flight(
              id,
              fromAirport,
              toAirport,
              airplane,
              departureTime,
              arrivalTime,
              baseTicketPrice,
              extraBaggagePrice,
              freeSeatEconomy,
              freeSeatBusiness);

          responseOut.println(reqFlight);
          break;
        case "delete":
          Flight flight = new Flight();
          flight.setId(id);

          break;
        default:
          return;
      }
    } catch (Exception e) {
      // out to user
      return;

    }
  }
}
