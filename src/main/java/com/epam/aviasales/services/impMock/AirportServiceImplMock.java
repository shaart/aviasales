package com.epam.aviasales.services.impMock;

import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.repositories.AirportRepository;
import com.epam.aviasales.repositories.implMock.AirportRepositoryImplMock;
import com.epam.aviasales.services.AirportService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirportServiceImplMock implements AirportService {

  private static volatile AirportService instance;

  public static AirportService getInstance() {
    AirportService localInstance = instance;
    if (localInstance == null) {
      synchronized (AirportServiceImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new AirportServiceImplMock();
        }
      }
    }

    return localInstance;
  }

  private static final AirportRepository airportRepository = AirportRepositoryImplMock
      .getInstance();

  @Override
  public List<Airport> getAirports() {
    return airportRepository.getAirports();
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
    airportRepository.addAirport(airport);
  }

  @Override
  public void deleteAirport(Long id) {
    airportRepository.deleteAirport(id);
  }

  @Override
  public List<Airport> getAirportsLike(Airport seekingAirport, int page, int size) {
    return airportRepository.getAirportsLike(seekingAirport, page, size);
  }

  @Override
  public void updateAirport(Long id, Airport receivedAirport) {
    airportRepository.updateAirport(id, receivedAirport);
  }
}
