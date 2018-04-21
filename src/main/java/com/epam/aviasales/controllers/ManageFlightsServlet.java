package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.services.AirplaneService;
import com.epam.aviasales.services.AirportService;
import com.epam.aviasales.services.FlightService;
import com.epam.aviasales.services.ParserService;
import com.epam.aviasales.services.impl.AirplaneServiceImpl;
import com.epam.aviasales.services.impl.AirportServiceImpl;
import com.epam.aviasales.services.impl.FlightServiceImpl;
import com.epam.aviasales.services.impl.ParserServiceImpl;
import com.epam.aviasales.util.Action;
import com.epam.aviasales.util.ParseRequestHelper;
import java.io.IOException;
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
  private ParserService parserService;

  @Override
  public void init() throws ServletException {
    try {
      flightService = FlightServiceImpl.getInstance();
      airportsService = AirportServiceImpl.getInstance();
      airplaneService = AirplaneServiceImpl.getInstance();
      parserService = ParserServiceImpl.getInstance();
    } catch (Exception e) {
      log.error(e);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      final int DEFAULT_PAGE_SIZE = 15;
      final int DEFAULT_PAGE = 1;

      int page = ParseRequestHelper.getParameterOrDefaultValue(req, "page", DEFAULT_PAGE);
      int size = ParseRequestHelper.getParameterOrDefaultValue(req, "size", DEFAULT_PAGE_SIZE);
      req.setAttribute("page", page);
      req.setAttribute("size", size);

      String[] parameters = {"id", "fromAirport", "toAirport", "airplane", "departureTime",
          "arrivalTime", "baseTicketPrice", "extraBaggagePrice", "freeSeatEconomy",
          "freeSeatBusiness"};
      ParseRequestHelper.setExistingParametersAsAttributes(req, parameters);
      ParseRequestHelper.setPagingURLAttributes(req, page);

      Flight seekingFlight = parserService.parseFlight(req);
      List<Flight> flights = flightService.getFlightsLike(seekingFlight, page, size);
      req.setAttribute("flights", flights);

      List<Airport> airports = airportsService.getAirports();
      req.setAttribute("airports", airports);

      List<Airplane> airplanes = airplaneService.getAirplanes();
      req.setAttribute("airplanes", airplanes);

      req.getRequestDispatcher("/WEB-INF/manageFlights.jsp").forward(req, resp);
    } catch (Exception e) {
      log.error(e.getCause(), e);
      req.setAttribute("error", e.toString());
      req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Action action = ParseRequestHelper.getRequestAction(req);
    try {
      Flight receivedFlight = parserService.parseFlight(req);

      switch (action) {
        case ADD:
          flightService.addFlight(receivedFlight);
          log.info("Added " + receivedFlight);
          break;
        case SAVE:
          flightService.updateFlight(receivedFlight.getId(), receivedFlight);
          log.info("Updated " + receivedFlight);
          break;
        case DELETE:
          flightService.deleteFlight(receivedFlight.getId());
          log.info("Removed " + receivedFlight);
          break;
        default:
          log.error("ManageFlightsServlet received unknown action to \"doPost()\"");
          break;
      }
    } catch (Exception e) {
      log.error(e.getCause(), e);
      req.setAttribute("error", e.getMessage());
      req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
      return;
    }
    try {
      resp.sendRedirect("/manage/flights");
    } catch (IOException e) {
      log.error(e);
    }
  }
}
