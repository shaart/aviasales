package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.repositories.TicketRepository;
import com.epam.aviasales.services.TicketService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketServiceImpl implements TicketService {

  private static final TicketService instance = new TicketServiceImpl();

  private final TicketRepository ticketRepository = TicketRepository.getInstance();

  public static TicketService getInstance() {
    return instance;
  }

  @Override
  public Ticket getTicket(Long id) {
    return ticketRepository.getTicket(id);
  }

  @Override
  public List<Ticket> getTickets() {
    return ticketRepository.getTickets();
  }

  @Override
  public void createTicket(Ticket ticket) {
    ticketRepository.insert(ticket);
  }

  @Override
  public void deleteTicket(Long id) {
    ticketRepository.delete(id);
  }

  @Override
  public boolean check(Ticket ticket) {

    return false;
  }
}
