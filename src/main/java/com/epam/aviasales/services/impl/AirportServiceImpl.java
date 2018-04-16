package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.repositories.AirportRepository;
import com.epam.aviasales.services.AirportService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirportServiceImpl implements AirportService {

  private static final AirportService instance = new AirportServiceImpl();

  private final AirportRepository airportRepository = AirportRepository.getInstance();

  public static AirportService getInstance() {
    return instance;
  }

  @Override
  public Airport getAirport(Long id) {
    return airportRepository.getAirport(id);
  }

  @Override
  public List<Airport> getAirports() {
    return airportRepository.getAirports();
  }

  @Override
  public void createAirport(Airport airport) {
    airportRepository.insert(airport);
  }

  @Override
  public void deleteAirport(Long id) {
    airportRepository.delete(id);
  }
}
