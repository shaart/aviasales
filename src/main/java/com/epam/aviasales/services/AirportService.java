package com.epam.aviasales.services;

import com.epam.aviasales.domain.Airport;
import java.util.List;

public interface AirportService {

  List<Airport> getAirports();

  List<Airport> getAirports(int page, int count);

  Airport getAirportByName(String name);

  Airport getAirportById(Long id);

  void addAirport(Airport airport);

  void deleteAirport(Long id);

  List<Airport> getAirportsLike(Airport seekingAirport, int page, int size);

  void updateAirport(Long id, Airport receivedAirport);
}