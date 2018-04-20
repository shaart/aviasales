package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.services.AirportService;
import com.epam.aviasales.services.ParserService;
import com.epam.aviasales.services.impl.AirportServiceImpl;
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
public class ManageAirportsServlet extends HttpServlet {

  private ParserService parserService;
  private AirportService airportService;

  @Override
  public void init() throws ServletException {
    try {
      airportService = AirportServiceImpl.getInstance();
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

      String[] parameters = {"id", "name"};
      ParseRequestHelper.setExistingParametersAsAttributes(req, parameters);
      ParseRequestHelper.setPagingURLAttributes(req, page);

      Airport seekingAirport = parserService.parseAirport(req);
      List<Airport> airports = airportService.getAirportsLike(seekingAirport, page, size);
      req.setAttribute("airports", airports);

      req.getRequestDispatcher("/WEB-INF/manageAirports.jsp").forward(req, resp);
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
      Airport receivedAirport = parserService.parseAirport(req);

      switch (action) {
        case ADD:
          airportService.addAirport(receivedAirport);
          log.info("Added " + receivedAirport);
          break;
        case SAVE:
          airportService.updateAirport(receivedAirport.getId(), receivedAirport);
          log.info("Updated " + receivedAirport);
          break;
        case DELETE:
          airportService.deleteAirport(receivedAirport.getId());
          log.info("Removed " + receivedAirport);
          break;
        default:
          log.error("ManageAirportsServlet received unknown action to \"doPost()\"");
          break;
      }
    } catch (Exception e) {
      log.error(e.getCause(), e);
      resp.sendError(400);
      return;
    }
    try {
      resp.sendRedirect("/manage/airports");
    } catch (IOException e) {
      log.error(e);
    }
  }
}
