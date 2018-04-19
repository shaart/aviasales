package com.epam.aviasales.services;

import com.epam.aviasales.domain.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {

  List<Flight> getFlights();

  List<Flight> getFlights(Long fromId, Long toId);

  List<Flight> getFlightsPage(int page, int count);

  Flight getFlightById(Long id);

  void addFlight(Flight flight);

  void deleteFlight(Long id);

  List<Flight> getFlights(Long airportIdFrom, Long airportIdTo, LocalDate date);

  void updateFlight(Flight flight, Boolean isBusiness, Boolean increaseNumberOfSeats);
}
