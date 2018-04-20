package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.repositories.AirportRepository;
import com.epam.aviasales.repositories.impl.AirportRepositoryImpl;
import com.epam.aviasales.services.AirportService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirportServiceImpl implements AirportService {

  private static volatile AirportService instance;

  public static AirportService getInstance() {
    AirportService localInstance = instance;
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

  private static final AirportRepository airportRepositoryMock = AirportRepositoryImpl.getInstance();

  @Override
  public List<Airport> getAirports() {
    return airportRepositoryMock.getAirports();
  }

  @Override
  public List<Airport> getAirports(int page, int count) {
    return airportRepositoryMock.getAirports(page, count);
  }

  @Override
  public Airport getAirportByName(String name) {
    return airportRepositoryMock.getAirportByName(name);
  }

  @Override
  public Airport getAirportById(Long id) {
    return airportRepositoryMock.getAirportById(id);
  }

  @Override
  public void addAirport(Airport airport) {
    airportRepositoryMock.addAirport(airport);
  }

  @Override
  public void deleteAirport(Long id) {
    airportRepositoryMock.deleteAirport(id);
  }

  @Override
  public List<Airport> getAirportsLike(Airport seekingAirport, int page, int size) {
    return airportRepositoryMock.getAirportsLike(seekingAirport, page, size);
  }

  @Override
  public void updateAirport(Long id, Airport receivedAirport) {
    airportRepositoryMock.updateAirport(id, receivedAirport);
  }
}
