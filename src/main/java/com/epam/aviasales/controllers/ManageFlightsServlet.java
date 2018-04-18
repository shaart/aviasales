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
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;

@Log4j
public class ManageFlightsServlet extends HttpServlet {

  enum Action {NONE, ADD, SAVE, DELETE}

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
      log.error(e);
    }
  }

  private void setAttributeIfExistsParameter(HttpServletRequest request, String parameter) {
    String parameterValue = request.getParameter(parameter);
    if (parameterValue != null && !parameterValue.isEmpty()) {
      request.setAttribute(parameter, parameterValue);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      final int DEFAULT_PAGE_SIZE = 15;
      final int DEFAULT_PAGE = 1;

      String pageStr = req.getParameter("page");
      int page;
      if (pageStr == null) {
        page = DEFAULT_PAGE;
      } else {
        page = Integer.parseInt(pageStr);
      }
      String sizeStr = req.getParameter("size");
      int size;
      if (sizeStr == null) {
        size = DEFAULT_PAGE_SIZE;
      } else {
        size = Integer.parseInt(sizeStr);
      }

      req.setAttribute("page", page);
      req.setAttribute("size", size);

      setAttributeIfExistsParameter(req, "id");
      setAttributeIfExistsParameter(req, "fromAirport");
      setAttributeIfExistsParameter(req, "toAirport");
      setAttributeIfExistsParameter(req, "airplane");
      setAttributeIfExistsParameter(req, "departureTime");
      setAttributeIfExistsParameter(req, "arrivalTime");
      setAttributeIfExistsParameter(req, "baseTicketPrice");
      setAttributeIfExistsParameter(req, "extraBaggagePrice");
      setAttributeIfExistsParameter(req, "freeSeatEconomy");
      setAttributeIfExistsParameter(req, "freeSeatBusiness");

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

      String idParameter = req.getParameter("id");
      Long id = isNullOrEmpty(idParameter) ? null : Long.valueOf(idParameter);

      String fromAirportParameter = req.getParameter("fromAirport");

      Airport fromAirport = isNullOrEmpty(fromAirportParameter) ? null
          : airportsService.getAirportByName(fromAirportParameter);
      String toAirportParameter = req.getParameter("toAirport");

      Airport toAirport = isNullOrEmpty(toAirportParameter) ? null
          : airportsService.getAirportByName(toAirportParameter);

      String airplaneParameter = req.getParameter("airplane");
      Airplane airplane =
          isNullOrEmpty(airplaneParameter) ? null
              : airplaneService.getAirplaneByName(airplaneParameter);

      String departureTimeParameter = req.getParameter("departureTime");
      LocalDateTime departureTime =
          isNullOrEmpty(departureTimeParameter) ? null
              : LocalDateTime.parse(departureTimeParameter);

      String arrivalTimeParameter = req.getParameter("arrivalTime");
      LocalDateTime arrivalTime =
          isNullOrEmpty(arrivalTimeParameter) ? null : LocalDateTime.parse(arrivalTimeParameter);

      String baseTicketPriceParameter = req.getParameter("baseTicketPrice");
      Integer baseTicketPrice = isNullOrEmpty(baseTicketPriceParameter) ? null
          : Integer.parseInt(baseTicketPriceParameter);

      String extraBaggagePriceParameter = req.getParameter("extraBaggagePrice");
      Integer extraBaggagePrice = isNullOrEmpty(extraBaggagePriceParameter) ? null
          : Integer.parseInt(extraBaggagePriceParameter);

      String freeSeatEconomyParameter = req.getParameter("freeSeatEconomy");
      Integer freeSeatEconomy = isNullOrEmpty(freeSeatEconomyParameter) ? null
          : Integer.parseInt(freeSeatEconomyParameter);

      String freeSeatBusinessParameter = req.getParameter("freeSeatBusiness");
      Integer freeSeatBusiness = isNullOrEmpty(freeSeatBusinessParameter) ? null
          : Integer.parseInt(freeSeatBusinessParameter);

      Flight seekingFlight = Flight.builder().id(id).fromAirport(fromAirport)
          .toAirport(toAirport)
          .airplane(airplane).departureTime(departureTime).arrivalTime(arrivalTime)
          .baseTicketPrice(baseTicketPrice).extraBaggagePrice(extraBaggagePrice)
          .freeSeatEconomy(freeSeatEconomy).freeSeatBusiness(freeSeatBusiness).build();

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

  private boolean isNullOrEmpty(String parameter) {
    return parameter == null || parameter.isEmpty();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

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

    try {
      Long id;
      if (action == Action.ADD) {
        id = null;
      } else {
        id = Long.valueOf(req.getParameter("id"));
      }
      Airport fromAirport = airportsService
          .getAirportByName(req.getParameter("fromAirport"));
      Airport toAirport = airportsService
          .getAirportByName(req.getParameter("toAirport"));
      Airplane airplane = airplaneService
          .getAirplaneByName(req.getParameter("airplane"));

      LocalDateTime departureTime = LocalDateTime.parse(req.getParameter("departureTime"));
      LocalDateTime arrivalTime = LocalDateTime.parse(req.getParameter("arrivalTime"));
      Integer baseTicketPrice = Integer.parseInt(req.getParameter("baseTicketPrice"));
      Integer extraBaggagePrice = Integer.parseInt(req.getParameter("extraBaggagePrice"));
      Integer freeSeatEconomy = Integer.parseInt(req.getParameter("freeSeatEconomy"));
      Integer freeSeatBusiness = Integer.parseInt(req.getParameter("freeSeatBusiness"));

      Flight receivedFlight = Flight.builder().id(id).fromAirport(fromAirport)
          .toAirport(toAirport)
          .airplane(airplane).departureTime(departureTime).arrivalTime(arrivalTime)
          .baseTicketPrice(baseTicketPrice).extraBaggagePrice(extraBaggagePrice)
          .freeSeatEconomy(freeSeatEconomy).freeSeatBusiness(freeSeatBusiness).build();

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
}
