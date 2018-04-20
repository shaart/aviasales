package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.exceptions.NoAvailableSeatsForTheFlight;
import com.epam.aviasales.repositories.FlightRepository;
import com.epam.aviasales.repositories.impl.FlightRepositoryImpl;
import com.epam.aviasales.services.FlightService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightServiceImpl implements FlightService {

  private static volatile FlightService instance;

  public static FlightService getInstance() {
    FlightService localInstance = instance;
    if (localInstance == null) {
      synchronized (FlightServiceImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new FlightServiceImpl();
        }
      }
    }

    return localInstance;
  }

  private static final FlightRepository flightRepository = FlightRepositoryImpl.getInstance();

  @Override
  public List<Flight> getFlights() {
    return flightRepository.getFlights(1L, Long.MAX_VALUE);
  }

  @Override
  public List<Flight> getFlightsPage(int page, int count) {
    return flightRepository.getFlightsPage(page, count);
  }

  @Override
  public Flight getFlightById(Long id) {
    return flightRepository.getFlightById(id);
  }

  @Override
  public void addFlight(Flight flight) {
    flightRepository.addFlight(flight);
  }

  @Override
  public void deleteFlight(Long id) {
    flightRepository.deleteFlight(id);
  }

  @Override
  public List<Flight> getFlights(Long airportIdFrom, Long airportIdTo, LocalDate date) {
    if (airportIdFrom == null || airportIdTo == null || date == null ||
        Objects.equals(airportIdFrom, airportIdTo)
        || date.toString().compareTo(LocalDate.now().toString()) < 0) {
      return Collections.emptyList();
    } else {
      List<Flight> list = flightRepository.getFlights(airportIdFrom, airportIdTo, date);
      return list.stream()
          .filter(x -> x.getFreeSeatEconomy() > 0 || x.getFreeSeatBusiness() > 0)
          .filter(x -> x.getDepartureTime().isAfter(LocalDateTime.now()))
          .collect(Collectors.toList());
    }
  }

  @Override
  public synchronized void updateFlight(Flight flight, Boolean isBusiness,
      Boolean increaseNumberOfSeats) throws NoAvailableSeatsForTheFlight {
    flight = flightRepository.getFlightById(flight.getId());
    flight = increaseNumberOfSeats ? localIncreaseNumberOfSeats(flight, isBusiness)
        : localDecreaseNumberOfSeats(flight, isBusiness);
    flightRepository.updateFlight(flight);
  }

  private Flight localIncreaseNumberOfSeats(Flight flight, Boolean isBusiness) {
    if (isBusiness) {
      flight.setFreeSeatBusiness(flight.getFreeSeatBusiness() + 1);
    } else {
      flight.setFreeSeatEconomy(flight.getFreeSeatEconomy() + 1);
    }
    return flight;
  }

  private Flight localDecreaseNumberOfSeats(Flight flight, Boolean isBusiness)
      throws NoAvailableSeatsForTheFlight {
    if (isBusiness) {
      if (flight.getFreeSeatBusiness() < 1) {
        throw new NoAvailableSeatsForTheFlight();
      }
      flight.setFreeSeatBusiness(flight.getFreeSeatBusiness() - 1);
    } else {
      if (flight.getFreeSeatEconomy() < 1) {
        throw new NoAvailableSeatsForTheFlight();
      }
      flight.setFreeSeatEconomy(flight.getFreeSeatEconomy() - 1);
    }
    return flight;
  }

  @Override
  public void updateFlight(Long id, Flight updatedFlight) {
    flightRepository.updateFlight(id, updatedFlight);
  }

  @Override
  public List<Flight> getFlights(Long fromId, Long toId) {
    return flightRepository.getFlights(fromId, toId);
  }

  @Override
  public boolean isExist(Long id) {
    return flightRepository.isExist(id);
  }

  @Override
  public List<Flight> getFlightsLike(Flight seekingFlight, int page, int size) {
    return flightRepository.getFlightsLike(seekingFlight, page, size);
  }
}
