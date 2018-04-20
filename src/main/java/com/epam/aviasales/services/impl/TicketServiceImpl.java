package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.exceptions.NoAvailableSeatsForTheFlight;
import com.epam.aviasales.repositories.TicketRepository;
import com.epam.aviasales.repositories.impl.FlightRepositoryImpl;
import com.epam.aviasales.repositories.impl.TicketRepositoryImpl;
import com.epam.aviasales.services.TicketService;

import java.util.List;

import javax.naming.ldap.UnsolicitedNotification;
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
  public void addTicket(Ticket ticket) throws NoAvailableSeatsForTheFlight {
    FlightServiceImpl.getInstance().updateFlight(ticket.getFlight(), ticket.getIsBusiness(), false);
    Account account = AccountServiceImpl.getInstance()
        .getAccountById(ticket.getAccount().getId());
    ticket.setAccount(account);
    Flight flight = FlightRepositoryImpl.getInstance()
        .getFlightById(ticket.getFlight().getId());
    ticket.setFlight(flight);
    ticketRepository.addTicket(ticket);
  }

  @Override
  public void deleteTicket(Long id) {
    ticketRepository.deleteTicket(id);
  }

  @Override
  public boolean isValid(Ticket ticket) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Ticket> getTicketsLike(Ticket seekingTicket, int page, int size) {
    return ticketRepository.getTicketsLike(seekingTicket, page, size);
  }

  @Override
  public void updateTicket(Long id, Ticket receivedTicket) {
    ticketRepository.updateTicket(id, receivedTicket);
  }
}
