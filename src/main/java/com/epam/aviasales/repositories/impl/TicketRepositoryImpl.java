package com.epam.aviasales.repositories.impl;

import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.repositories.TicketRepository;
import com.epam.aviasales.util.HibernateUtil;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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
  public List<Ticket> getTicketsLike(Ticket seekingTicket, int page, int size) {
    if (size <= 0) {
      return new ArrayList<>();
    }
    if (seekingTicket == null) {
      return getTickets(page, size);
    }
    if (page < 1) {
      page = 1;
    }
    // pages start from 1
    page -= 1;

    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    Criteria criteria = session.createCriteria(Ticket.class);
    if (seekingTicket.getId() != null) {
      criteria.add(Restrictions.eq("id", seekingTicket.getId()));
    }
    if (seekingTicket.getPersonalData() != null) {
      criteria.add(Restrictions.eq("personalData", seekingTicket.getPersonalData()));
    }
    if (seekingTicket.getFlight() != null) {
      criteria.add(Restrictions.eq("flight", seekingTicket.getFlight()));
    }
    if (seekingTicket.getAccount() != null) {
      criteria.add(Restrictions.eq("account", seekingTicket.getAccount()));
    }
    if (seekingTicket.getPrice() != null) {
      criteria.add(Restrictions.eq("price", seekingTicket.getPrice()));
    }
    if (seekingTicket.getIsBusiness() != null) {
      criteria.add(Restrictions.eq("isBusiness", seekingTicket.getIsBusiness()));
    }

    criteria.addOrder(Order.asc("id"));
    criteria.setFirstResult(page * size);
    criteria.setMaxResults(size);

    List<Ticket> result = (List<Ticket>) criteria.list();
    session.getTransaction().commit();
    session.close();

    return result;
  }

  @Override
  public void updateTicket(Long id, Ticket receivedTicket) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    if (id != null && id != receivedTicket.getId()) {
      receivedTicket.setId(id);
    }
    session.update(receivedTicket);

    session.getTransaction().commit();
    session.close();
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
