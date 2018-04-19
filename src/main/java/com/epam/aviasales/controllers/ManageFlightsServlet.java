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

      int page = getParameterOrDefaultValue(req, "page", DEFAULT_PAGE);
      int size = getParameterOrDefaultValue(req, "size", DEFAULT_PAGE_SIZE);
      req.setAttribute("page", page);
      req.setAttribute("size", size);

      String[] parameters = {"id", "fromAirport", "toAirport", "airplane", "departureTime",
          "arrivalTime", "baseTicketPrice", "extraBaggagePrice", "freeSeatEconomy",
          "freeSeatBusiness"};
      setExistingParametersAsAttributes(req, parameters);
      setPagingURLAttributes(req, page);

      Flight seekingFlight = parserService.parseFlight(req);
      List<Flight> flights = flightService.getFlightsLike(seekingFlight, page, size);
      req.setAttribute("flights", flights);

      List<Airport> airports = airportsService.getAirports();
      req.setAttribute("airports", airports);

      List<Airplane> airplanes = airplaneService.getAirplanes();
      req.setAttribute("airplanes", airplanes);

      req.getRequestDispatcher("manageFlights.jsp").forward(req, resp);
    } catch (Exception e) {
      log.error(e.getCause(), e);
      req.setAttribute("error", e.toString());
      req.getRequestDispatcher("../error.jsp").forward(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Action action = getAction(req);
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
      log.error(e);
      resp.sendError(400);
      return;
    }
    try {
      resp.sendRedirect("/manage/flights");
    } catch (IOException e) {
      log.error(e);
    }
  }

  private Action getAction(HttpServletRequest req) {
    Action action = Action.NONE;
    String actionAdd = req.getParameter("actionAdd");
    String actionSave = req.getParameter("actionSave");
    String actionDelete = req.getParameter("actionDelete");
    if (actionAdd != null && !actionAdd.isEmpty()) {
      action = Action.ADD;
    } else if (actionSave != null && !actionSave.isEmpty()) {
      action = Action.SAVE;
    } else if (actionDelete != null && !actionDelete.isEmpty()) {
      action = Action.DELETE;
    }
    return action;
  }

  private void setExistingParametersAsAttributes(HttpServletRequest request, String[] parameters) {
    for (String parameter : parameters) {
      String parameterValue = request.getParameter(parameter);
      if (parameterValue != null && !parameterValue.trim().isEmpty()) {
        request.setAttribute(parameter, parameterValue);
      }
    }
  }

  private int getParameterOrDefaultValue(HttpServletRequest req, String parameter,
      final int DEFAULT) {
    String paramStr = req.getParameter(parameter);
    return paramStr == null ? DEFAULT : Integer.parseInt(paramStr);
  }

  private void setPagingURLAttributes(HttpServletRequest req, int page) {
    String reqParameters = req.getQueryString();
    if (reqParameters == null) {
      reqParameters = "?";
    } else {
      reqParameters = "?" + reqParameters;
    }
    String prevPage;
    String currPage;
    String nextPage;
    if (reqParameters.contains("page")) {
      prevPage = reqParameters.replaceFirst("page=[^&]*", "page=" + (page - 1));
      currPage = reqParameters;
      nextPage = reqParameters.replaceFirst("page=[^&]*", "page=" + (page + 1));
    } else {
      prevPage = reqParameters + "&page=" + (page - 1);
      currPage = reqParameters + "&page=" + (page);
      nextPage = reqParameters + "&page=" + (page + 1);
    }

    req.setAttribute("prevPageURL", prevPage);
    req.setAttribute("currPageURL", currPage);
    req.setAttribute("nextPageURL", nextPage);
  }

  private boolean isNullOrEmpty(String parameter) {
    return parameter == null || parameter.isEmpty();
  }
}
