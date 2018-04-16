package com.epam.aviasales.repositories.impl;

import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.repositories.TicketRepository;
import com.epam.aviasales.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketRepositoryImpl implements TicketRepository {

  private static volatile TicketRepository instance;

  public static synchronized TicketRepository getInstance() {
    TicketRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (TicketRepositoryImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new TicketRepositoryImpl();
        }
      }
    }

    return localInstance;
  }

  @Override
  public void addTicket(Ticket ticket) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    session.beginTransaction();

    session.save(ticket);
    session.getTransaction().commit();

    session.close();
  }

  @Override
  public boolean isExist(Long id) {
    return getTicketById(id) != null;
  }

  @Override
  public void deleteTicket(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    session.delete(getTicketById(id));

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public Ticket getTicketById(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Ticket where id=:id");
    query.setParameter("id", id);
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (Ticket) list.get(0) : null;
  }

  @Override
  public List<Ticket> getTickets() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Ticket");
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (List<Ticket>) list : null;
  }

  @Override
  public List<Ticket> getTickets(int page, int count) {
    return null;
  }
}
