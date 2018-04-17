package com.epam.aviasales.repositories.implMock;

import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.repositories.TicketRepository;
import com.epam.aviasales.services.AccountService;
import com.epam.aviasales.services.FlightService;
import com.epam.aviasales.services.PersonalDataService;
import com.epam.aviasales.services.impl.AccountServiceImpl;
import com.epam.aviasales.services.impl.FlightServiceImpl;
import com.epam.aviasales.services.impl.PersonalDataServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class TicketRepositoryImplMock implements TicketRepository {

  private static volatile TicketRepository instance;
  private static final Map<Long, Ticket> TICKET_CACHE = new HashMap<>();
  private static final PersonalDataService personalDataService = PersonalDataServiceImpl
      .getInstance();
  private static final AccountService accountService = AccountServiceImpl.getInstance();
  private static final FlightService flightService = FlightServiceImpl.getInstance();

  public static TicketRepository getInstance() {
    TicketRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (TicketRepositoryImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new TicketRepositoryImplMock();
        }
      }
    }

    return localInstance;
  }

  private TicketRepositoryImplMock() {
    final int CACHE_COUNT = 100;

    for (int i = 1; i < CACHE_COUNT; i++) {
      Long longI = Long.valueOf(i);
      TICKET_CACHE.put(longI,
          Ticket.builder().id(longI)
              .personalData(personalDataService.getPersonalDataById(longI))
              .flight(flightService.getFlightById(longI))
              .account(accountService.getAccountById(longI)).price(1000 + i * 100)
              .isBusiness(i % 7 == 0 ? true : false).build());
    }
  }

  @Override
  public List<Ticket> getTickets() {
    return getTickets(1, Integer.MAX_VALUE);
  }

  @Override
  public List<Ticket> getTickets(int page, int count) {
    List<Ticket> ticketList = new ArrayList<>();
    if (page <= 0 || count <= 0) {
      return ticketList;
    }

    final int startI = (page - 1) * count;
    for (int i = startI; i < startI + count; i++) {
      if (i >= TICKET_CACHE.size()) {
        break;
      }
      ticketList.add(TICKET_CACHE.get(Long.valueOf(i)));
    }
    return ticketList;
  }

  @Override
  public Ticket getTicketById(Long id) {
    return TICKET_CACHE.get(id);
  }

  @Override
  public void addTicket(Ticket ticket) {
    TICKET_CACHE.put(ticket.getId(), ticket);
  }

  @Override
  public void deleteTicket(Long id) {
    TICKET_CACHE.remove(id);
  }

  @Override
  public boolean isExist(Long id) {
    return TICKET_CACHE.containsKey(id);
  }
}
