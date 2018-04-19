package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.services.AirplaneService;
import com.epam.aviasales.services.AirportService;
import com.epam.aviasales.services.FlightService;
import com.epam.aviasales.services.ParserService;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class ParserServiceImpl implements ParserService {

  private static volatile ParserService instance;

  private FlightService flightService;
  private AirportService airportsService;
  private AirplaneService airplaneService;

  public static ParserService getInstance() {
    ParserService localInstance = instance;
    if (localInstance == null) {
      synchronized (AirportServiceImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new ParserServiceImpl();
        }
      }
    }

    return localInstance;
  }

  private ParserServiceImpl() {
    flightService = FlightServiceImpl.getInstance();
    airportsService = AirportServiceImpl.getInstance();
    airplaneService = AirplaneServiceImpl.getInstance();
  }

  public Object parseParameter(String parameter, Class type) {
    if (parameter == null || parameter.trim().isEmpty()) {
      return null;
    }

    if (type == Long.class) {
      return Long.valueOf(parameter);
    } else if (type == Integer.class) {
      return Integer.valueOf(parameter);
    } else if (type == LocalDateTime.class) {
      return LocalDateTime.parse(parameter);
    } else if (type == Airplane.class) {
      return airplaneService.getAirplaneByName(parameter);
    } else if (type == Airport.class) {
      return airportsService.getAirportByName(parameter);
    }
    return null;
  }

  public Flight parseFlight(HttpServletRequest req) {
    Long id = (Long) parseParameter(req.getParameter("id"), Long.class);
    Airport fromAirport = (Airport) parseParameter(req.getParameter("fromAirport"), Airport.class);
    Airport toAirport = (Airport) parseParameter(req.getParameter("toAirport"), Airport.class);
    Airplane airplane = (Airplane) parseParameter(req.getParameter("airplane"), Airplane.class);
    LocalDateTime departureTime = (LocalDateTime) parseParameter(req.getParameter("departureTime"),
        LocalDateTime.class);
    LocalDateTime arrivalTime = (LocalDateTime) parseParameter(req.getParameter("arrivalTime"),
        LocalDateTime.class);
    Integer baseTicketPrice = (Integer) parseParameter(req.getParameter("baseTicketPrice"),
        Integer.class);
    Integer extraBaggagePrice = (Integer) parseParameter(req.getParameter("extraBaggagePrice"),
        Integer.class);
    Integer freeSeatEconomy = (Integer) parseParameter(req.getParameter("freeSeatEconomy"),
        Integer.class);
    Integer freeSeatBusiness = (Integer) parseParameter(req.getParameter("freeSeatBusiness"),
        Integer.class);

    return Flight.builder().id(id).fromAirport(fromAirport)
        .toAirport(toAirport)
        .airplane(airplane).departureTime(departureTime).arrivalTime(arrivalTime)
        .baseTicketPrice(baseTicketPrice).extraBaggagePrice(extraBaggagePrice)
        .freeSeatEconomy(freeSeatEconomy).freeSeatBusiness(freeSeatBusiness).build();
  }

  public static void main(String[] args) {
    ParserServiceImpl parserService = new ParserServiceImpl();
//    Long id = (Long)
    Long id = (Long) parserService.parseParameter("5", Long.class);
    System.out.println(id);
    Airport airport = (Airport) parserService.parseParameter("Moscow", Airport.class);
    System.out.println(airport);

  }
}