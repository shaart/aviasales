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

  private static final TicketRepository ticketRepositoryMock = TicketRepositoryImplMock
      .getInstance();

  @Override
  public List<Ticket> getTickets() {
    return ticketRepositoryMock.getTickets(1, 20);
  }

  @Override
  public List<Ticket> getTickets(int page, int count) {
    return ticketRepositoryMock.getTickets(page, count);
  }

  @Override
  public Ticket getTicketById(Long id) {
    return ticketRepositoryMock.getTicketById(id);
  }

  @Override
  public void addTicket(Ticket ticket) {
    ticketRepositoryMock.addTicket(ticket);
  }

  @Override
  public void deleteTicket(Long id) {
    ticketRepositoryMock.deleteTicket(id);
  }

  @Override
  public boolean isValid(Ticket ticket) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Ticket> getTicketsLike(Ticket seekingTicket, int page, int size) {
    return ticketRepositoryMock.getTicketsLike(seekingTicket, page, size);
  }

  @Override
  public void updateTicket(Long id, Ticket receivedTicket) {
    ticketRepositoryMock.updateTicket(id, receivedTicket);
  }
}
