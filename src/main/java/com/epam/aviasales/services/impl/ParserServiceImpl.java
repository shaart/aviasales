package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.services.AirplaneService;
import com.epam.aviasales.services.AirportService;
import com.epam.aviasales.services.FlightService;
import com.epam.aviasales.services.ParserService;
import com.epam.aviasales.util.CastType;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j;

@Log4j
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

  public static void main(String[] args) {
    ParserServiceImpl parserService = new ParserServiceImpl();

    Airport airport = parserService.parseParameter("Moscow", CastType.AIRPORT);
    System.out.println(airport);
    Airplane airplane = parserService.parseParameter("SU-2", CastType.AIRPLANE);
    System.out.println(airplane);
  }

  public <T> T parseParameter(String parameter, CastType type) {
    if (parameter == null || parameter.trim().isEmpty()) {
      return null;
    }

    try {
      switch (type) {
        case LONG:
          return (T) Long.valueOf(parameter);
        case INTEGER:
          return (T) Integer.valueOf(parameter);
        case LOCAL_DATE_TIME:
          return (T) LocalDateTime.parse(parameter);
        case AIRPLANE:
          return (T) airplaneService.getAirplaneByName(parameter);
        case AIRPORT:
          return (T) airportsService.getAirportByName(parameter);
        case STRING:
          return (T) parameter;
        default:
          log.error("Received not implemented (unknown) CastType");
          return null;
      }
    } catch (ClassCastException e) {
      log.error(e.getCause(), e);
      return null;
    }
  }

  public Flight parseFlight(HttpServletRequest req) {
    Long id = parseParameter(req.getParameter("id"), CastType.LONG);
    Airport fromAirport = parseParameter(req.getParameter("fromAirport"), CastType.AIRPORT);
    Airport toAirport = parseParameter(req.getParameter("toAirport"), CastType.AIRPORT);
    Airplane airplane = parseParameter(req.getParameter("airplane"), CastType.AIRPLANE);
    LocalDateTime departureTime = parseParameter(req.getParameter("departureTime"),
        CastType.LOCAL_DATE_TIME);
    LocalDateTime arrivalTime = parseParameter(req.getParameter("arrivalTime"),
        CastType.LOCAL_DATE_TIME);
    Integer baseTicketPrice = parseParameter(req.getParameter("baseTicketPrice"), CastType.INTEGER);
    Integer extraBaggagePrice = parseParameter(req.getParameter("extraBaggagePrice"),
        CastType.INTEGER);
    Integer freeSeatEconomy = parseParameter(req.getParameter("freeSeatEconomy"), CastType.INTEGER);
    Integer freeSeatBusiness = parseParameter(req.getParameter("freeSeatBusiness"),
        CastType.INTEGER);

    return Flight.builder().id(id).fromAirport(fromAirport)
        .toAirport(toAirport)
        .airplane(airplane).departureTime(departureTime).arrivalTime(arrivalTime)
        .baseTicketPrice(baseTicketPrice).extraBaggagePrice(extraBaggagePrice)
        .freeSeatEconomy(freeSeatEconomy).freeSeatBusiness(freeSeatBusiness).build();
  }

  @Override
  public Airport parseAirport(HttpServletRequest req) {
    Long id = parseParameter(req.getParameter("id"), CastType.LONG);
    String name = parseParameter(req.getParameter("name"), CastType.STRING);

    return Airport.builder().id(id).name(name).build();
  }

  @Override
  public Airplane parseAirplane(HttpServletRequest req) {
    Long id = parseParameter(req.getParameter("id"), CastType.LONG);
    String name = parseParameter(req.getParameter("name"), CastType.STRING);
    Integer economySeatsCount = parseParameter(req.getParameter("economySeatsCount"), CastType.INTEGER);
    Integer businessSeatsCount = parseParameter(req.getParameter("businessSeatsCount"), CastType.INTEGER);

    return Airplane.builder().id(id).name(name).economySeatsCount(economySeatsCount)
        .businessSeatsCount(businessSeatsCount).build();
  }
}