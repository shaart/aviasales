package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirportRepository {

  private static final AirportRepository instance = new AirportRepository();

  public void insert(Airport airport) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    session.save(airport);

    session.getTransaction().commit();
    session.close();
  }

  public boolean isExist(Long id) {
    return getAirport(id) != null;
  }

  public void delete(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    session.delete(getAirport(id));

    session.getTransaction().commit();
    session.close();
  }

  public Airport getAirport(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Airport where id=:id");
    query.setParameter("id", id);
    List list = query.list();

    session.getTransaction().commit();
    session.close();
    return list.size() > 0 ? (Airport) list.get(0) : null;
  }

  public List<Airport> getAirports() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Airport");
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (List<Airport>) list : null;
  }

  public static AirportRepository getInstance() {
    return instance;
  }
}
