package com.epam.aviasales.services;

import com.epam.aviasales.domain.Flight;
import java.util.List;

public interface FlightService {

  List<Flight> getFlights();

  List<Flight> getFlights(int page, int count);

  Flight getById(Long id);

  void delete(Flight flight);

  void deleteByID(Long id);

  void update(Flight flight);

  boolean isExists(Flight flight);

  boolean isExists(Long id);
}
