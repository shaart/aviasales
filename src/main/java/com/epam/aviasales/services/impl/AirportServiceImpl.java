package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.repositories.AirportRepository;
import com.epam.aviasales.repositories.implMock.AirportRepositoryImplMock;
import com.epam.aviasales.services.AirportService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirportServiceImpl implements AirportService {

  private static final AirportService instance = new AirportServiceImpl();

  public static AirportService getInstance() {
    return instance;
  }

  private static final AirportRepository airportRepository = AirportRepositoryImplMock
      .getInstance();

  @Override
  public List<Airport> getAirports() {
    return airportRepository.getAirports(1, 20);
  }

  @Override
  public List<Airport> getAirports(int page, int count) {
    return airportRepository.getAirports(page, count);
  }

  @Override
  public Airport getByName(String name) {
    return airportRepository.getAirportByName(name);
  }

  @Override
  public Airport getById(Long id) {
    return airportRepository.getAirportById(id);
  }
}
