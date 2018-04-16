package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightRepository {

  private final static FlightRepository instance =  new FlightRepository();

  public void insert(Flight flight) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    session.beginTransaction();

    session.save(flight);

    session.getTransaction().commit();
    session.close();
  }

  public boolean isExist(Long id){
    return getFlight(id)!=null;
  }

  public void delete(Long id){
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    session.delete(getFlight(id));

    session.getTransaction().commit();
    session.close();
  }

  public Flight getFlight(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Flight where id=:id");
    query.setParameter("id", id);
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (Flight) list.get(0) : null;
  }

  public List<Flight> getFlights(Long from, Long to) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Flight where id>=:from and id<=:to");
    query.setParameter("from", from);
    query.setParameter("to", to);
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (List<Flight>) list : null;
  }

  public List<Flight> getFlights() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Flight");
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (List<Flight>) list : null;
  }

  public static FlightRepository getInstance() {
    return instance;
  }
}
