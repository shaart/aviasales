package com.epam.aviasales.services;

import com.epam.aviasales.domain.Flight;
import java.util.List;

public interface FlightService {

  List<Flight> getFlights();

  List<Flight> getFlights(int page, int count);

  Flight getFlightById(Long id);
}
