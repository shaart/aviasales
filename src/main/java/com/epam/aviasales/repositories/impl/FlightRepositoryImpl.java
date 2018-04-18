package com.epam.aviasales.repositories.impl;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.repositories.FlightRepository;
import com.epam.aviasales.util.HibernateUtil;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightRepositoryImpl implements FlightRepository {

  private static volatile FlightRepository instance;

  public static FlightRepository getInstance() {
    FlightRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (FlightRepositoryImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new FlightRepositoryImpl();
        }
      }
    }

    return localInstance;
  }

  @Override
  public void addFlight(Flight flight) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    session.beginTransaction();

    session.save(flight);

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public boolean isExist(Long id) {
    return getFlightById(id) != null;
  }

  @Override
  public void deleteFlight(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    session.delete(getFlightById(id));

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public Flight getFlightById(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Flight where id=:id");
    query.setParameter("id", id);
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (Flight) list.get(0) : null;
  }

  @Override
  public List<Flight> getFlights(Long fromId, Long toId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Flight where id>=:from and id<=:to");
    query.setParameter("from", fromId);
    query.setParameter("to", toId);
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (List<Flight>) list : null;
  }

  @Override
  public void updateFlight(Long id, Flight updatedFlight) {

  }

  @Override
  public List<Flight> getFlightsLike(Flight seekingFlight, int page, int size) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Flight> getFlights() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Flight");
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (List<Flight>) list : null;
  }

  @Override
  public List<Flight> getFlightsPage(int page, int count) {
    return null;
  }
}
