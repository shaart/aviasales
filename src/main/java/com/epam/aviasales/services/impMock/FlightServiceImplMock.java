package com.epam.aviasales.services.impMock;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.repositories.FlightRepository;
import com.epam.aviasales.repositories.implMock.FlightRepositoryImplMock;
import com.epam.aviasales.services.FlightService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightServiceImplMock implements FlightService {

  private static volatile FlightService instance;

  public static FlightService getInstance() {
    FlightService localInstance = instance;
    if (localInstance == null) {
      synchronized (FlightServiceImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new FlightServiceImplMock();
        }
      }
    }

    return localInstance;
  }

  private static final FlightRepository flightRepository = FlightRepositoryImplMock.getInstance();

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
  public List<Flight> getFlights(Long fromId, Long toId) {
    return flightRepository.getFlights(fromId, toId);
  }
}
