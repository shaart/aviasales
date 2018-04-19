package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository {

  Flight getFlightById(Long id);

  List<Flight> getFlights();

  List<Flight> getFlightsPage(int page, int count);

  void addFlight(Flight flight);

  void deleteFlight(Long id);

  boolean isExist(Long id);

  List<Flight> getFlights(Long fromId, Long toId);

  List<Flight> getFlights(Long airportIdFrom, Long airportIdTo, LocalDate date);

  void updateFlight(Flight flight);
}
