package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.services.AccountService;
import com.epam.aviasales.services.AirplaneService;
import com.epam.aviasales.services.AirportService;
import com.epam.aviasales.services.FlightService;
import com.epam.aviasales.services.ParserService;
import com.epam.aviasales.services.PersonalDataService;
import com.epam.aviasales.util.CastType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j;

@Log4j
public class ParserServiceImpl implements ParserService {

  private static volatile ParserService instance;

  private FlightService flightService;
  private AirportService airportsService;
  private AirplaneService airplaneService;
  private AccountService accountService;
  private PersonalDataService personalDataService;

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
    accountService = AccountServiceImpl.getInstance();
    personalDataService = PersonalDataServiceImpl.getInstance();
  }

  public <T> T parseParameter(final String parameter, CastType type) {
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
        case LOCAL_DATE:
          return (T) LocalDate.parse(parameter);
        case FLIGHT:
          return (T) flightService.getFlightById(Long.valueOf(parameter));
        case ACCOUNT:
          return (T) accountService.getAccountByLogin(parameter);
        case BOOLEAN:
          switch (parameter) {
            case "on":
            case "1":
              return (T) Boolean.TRUE;
            case "off":
            case "0":
              return (T) Boolean.FALSE;
            default:
              return (T) Boolean.valueOf(parameter);
          }
        case PERSONAL_DATA:
          return (T) personalDataService.getPersonalDataByPassport(parameter);
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
    Integer economySeatsCount = parseParameter(req.getParameter("economySeatsCount"),
        CastType.INTEGER);
    Integer businessSeatsCount = parseParameter(req.getParameter("businessSeatsCount"),
        CastType.INTEGER);

    return Airplane.builder().id(id).name(name).economySeatsCount(economySeatsCount)
        .businessSeatsCount(businessSeatsCount).build();
  }

  @Override
  public PersonalData parsePersonalData(HttpServletRequest req) {
    Long id = parseParameter(req.getParameter("id"), CastType.LONG);
    String name = parseParameter(req.getParameter("name"), CastType.STRING);
    String passport = parseParameter(req.getParameter("passport"), CastType.STRING);
    LocalDate dateOfBirth = parseParameter(req.getParameter("dateOfBirth"), CastType.LOCAL_DATE);

    return PersonalData.builder().id(id).name(name).passport(passport).dateOfBirth(dateOfBirth)
        .build();
  }

  @Override
  public Ticket parseTicket(HttpServletRequest req) {
    Long id = parseParameter(req.getParameter("id"), CastType.LONG);
    PersonalData personalData = parseParameter(req.getParameter("personalDataPassport"),
        CastType.PERSONAL_DATA);
    Flight flight = parseParameter(req.getParameter("flightId"), CastType.FLIGHT);
    Account account = parseParameter(req.getParameter("accountName"), CastType.ACCOUNT);
    Integer price = parseParameter(req.getParameter("price"), CastType.INTEGER);
    Boolean isBusiness = parseParameter(req.getParameter("isBusiness"), CastType.BOOLEAN);

    return Ticket.builder().id(id).personalData(personalData).flight(flight).account(account)
        .price(price).isBusiness(isBusiness).build();
  }
}