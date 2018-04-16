package com.epam.aviasales.services;

import com.epam.aviasales.domain.Flight;
import java.util.List;

public interface FlightsService {

  void createFlight(Flight flight);
  void deleteFlight(Long id);
  Flight getFlight(Long id);
  List<Flight> getFlights(Long from, Long to);
  List<Flight> getFlights();
}