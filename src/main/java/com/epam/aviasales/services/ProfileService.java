package com.epam.aviasales.services;

import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.repositories.TicketRepository;
import com.epam.aviasales.repositories.impl.TicketRepositoryImpl;

import java.util.List;

public class ProfileService {
  private static volatile ProfileService instance;
  private static final TicketRepository ticketRepository = TicketRepositoryImpl.getInstance();

  private ProfileService() {}

  public static ProfileService getInstance() {
    ProfileService localInstance = instance;
    if (localInstance == null) {
      synchronized (ProfileService.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new ProfileService();
        }
      }
    }
    return localInstance;
  }

  public List<Ticket> getAccountTickets(Long accountId) {
    return ticketRepository.getTicketsByAccountId(accountId);
  }
}
