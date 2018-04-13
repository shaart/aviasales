package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.services.FlightsService;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightsServiceImpl implements FlightsService {

  private static final FlightsService instance = new FlightsServiceImpl();

  public static FlightsService getInstance() {
    return instance;
  }

  public List<Flight> getFlights() {
    List<Flight> flightList = new ArrayList<Flight>();
    flightList.add(
        new Flight(1L,
            new Airport(1L, "Moscow"),
            new Airport(2L, "Saint-Petersburg"),
            new Airplane(1L, "Don Corleone", 5, 5),
            LocalDateTime.of(2018, Month.APRIL, 10, 20, 00, 00),
            LocalDateTime.of(2018, Month.APRIL, 11, 06, 00, 00),
            1000,
            500,
            5,
            5));
    flightList.add(
        new Flight(2L,
            new Airport(2L, "Saint-Petersburg"),
            new Airport(1L, "Moscow"),
            new Airplane(2L, "Skywalker", 15, 35),
            LocalDateTime.of(2018, Month.APRIL, 11, 20, 00, 00),
            LocalDateTime.of(2018, Month.APRIL, 12, 06, 00, 00),
            2000,
            500,
            15,
            5));

    return flightList;
  }
}
