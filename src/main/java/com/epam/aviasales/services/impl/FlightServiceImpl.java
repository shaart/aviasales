package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.repositories.FlightRepository;
import com.epam.aviasales.services.FlightService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightServiceImpl implements FlightService {

  private static final FlightService instance = new FlightServiceImpl();
  private static final FlightRepository flightRepository = FlightRepository.getInstance();

  public static FlightService getInstance() {
    return instance;
  }

  public List<Flight> getFlights() {
    return flightRepository.getFlights(1, Integer.MAX_VALUE);
  }

  @Override
  public List<Flight> getFlights(int page, int count) {
    return flightRepository.getFlights(page, count);
  }

  @Override
  public Flight getById(Long id) {
    return flightRepository.getById(id);
  }
}
