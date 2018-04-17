package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.repositories.TicketRepository;
import com.epam.aviasales.repositories.impl.TicketRepositoryImpl;

import com.epam.aviasales.services.ProfileService;
import java.util.List;

public class ProfileServiceImpl implements ProfileService {
  private static volatile ProfileServiceImpl instance;
  private static final TicketRepository ticketRepository = TicketRepositoryImpl.getInstance();

  private ProfileServiceImpl() {}

  public static ProfileServiceImpl getInstance() {
    ProfileServiceImpl localInstance = instance;
    if (localInstance == null) {
      synchronized (ProfileServiceImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new ProfileServiceImpl();
        }
      }
    }
    return localInstance;
  }

  public List<Ticket> getAccountTickets(Long accountId) {
    return ticketRepository.getTicketsByAccountId(accountId);
  }

  public void deleteAccountTicketById(Long ticketId) {
    ticketRepository.delete(ticketId);
  }
}
