package com.epam.aviasales.services.impMock;

import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.repositories.TicketRepository;
import com.epam.aviasales.repositories.implMock.TicketRepositoryImplMock;
import com.epam.aviasales.services.TicketService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketServiceImplMock implements TicketService {

  private static volatile TicketService instance;

  public static TicketService getInstance() {
    TicketService localInstance = instance;
    if (localInstance == null) {
      synchronized (TicketServiceImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new TicketServiceImplMock();
        }
      }
    }
    return localInstance;
  }

  private static final TicketRepository ticketRepository = TicketRepositoryImplMock.getInstance();

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
