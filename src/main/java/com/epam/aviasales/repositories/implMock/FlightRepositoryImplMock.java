package com.epam.aviasales.repositories.implMock;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.repositories.FlightRepository;
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
public class FlightRepositoryImplMock implements FlightRepository {

  private static volatile FlightRepository instance;

  public static FlightRepository getInstance() {
    FlightRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (FlightRepositoryImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new FlightRepositoryImplMock();
        }
      }
    }

    return localInstance;
  }


  private static final Map<Long, Flight> FLIGHT_CACHE = new HashMap<>();

  private static final AirportService airportService = AirportServiceImpl.getInstance();
  private static final AirplaneService airplaneService = AirplaneServiceImpl.getInstance();

  static {
    final int MAX_DAY = 20;
    final int CACHE_COUNT = 100;

    for (int i = 1; i < CACHE_COUNT; i++) {
      int arrivalDay = (2 + i > MAX_DAY) ? (2 + i) % MAX_DAY + 1 : (2 + i);
      int departureDay = (1 + i > MAX_DAY) ? (1 + i) % MAX_DAY + 1 : (1 + i);
      FLIGHT_CACHE.put(
          Long.valueOf(i),
          Flight.builder().id(Long.valueOf(i)).fromAirportId(airportService.getById(1L))
              .toAirportId(airportService.getById(2L)).airplaneId(airplaneService.getById(2L))
              .departureTime(LocalDateTime.of(2018, Month.APRIL, departureDay, 00, 00, 00))
              .arrivalTime(LocalDateTime
                  .of(2018, Month.APRIL, arrivalDay, 06, 00, 00)).baseTicketPrice(2000)
              .extraBaggagePrice(500).freeSeatEconomy(15)
              .freeSeatBusiness(5).build());
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

    final int startI = (page - 1) * count;
    for (int i = startI; i < startI + count; i++) {
      if (i >= FLIGHT_CACHE.size()) {
        break;
      }
      flightList.add(FLIGHT_CACHE.get(Long.valueOf(i)));
    }
    return flightList;
  }
}
