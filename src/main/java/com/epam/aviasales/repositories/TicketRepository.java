package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Ticket;
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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketRepository {

  private static final TicketRepository instance = new TicketRepository();
  private static final PersonalDataService personalDataService = PersonalDataServiceImpl
      .getInstance();
  private static final FlightService flightService = FlightServiceImpl.getInstance();
  private static final AccountService accountService = AccountServiceImpl.getInstance();

  public static TicketRepository getInstance() {
    return instance;
  }

  private static final Map<Long, Ticket> TICKET_CACHE = new HashMap<>();

  static {
    final int CACHE_COUNT = 100;

    for (int i = 2; i < CACHE_COUNT; i++) {
      TICKET_CACHE.put(Long.valueOf(i),
          new Ticket(Long.valueOf(i), personalDataService.getById(Long.valueOf(i)),
              flightService.getById(Long.valueOf(i)), accountService.getById(Long.valueOf(i)),
              1000 + i * 100, i % 7 == 0));
    }
  }

  public List<Ticket> getTickets() {
    return getTickets(1, Integer.MAX_VALUE);
  }

  public List<Ticket> getTickets(int page, int count) {
    List<Ticket> ticketList = new ArrayList<>();
    if (page <= 0 || count <= 0) {
      return ticketList;
    }

    final int startI = (page - 1) * count + 1;
    final int REQ_NUM = count == Integer.MAX_VALUE ? count : startI + count;
    for (int i = startI; i < REQ_NUM; i++) {
      if (i >= TICKET_CACHE.size()) {
        break;
      }
      ticketList.add(TICKET_CACHE.get(Long.valueOf(i)));
    }
    return ticketList;
  }

  public Ticket getById(Long id) {
    return TICKET_CACHE.get(id);
  }
}
