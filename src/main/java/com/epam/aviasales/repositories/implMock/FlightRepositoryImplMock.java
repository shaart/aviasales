package com.epam.aviasales.repositories.implMock;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.repositories.FlightRepository;
import com.epam.aviasales.services.AirplaneService;
import com.epam.aviasales.services.AirportService;
import com.epam.aviasales.services.impMock.AirplaneServiceImplMock;
import com.epam.aviasales.services.impMock.AirportServiceImplMock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class FlightRepositoryImplMock implements FlightRepository {

  private static volatile FlightRepository instance;
  private static final Map<Long, Flight> FLIGHT_CACHE = new HashMap<>();
  private static final AirportService airportService = AirportServiceImplMock.getInstance();
  private static final AirplaneService airplaneService = AirplaneServiceImplMock.getInstance();

  public static FlightRepository getInstance() {
    FlightRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (FlightRepositoryImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new FlightRepositoryImplMock();
        }
      }
    }

    return localInstance;
  }

  private FlightRepositoryImplMock() {
    final int MAX_DAY = 20;
    final int CACHE_COUNT = 100;

    for (int i = 1; i < CACHE_COUNT; i++) {
      int arrivalDay = (2 + i > MAX_DAY) ? (2 + i) % MAX_DAY + 1 : (2 + i);
      int departureDay = (1 + i > MAX_DAY) ? (1 + i) % MAX_DAY + 1 : (1 + i);
      FLIGHT_CACHE.put(
          Long.valueOf(i),
          Flight.builder().id(Long.valueOf(i)).fromAirport(airportService.getAirportById(1L))
              .toAirport(airportService.getAirportById(2L))
              .airplane(airplaneService.getAirplaneById(2L))
              .departureTime(LocalDateTime.of(2018, Month.APRIL, departureDay, 00, 00, 00))
              .arrivalTime(LocalDateTime
                  .of(2018, Month.APRIL, arrivalDay, 06, 00, 00)).baseTicketPrice(2000)
              .extraBaggagePrice(500).freeSeatEconomy(15)
              .freeSeatBusiness(5).build());
    }
  }

  @Override
  public Flight getFlightById(Long id) {
    return FLIGHT_CACHE.get(id);
  }

  @Override
  public List<Flight> getFlights() {
    return getFlightsPage(1, Integer.MAX_VALUE);
  }

  @Override
  public void addFlight(Flight flight) {
    FLIGHT_CACHE.put(flight.getId(), flight);
  }

  @Override
  public void deleteFlight(Long id) {
    FLIGHT_CACHE.remove(id);
  }

  @Override
  public boolean isExist(Long id) {
    return FLIGHT_CACHE.containsKey(id);
  }

  @Override
  public List<Flight> getFlights(Long fromId, Long toId) {
    throw new UnsupportedOperationException("List<Flight> getFlights(Long fromId, Long toId)");
  }

  @Override
  public void updateFlight(Long id, Flight updatedFlight) {
    FLIGHT_CACHE.put(id, updatedFlight);
  }

  @Override
  public List<Flight> getFlightsLike(Flight seekingFlight, int page, int size) {

    List<Flight> flightList = new ArrayList<>();
    if (size <= 0) {
      return flightList;
    }
    if (page <= 0) {
      page = 1;
    }

    final int startI = (page - 1) * size;
    for (int i = startI; i < startI + size; i++) {
      if (i >= FLIGHT_CACHE.size()) {
        break;
      }
      Flight flight = FLIGHT_CACHE.get(Long.valueOf(i));
      if (flight != null) {
        if ((seekingFlight.getId() == null || seekingFlight.getId().equals(flight.getId()))
            && (seekingFlight.getFromAirport() == null || seekingFlight.getFromAirport()
            .equals(flight.getFromAirport()))
            && (seekingFlight.getToAirport() == null || seekingFlight.getToAirport()
            .equals(flight.getToAirport()))
            && (seekingFlight.getAirplane() == null || seekingFlight.getAirplane()
            .equals(flight.getAirplane()))
            && (seekingFlight.getDepartureTime() == null || seekingFlight.getDepartureTime()
            .equals(flight.getDepartureTime()))
            && (seekingFlight.getArrivalTime() == null || seekingFlight.getArrivalTime()
            .equals(flight.getArrivalTime()))
            && (seekingFlight.getBaseTicketPrice() == null || seekingFlight.getBaseTicketPrice()
            .equals(flight.getBaseTicketPrice()))
            && (seekingFlight.getExtraBaggagePrice() == null || seekingFlight.getExtraBaggagePrice()
            .equals(flight.getExtraBaggagePrice()))
            && (seekingFlight.getFreeSeatEconomy() == null || seekingFlight.getFreeSeatEconomy()
            .equals(flight.getFreeSeatEconomy()))
            && (seekingFlight.getFreeSeatBusiness() == null || seekingFlight.getFreeSeatBusiness()
            .equals(flight.getFreeSeatBusiness()))) {
          flightList.add(flight);
        }
      }
    }
    return flightList;
  }

  @Override
  public List<Flight> getFlights(Long airportIdFrom, Long airportIdTo, LocalDate date) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void updateFlight(Flight flight) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Flight> getFlightsPage(int page, int count) {

    List<Flight> flightList = new ArrayList<>();
    if (page <= 0 || count <= 0) {
      return flightList;
    }

    final int startI = (page - 1) * count;
    for (int i = startI; i < startI + count; i++) {
      if (i >= FLIGHT_CACHE.size()) {
        break;
      }
      Flight flight = FLIGHT_CACHE.get(Long.valueOf(i));
      if (flight != null) {
        flightList.add(flight);
      }
    }
    return flightList;
  }
}
