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

  private static volatile AirportService instance;

  public static AirportService getInstance() {
    AirportServiceImpl localInstance = instance;
    if (localInstance == null) {
      synchronized (AirportServiceImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new AirportServiceImpl();
        }
      }
    }

    return localInstance;
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
  public Airport getAirportByName(String name) {
    return airportRepository.getAirportByName(name);
  }

  @Override
  public Airport getAirportById(Long id) {
    return airportRepository.getAirportById(id);
  }

  @Override
  public void addAirport(Airport airport) {
    airportRepository.insert(airport);
  }

  @Override
  public void deleteAirport(Long id) {
    airportRepository.delete(id);
  }
}
