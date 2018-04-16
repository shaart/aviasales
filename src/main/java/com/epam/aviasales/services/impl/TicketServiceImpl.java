package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.repositories.TicketRepository;
import com.epam.aviasales.repositories.implMock.TicketRepositoryImplMock;
import com.epam.aviasales.services.TicketService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketServiceImpl implements TicketService {

  private static final TicketService instance = new TicketServiceImpl();

  public static TicketService getInstance() {
    return instance;
  }

  private static final TicketRepository ticketRepository = TicketRepositoryImplMock
      .getInstance();

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
}
