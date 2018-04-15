package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.services.AirplaneService;
import com.epam.aviasales.services.AirportService;
import com.epam.aviasales.services.impl.AirplaneServiceImpl;
import com.epam.aviasales.services.impl.AirportServiceImpl;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightRepository {

  private static final FlightRepository instance = new FlightRepository();

  public static FlightRepository getInstance() {
    return instance;
  }

  private static final Map<Long, Flight> FLIGHT_CACHE = new HashMap<>();

  private static final AirportService airportService = AirportServiceImpl.getInstance();
  private static final AirplaneService airplaneService = AirplaneServiceImpl.getInstance();

  static {
    final int MAX_DAY = 20;
    final int CACHE_COUNT = 100;

    for (int i = 1; i < CACHE_COUNT; i++) {
      FLIGHT_CACHE.put(
          Long.valueOf(i),
          new Flight(
              Long.valueOf(i),
              airportService.getById(1L),
              airportService.getById(2L),
              airplaneService.getById(2L),
              LocalDateTime
                  .of(2018, Month.APRIL, (1 + i > MAX_DAY) ? (1 + i) % MAX_DAY + 1 : (1 + i), 00,
                      00,
                      00),
              LocalDateTime
                  .of(2018, Month.APRIL, (2 + i > MAX_DAY) ? (2 + i) % MAX_DAY + 1 : (2 + i), 06,
                      00,
                      00),
              2000,
              500,
              15,
              5));
    }
  }

  public Flight getById(Long id) {
    return FLIGHT_CACHE.get(id);
  }

  public List<Flight> getFlights() {
    return getFlights(1, Integer.MAX_VALUE);
  }

  public List<Flight> getFlights(int page, int count) {

    List<Flight> flightList = new ArrayList<>();
    if (page <= 0 || count <= 0) {
      return flightList;
    }

    final int startI = (page - 1) * count + 1;
    final int REQ_NUM = count == Integer.MAX_VALUE ? count : startI + count;
    for (int i = startI; i < REQ_NUM; i++) {
      if (i >= FLIGHT_CACHE.size()) {
        break;
      }
      flightList.add(FLIGHT_CACHE.get(Long.valueOf(i)));
    }
    return flightList;
  }

  public void deleteById(Long id) {
  }

  public void delete(Flight flight) {
  }

  public void update(Flight flight) {
  }

  public boolean isExists(Flight flight) {
    return false;
  }

  public boolean isExists(Long id) {
    return false;
  }
}
