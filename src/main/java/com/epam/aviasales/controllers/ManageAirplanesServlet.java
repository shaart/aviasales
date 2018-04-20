package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.services.AirplaneService;
import com.epam.aviasales.services.ParserService;
import com.epam.aviasales.services.impl.AirplaneServiceImpl;
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
public class ManageAirplanesServlet extends HttpServlet {

  private ParserService parserService;
  private AirplaneService airplaneService;

  @Override
  public void init() throws ServletException {
    try {
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

      String[] parameters = {"id", "name", "economySeatsCount", "businessSeatsCount"};
      ParseRequestHelper.setExistingParametersAsAttributes(req, parameters);
      ParseRequestHelper.setPagingURLAttributes(req, page);

      Airplane seekingAirplane = parserService.parseAirplane(req);
      List<Airplane> airplanes = airplaneService.getAirplanesLike(seekingAirplane, page, size);
      req.setAttribute("airplanes", airplanes);

      req.getRequestDispatcher("manageAirplanes.jsp").forward(req, resp);
    } catch (Exception e) {
      log.error(e.getCause(), e);
      req.setAttribute("error", e.toString());
      req.getRequestDispatcher("../error.jsp").forward(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Action action = ParseRequestHelper.getRequestAction(req);
    try {
      Airplane receivedAirplane = parserService.parseAirplane(req);

      switch (action) {
        case ADD:
          airplaneService.addAirplane(receivedAirplane);
          log.info("Added " + receivedAirplane);
          break;
        case SAVE:
          airplaneService.updateAirplane(receivedAirplane.getId(), receivedAirplane);
          log.info("Updated " + receivedAirplane);
          break;
        case DELETE:
          airplaneService.deleteAirplane(receivedAirplane.getId());
          log.info("Removed " + receivedAirplane);
          break;
        default:
          log.error("ManageAirplanesServlet received unknown action to \"doPost()\"");
          break;
      }
    } catch (Exception e) {
      log.error(e.getCause(), e);
      resp.sendError(400);
      return;
    }
    try {
      resp.sendRedirect("/manage/airplanes");
    } catch (IOException e) {
      log.error(e);
    }
  }
}
