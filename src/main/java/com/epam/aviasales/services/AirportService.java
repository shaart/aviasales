package com.epam.aviasales.services;

import com.epam.aviasales.domain.Airport;

import java.util.List;

public interface AirportService {

  Airport getAirport(Long id);
  List<Airport> getAirports();
  void createAirport(Airport airport);
  void deleteAirport(Long id);

}