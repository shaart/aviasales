package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.repositories.TicketRepository;
import com.epam.aviasales.repositories.impl.TicketRepositoryImpl;
import com.epam.aviasales.services.TicketService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketServiceImpl implements TicketService {

  private static volatile TicketService instance;

  public static TicketService getInstance() {
    TicketService localInstance = instance;
    if (localInstance == null) {
      synchronized (TicketServiceImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new TicketServiceImpl();
        }
      }
    }
    return localInstance;
  }

  private static final TicketRepository ticketRepository = TicketRepositoryImpl.getInstance();

  @Override
  public List<Ticket> getTickets() {
    return ticketRepository.getTickets(1, 20);
  }

  @Override
  public List<Ticket> getTickets(int page, int count) {
    return ticketRepository.getTickets(page, count);
  }

  @Override
  public Ticket getTicketById(Long id) {
    return ticketRepository.getTicketById(id);
  }

  @Override
  public void addTicket(Ticket ticket) {
    ticketRepository.addTicket(ticket);
  }

  @Override
  public void deleteTicket(Long id) {
    ticketRepository.deleteTicket(id);
  }

  @Override
  public boolean isValid(Ticket ticket) {
    return false;
  }
}
