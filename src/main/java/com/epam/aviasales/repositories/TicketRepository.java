package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketRepository {

  private static final TicketRepository instance = new TicketRepository();

  public void insert(Ticket ticket) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    session.beginTransaction();

    session.save(ticket);
    session.getTransaction().commit();

    session.close();
  }

  public boolean isExist(Long id){
    return getTicket(id)!=null;
  }

  public void delete(Ticket ticket){
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    session.delete(ticket);

    session.getTransaction().commit();
    session.close();
  }

  public Ticket getTicket(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Ticket where id=:id");
    query.setParameter("id", id);
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (Ticket) list.get(0) : null;
  }

  public List<Ticket> getTickets() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Ticket");
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (List<Ticket>) list : null;
  }

  public static synchronized TicketRepository getInstance() {
    return instance;
  }
}
