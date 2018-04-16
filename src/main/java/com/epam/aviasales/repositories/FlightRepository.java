package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Flight;
import java.util.List;

public interface FlightRepository {

  Flight getFlightById(Long id);

  List<Flight> getFlights();

  List<Flight> getFlights(int page, int count);
}
