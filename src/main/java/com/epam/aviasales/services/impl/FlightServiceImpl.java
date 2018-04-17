package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.repositories.FlightRepository;
import com.epam.aviasales.repositories.impl.FlightRepositoryImpl;
import com.epam.aviasales.services.FlightService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightServiceImpl implements FlightService {

  private static volatile FlightService instance;

  public static FlightService getInstance() {
    FlightService localInstance = instance;
    if (localInstance == null) {
      synchronized (FlightServiceImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new FlightServiceImpl();
        }
      }
    }

    return localInstance;
  }

  private static final FlightRepository flightRepository = FlightRepositoryImpl.getInstance();

  @Override
  public List<Flight> getFlights() {
    return flightRepository.getFlights(1L, Long.MAX_VALUE);
  }

  @Override
  public List<Flight> getFlightsPage(int page, int count) {
    return flightRepository.getFlightsPage(page, count);
  }

  @Override
  public Flight getFlightById(Long id) {
    return flightRepository.getFlightById(id);
  }

  @Override
  public void addFlight(Flight flight) {
    flightRepository.addFlight(flight);
  }

  @Override
  public void deleteFlight(Long id) {
    flightRepository.deleteFlight(id);
  }

  @Override
  public List<Flight> getFlights(Long airportIdFrom, Long airportIdTo, LocalDate date) {
    if (airportIdFrom == null || airportIdTo == null
        || date == null || Objects.equals(airportIdFrom, airportIdTo)
        || date.toString().compareTo(LocalDate.now().toString()) < 0) {
      //TODO return Collections.emptyList();
      return new ArrayList<>();
    }
    else{
      return flightRepository.getFlights(airportIdFrom, airportIdTo, date);
    }
  }

  @Override
  public List<Flight> getFlights(Long fromId, Long toId) {
    return flightRepository.getFlights(fromId, toId);
  }
}
